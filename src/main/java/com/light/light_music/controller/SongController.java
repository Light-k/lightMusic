package com.light.light_music.controller;

import com.alibaba.fastjson.JSON;
import com.light.light_music.MyUtils.MyCookie;
import com.light.light_music.pojo.*;
import com.light.light_music.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


/**
 * song类的controller层
 *
 * @Author : KangXu
 * @Date : 2020/7/2
 * @Package : com.light.light_music.controller
 */

@Controller
@RequestMapping(value = "/song")
public class SongController {
    @Autowired
    SongService songService;
    @Autowired
    LoveService loveService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    SingerService singerService;
    @Autowired
    AlbumService albumService;

    @ApiOperation("根据用户输入的信息查询")
    @GetMapping(value = "/query.do/{queryName}")
    public String query(@ApiParam("要查询的信息") @PathVariable("queryName") String queryName, Model model) throws UnsupportedEncodingException {
        List<Song> songs = songService.findBySongName(queryName);
        Singer tempSinger = singerService.findBySinger(queryName);
        List<Album> albums = albumService.findByAlbumName(queryName);
        if (!songs.isEmpty()) {
            for (Song song : songs) {
                Song tempSong = songService.findAlbumBySongId(song.getUuid());
                song.setAlbum(tempSong.getAlbum());
            }
            model.addAttribute("songs", songs);
            return "song/songs-songDetail-list";
        }
        if (null != tempSinger) {
            String singer = URLEncoder.encode(queryName, "UTF-8");
            return "redirect:/toSingerDetail.do/" + singer;
        }
        if (!albums.isEmpty()) {
            System.out.println(queryName);
            System.out.println(albums);
            model.addAttribute("albums", albums);
            return "song/songs-albumDetail-list";
        } else {
            return "error/4xx";
        }
    }

    @ApiOperation("查询全部歌手")
    @GetMapping(value = "/findAllSingers.do")
    public String findAllSingers(Model model) {
        List<Singer> singers = singerService.findAll();
        model.addAttribute("singers", singers);
        return "song/songs-singer-list";
    }

    @ApiOperation("根据歌手的名称的首字母/性别/所在的地区/演唱的曲风查询歌手")
    @ResponseBody
    @PostMapping(value = "/querySinger.do")
    public List<Singer> querySinger(@ApiParam("歌手的对象") @RequestBody Singer singer) {
        List<Singer> singers = singerService.querySinger(singer);
        return singers;
    }

    @ApiOperation("查看收藏列表")
    @GetMapping(value = "/findMyLove.do")
    public String findMyLove(HttpServletRequest request, Model model) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            List<Love> loves = loveService.findByNickname(nickname);
            if (!loves.isEmpty()) {
                for (Love love : loves) {
                    Song song = loveService.findByLoveId(love.getUuid());
                    love.setSong(song);
                }
            }
            model.addAttribute("loves", loves);
            return "song/songs-love-list";
        }
        return null;
    }

    @ApiOperation("收藏功能")
    @ResponseBody
    @GetMapping(value = "/myLove.do/{songId}")
    public String myLove(@ApiParam("想要收藏的歌曲的uuid值") @PathVariable("songId") String songId, HttpServletRequest request) {
        Song song = songService.findBySongId(songId);
        Song tempSong = songService.findAlbumBySongId(song.getUuid());
        if (null != tempSong) {
            song.setAlbum(tempSong.getAlbum());
        }
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            Love love = new Love();
            love.setNickname(nickname);
            love.setSongName(song.getSongName());
            love.setSinger(song.getSinger());
            love.setPlayTime(song.getPlayTime());
            love.setSongImage(song.getAlbum().getImageStr());
            love.setSongAlbum(song.getAlbum().getAlbumName());
            loveService.save(love);
            loveService.toLove(songId, love.getUuid());
            return "已收藏";
        }
        return null;
    }

    @ApiOperation("取消关注")
    @ResponseBody
    @GetMapping(value = "/removeMyLove.do/{loveId}")
    public String removeMyLove(@ApiParam("想要收藏的歌曲的uuid值") @PathVariable("loveId") String loveId) {
        loveService.toRemoveLove(loveId);
        loveService.delete(loveId);
        return "已取消关注";
    }

    @ApiOperation("评论歌曲")
    @ResponseBody
    @PostMapping(value = "/comment.do")
    public String comment(@RequestBody @ApiParam("评论对象") Comment comment, HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            comment.setNickname(nickname);
            commentService.save(comment);
            commentService.toComment(comment.getSong().getUuid(), comment.getUuid());
            return JSON.toJSONString("已评论");
        }
        return JSON.toJSONString(null);
    }

    @ApiOperation("删除用户的评论")
    @ResponseBody
    @GetMapping(value = "/deleteComment.do/{uuid}")
    public String deleteComment(@ApiParam("评论的uuid值") @PathVariable("uuid") String uuid) {
        commentService.toRemoveComment(uuid);
        commentService.delete(uuid);
        return "已删除";
    }

    @ApiOperation("回复歌曲的评论")
    @ResponseBody
    @PostMapping(value = "/reply.do")
    public String reply(@RequestBody @ApiParam("评论对象和回复对象的集合") JsonParams jsonParams, HttpServletRequest request) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            Comment comment = jsonParams.getComment();
            Reply reply = jsonParams.getReply();
            reply.setNickname(nickname);
            replyService.save(reply);
            //回复评论
            replyService.replyToComment(comment.getUuid(), reply.getUuid());
            return JSON.toJSONString("已回复");
        }
        return JSON.toJSONString(null);
    }

    @ApiOperation("删除用户的回复")
    @ResponseBody
    @GetMapping(value = "/deleteReply.do/{uuid}")
    public String deleteReply(@ApiParam("回复的uuid值") @PathVariable("uuid") String uuid) {
        replyService.toRemoveReply(uuid);
        replyService.delete(uuid);
        return "已删除";
    }
}
