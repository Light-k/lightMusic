package com.light.light_music.controller;

import com.light.light_music.MyUtils.MyCookie;
import com.light.light_music.pojo.*;
import com.light.light_music.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * 视图跳转的controller层
 *
 * @Author : KangXu
 * @Date : 2020/7/3
 * @Package : com.light.light_music.controller
 */

@Controller
public class RouterController {
    @Autowired
    UserService userService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;
    @Autowired
    SongService songService;
    @Autowired
    AlbumService albumService;
    @Autowired
    LoveService loveService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;
    @Autowired
    SingerService singerService;

    /**
     * 前台页面---start
     */
    @ApiOperation("跳转到前端首页")
    @GetMapping(value = {"/", "/index", "/index.html"})
    public String index() {
        return "index";
    }

    @ApiOperation("跳转到用户登录的页面")
    @GetMapping(value = "/toLogin.do")
    public String toLogin() {
        return "user/login";
    }

    @ApiOperation("跳转到用户注册的页面")
    @GetMapping(value = "/toRegister.do")
    public String toRegister() {
        return "user/register";
    }

    @ApiOperation("跳转到用户申诉的页面")
    @GetMapping("/toAppeal.do")
    public String toAppeal() {
        return "user/appeal";
    }

    @ApiOperation("跳转到更新页面")
    @GetMapping(value = "/toUserUpdate.do")
    public String toUserUpdate(Model model, HttpSession session) {
        String uuid = (String) session.getAttribute("uuid");
        User user = userService.findByUUID(uuid);
        model.addAttribute("user", user);
        return "user/update";
    }

    @ApiOperation("跳转到音乐榜单的页面")
    @GetMapping(value = "/toSong.do")
    public String toSong(Model model) {
        List<Song> songs = songService.findAll();
        for (Song song : songs) {
            Song tempSong = songService.findAlbumBySongId(song.getUuid());
            if (null != tempSong) {
                song.setAlbum(tempSong.getAlbum());
            }
        }
        model.addAttribute("songs", songs);
        return "song/songs-list";
    }

    @ApiOperation("跳转到各个榜单的页面")
    @GetMapping(value = "/toSongGroup.do/{groupName}")
    public String toSongGroup(@ApiParam("该音乐所在的榜单") @PathVariable("groupName") String groupName, Model model) {
        List<Song> songs = songService.findBySongGroup(groupName);
        for (Song song : songs) {
            Song tempSong = songService.findAlbumBySongId(song.getUuid());
            if (null != tempSong) {
                song.setAlbum(tempSong.getAlbum());
            }
        }
        model.addAttribute("songs", songs);
        return "song/songs-group-list";
    }

    @ApiOperation("跳转到播放音乐的页面")
    @PostMapping(value = "/toPlay.do")
    public String toPlay(@ApiParam("歌曲的uuid值的集合") String[] songIds, HttpServletRequest request, Model model) throws JSONException {
        ArrayList<Song> songs = new ArrayList<>();
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            for (String songId : songIds) {
                Song song = songService.findAlbumBySongId(songId);
                if (null != song) {
                    Love love = loveService.findBySongIdAndNickname(song.getUuid(), nickname);
                    song.setLove(love);
                }
                songs.add(song);
            }
            model.addAttribute("songs", songs);
            return "song/songs-play";
        }
        return null;
    }

    @ApiOperation("跳转到歌手专区的页面")
    @GetMapping(value = "/toSinger.do")
    public String toSinger(Model model) {
        List<Singer> singers = singerService.findAll();
        model.addAttribute("singers", singers);
        return "song/songs-singer-list";
    }

    @ApiOperation("跳转到歌手详情的页面")
    @GetMapping(value = "/toSingerDetail.do/{singer}")
    public String toSingerDetail(@ApiParam("歌手的名称") @PathVariable("singer") String singer, Model model) {
        Singer tempSinger = singerService.findBySinger(singer);
        List<Song> songs = songService.findBySinger(singer);
        for (Song song : songs) {
            Song tempSong = songService.findAlbumBySongId(song.getUuid());
            song.setAlbum(tempSong.getAlbum());
        }
        Integer albumSum = songService.findAlbumSumBySinger(singer);
        Integer songsSum = songService.findSongsBySinger(singer);
        model.addAttribute("tempSinger", tempSinger);
        model.addAttribute("songs", songs);
        model.addAttribute("songsSum", songsSum);
        model.addAttribute("albumSum", albumSum);
        return "song/songs-singerDetail-list";
    }

    @ApiOperation("跳转到专辑详情的页面")
    @GetMapping(value = "/toAlbumDetail.do/{albumId}")
    public String toAlbumDetail(@PathVariable("albumId") Integer albumId, HttpServletRequest request, Model model) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            List<Song> songs = albumService.querySongFromAlbum(albumId);
            for (Song song : songs) {
                Song tempSong = songService.findAlbumBySongId(song.getUuid());
                song.setAlbum(tempSong.getAlbum());
                Love love = loveService.findBySongIdAndNickname(song.getUuid(), nickname);
                song.setLove(love);
            }
            model.addAttribute("songs", songs);
            return "song/songs-play";
        }
        return null;
    }

    @ApiOperation("跳转到评论的页面")
    @GetMapping(value = "/toComment.do/{songId}")
    public String toComment(@ApiParam("要评论的歌曲的uuid值") @PathVariable("songId") String songId, Model model) {
        Song song = songService.findAlbumBySongId(songId);
        List<Comment> comments = commentService.findBySongId(songId);
        for (Comment comment : comments) {
            //查询出该评论下面的所有回复
            List<Reply> replies = replyService.findByCommentId(comment.getUuid());
            comment.setReplies(replies);
        }
        model.addAttribute("song", song);
        model.addAttribute("comments", comments);
        return "song/songs-comment-list";
    }

    @ApiOperation("跳转到留言板的页面")
    @GetMapping(value = "/toReply.do")
    public String toReply(HttpServletRequest request, Model model) {
        Cookie cookie = MyCookie.findCookie("loginUser", request.getCookies());
        if (null != cookie) {
            String nickname = cookie.getValue();
            User user = userService.findByNickname(nickname);
            //查询出所有与我相关的评论
            List<Comment> comments = commentService.findByNickname(nickname);
            //查询出所有与我相关的回复
            List<Reply> replies = replyService.findByNickname(nickname);
            model.addAttribute("user", user);
            model.addAttribute("comments", comments);
            model.addAttribute("replies", replies);
            return "song/songs-reply-list";
        }
        return null;
    }

    /**
     * 前台页面---end
     */

    /**
     * 后台页面---start
     */

    @ApiOperation("跳转到后台首页")
    @GetMapping(value = {"/admin", "/adminIndex", "/adminIndex.html"})
    public String adminIndex() {
        return "admin/adminIndex";
    }

    @ApiOperation("跳转到后台主页")
    @GetMapping(value = "/adminMain")
    public String adminMain() {
        return "admin/adminMain";
    }

    @ApiOperation("跳转到修改员工密码的页面")
    @GetMapping(value = "/admin/toUpdateEmployeePwd.do")
    public String toUpdateEmployeePwd(HttpServletRequest request, Model model) {
        SecurityContextImpl securityContext = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        Employee tempEmployee = employeeService.findEmployeeByUsername(securityContext.getAuthentication().getName());
        Employee employee = employeeService.findEmployeeByUUID(tempEmployee.getUuid());
        model.addAttribute("employee", employee);
        return "admin/employee/updatePwd";
    }

    @ApiOperation("跳转到员工注册的页面")
    @GetMapping(value = "/admin/toSaveEmployee.do")
    public String toSaveEmployee() {
        return "admin/employee/register";
    }

    @ApiOperation("跳转到更新员工的页面")
    @GetMapping(value = "/admin/toUpdateEmployee.do/{uuid}")
    public String toUpdateEmployee(@ApiParam("员工id") @PathVariable("uuid") String uuid, Model model) {
        Employee employee = employeeService.findEmployeeByUUID(uuid);
        model.addAttribute("employee", employee);
        return "admin/employee/update";
    }

    @ApiOperation("跳转到添加员工身份的页面")
    @GetMapping(value = "/admin/toAddEmployeeRole.do/{uuid}")
    public String toAddEmployeeRole(@ApiParam("员工id") @PathVariable("uuid") String uuid, Model model) {
        Employee employee = employeeService.findEmployeeByUUID(uuid);
        model.addAttribute("employee", employee);
        //查询员工可以添加的身份
        List<Role> roles = roleService.findEnableRole(uuid);
        model.addAttribute("roles", roles);
        return "admin/employee/employee-role-save";
    }

    @ApiOperation("跳转到文件上传的页面")
    @GetMapping(value = "/admin/toUpload.do")
    public String toUpload() {
        return "admin/song/file";
    }

    @ApiOperation("跳转到发送邮件的页面")
    @GetMapping(value = "/admin/toSendMail.do/{username}")
    public String toSendMail(@ApiParam("用户的邮箱") @PathVariable("username") String username, Model model) {
        model.addAttribute("receiver", username);
        model.addAttribute("emailStatus", "编辑中");
        return "admin/user/mail";
    }

    @ApiOperation("跳转到上传新专辑的页面")
    @GetMapping(value = "/admin/toAddAlbum.do")
    public String toAddAlbum() {
        return "admin/album/album-add";
    }

    @ApiOperation("跳转到收录歌曲的页面")
    @GetMapping(value = "/admin/toAddSong.do/{albumId}")
    public String toAddSong(@ApiParam("专辑的id值") @PathVariable("albumId") Integer albumId, Model model) {
        //查询未被收录的歌曲
        List<Song> songs = albumService.findOtherSongs();
        model.addAttribute("songs", songs);
        model.addAttribute("albumId", albumId);
        return "admin/album/song-album-save";
    }

    @ApiOperation("添加歌手")
    @GetMapping(value = "/admin/addSinger.do")
    public String addSinger() {
        return "admin/singer/singer-add";
    }

    @ApiOperation("更新歌手信息")
    @GetMapping(value = "/admin/updateSinger.do/{uuid}")
    public String updateSinger(@ApiParam("歌手的uuid值") @PathVariable("uuid") String uuid, Model model) {
        Singer singer = singerService.findByUuid(uuid);
        model.addAttribute("singer", singer);
        return "admin/singer/singer-update";
    }
    /**
     * 后台页面---end
     */
}
