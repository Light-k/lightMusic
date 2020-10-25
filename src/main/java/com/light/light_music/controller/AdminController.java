package com.light.light_music.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.light.light_music.MyUtils.MyEmail;
import com.light.light_music.pojo.*;
import com.light.light_music.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * admin类的controller层
 *
 * @Author : KangXu
 * @Date : 2020/7/5
 * @Package : com.light.light_music.controller
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    SongService songService;
    @Autowired
    MyEmail myEmail;
    @Autowired
    AlbumService albumService;
    @Autowired
    SingerService singerService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;

    @ApiOperation("修改员工密码")
    @PostMapping(value = "/updateEmployeePwd.do")
    public String updateEmployeePwd(@ApiParam("员工对象") Employee employee) {
        Employee tempEmployee = employeeService.findEmployeeByUUID(employee.getUuid());
        tempEmployee.setPassword(employee.getPassword());
        employeeService.update(tempEmployee);
        return "redirect:/admin/findAllEmployees.do/1/5";
    }

    @ApiOperation("查询所有员工")
    @GetMapping(value = "/findAllEmployees.do/{pageNum}/{pageSize}")
    public String findAllEmployees(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(employeeService.findAllEmployees());
        model.addAttribute("pageInfo", pageInfo);
        return "admin/employee/employee-list";
    }

    @ApiOperation("根据员工的账号搜索")
    @PostMapping(value = "/findEmployeeByUsername.do")
    public String findEmployeeByUsername(@ApiParam("想要查询的员工的账号") String queryName, Model model) {
        Employee employee = employeeService.findEmployeeByUsername(queryName);
        ArrayList<Employee> employees = new ArrayList<>();
        if (null != employee) {
            employees.add(employee);
        }
        PageHelper.startPage(1, 1);
        PageInfo pageInfo = new PageInfo(employees);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/employee/employee-list";
    }

    @ApiOperation("上班签到")
    @GetMapping(value = "/goToWork.do/{uuid}")
    @ResponseBody
    public String goToWork(@ApiParam("员工id") @PathVariable("uuid") String uuid) {
        Employee employee = employeeService.findEmployeeByUUID(uuid);
        //设置上班签到时间
        employee.setStartTime(new Date());
        employeeService.update(employee);
        return "签到成功";
    }

    @ApiOperation("下班签到")
    @GetMapping(value = "/goOutWork.do/{uuid}")
    @ResponseBody
    public String goOutWork(@ApiParam("员工id") @PathVariable("uuid") String uuid) {
        Employee employee = employeeService.findEmployeeByUUID(uuid);
        //设置下班签到时间
        employee.setEndTime(new Date());
        employeeService.update(employee);
        return "签到成功";
    }

    @ApiOperation("注册新员工")
    @PostMapping(value = "/saveEmployee.do")
    public String saveEmployee(@ApiParam("员工对象") Employee employee, Model model) {
        Employee register = employeeService.findEmployeeByUsername(employee.getUsername());
        if (null != register) {
            model.addAttribute("msg", "注册失败");
        } else {
            employeeService.save(employee);
            model.addAttribute("msg", "注册成功,请登录");
        }
        return "admin/adminIndex";
    }

    @ApiOperation("更新员工信息")
    @PostMapping(value = "/updateEmployee.do")
    public String updateEmployee(@ApiParam("员工对象") Employee employee) {
        Employee tempEmployee = employeeService.findEmployeeByUUID(employee.getUuid());
        tempEmployee.setTrueName(employee.getTrueName());
        tempEmployee.setSex(employee.getSex());
        tempEmployee.setPhone(employee.getPhone());
        employeeService.update(tempEmployee);
        return "redirect:/admin/findAllEmployees.do/1/5";
    }

    @ApiOperation("查看员工身份")
    @GetMapping(value = "/toFindEmployeeRole.do/{pageNum}/{pageSize}/{uuid}")
    public String toFindEmployeeRole(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize, @ApiParam("员工id") @PathVariable("uuid") String uuid, Model model) {
        Employee employee = employeeService.findEmployeeByUUID(uuid);
        model.addAttribute("employee", employee);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(roleService.findByEmployeeId(uuid));
        model.addAttribute("pageInfo", pageInfo);
        return "admin/employee/employee-role-list";
    }

    @ApiOperation("添加员工身份")
    @PostMapping(value = "/saveEmployeeRole.do")
    public String saveEmployeeRole(@ApiParam("员工id") String employeeId, @ApiParam("员工身份") Integer[] roleIds) {
        roleService.save(employeeId, roleIds);
        return "redirect:/admin/toFindEmployeeRole.do/1/5/" + employeeId;
    }

    @ApiOperation("删除员工身份")
    @GetMapping(value = "/deleteEmployeeRole.do/{employeeId}/{roleId}")
    @ResponseBody
    public String deleteEmployeeRole(@ApiParam("员工id") @PathVariable("employeeId") String employeeId, @ApiParam("员工身份") @PathVariable("roleId") Integer roleId) {
        roleService.delete(employeeId, roleId);
        return "删除成功";
    }

    @ApiOperation("申请离职")
    @GetMapping(value = "/goOut.do/{uuid}")
    @ResponseBody
    public String goOut(@ApiParam("员工id") @PathVariable("uuid") String uuid) {
        Employee employee = employeeService.findEmployeeByUUID(uuid);
        //设置员工离职时间
        Date outTime = new Date();
        employee.setOutTime(outTime);
        //设置员工的状态
        employee.setStatus(0);
        employeeService.update(employee);
        return "已离职";
    }

    @ApiOperation("删除员工信息")
    @GetMapping(value = "/deleteEmployee.do/{uuid}")
    @ResponseBody
    public String deleteEmployee(@ApiParam("员工id") @PathVariable("uuid") String uuid) {
        employeeService.delete(uuid);
        return "删除成功";
    }

    @ApiOperation("查询所有用户")
    @GetMapping(value = "/findAllUsers.do/{pageNum}/{pageSize}")
    public String findAllUsers(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(userService.findAll());
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user/list";
    }

    @ApiOperation("根据用户的昵称查询")
    @PostMapping(value = "/findUserByNickname.do")
    public String findUserByNickname(@ApiParam("想要查询的用户的昵称") String queryName, Model model) {
        User user = userService.findByNickname(queryName);
        ArrayList<User> users = new ArrayList<>();
        if (null != user) {
            users.add(user);
        }
        PageHelper.startPage(1, 1);
        PageInfo pageInfo = new PageInfo(users);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/user/list";
    }

    @ApiOperation("发送邮件")
    @PostMapping(value = "/sendEmail.do")
    public String sendEmail(String receiver, String sendMan, String subject, MultipartFile file, String text, Model model) throws MessagingException, IOException {
        if (sendMan.equals("") || receiver.equals("")) {
            model.addAttribute("emailStatus", "发送失败");
        } else {
            if (file.isEmpty()) {
                //支持html解析的邮件
                if (text.startsWith("<")) {
                    myEmail.sendHtmlMail(sendMan, receiver, subject, text, true);
                }
                //普通邮件
                else {
                    myEmail.sendSimpleMail(sendMan, receiver, subject, text);
                }
            } else {
                //获取文件名
                String filename = file.getOriginalFilename();
                String path = "D:/idea/light_music/src/main/resources/static/music/mp3/";
                String filePath = path + filename;
                //复杂邮件
                if (text.startsWith("<")) {
                    myEmail.sendComplexMail(true, sendMan, receiver, subject, filename, filePath, text, true);
                }
                //仅支持上传附件
                else {
                    myEmail.sendAttachmentMail(true, sendMan, receiver, subject, filename, filePath, text);
                }
            }
            model.addAttribute("emailStatus", "已成功发送");
        }
        return "admin/user/mail";
    }

    @ApiOperation("删除用户信息")
    @GetMapping(value = "/deleteUser.do/{uuid}")
    @ResponseBody
    public String deleteUser(@ApiParam("用户id") @PathVariable("uuid") String uuid) {
        userService.delete(uuid);
        return "删除成功";
    }

    @ApiOperation("查询所有歌曲")
    @GetMapping(value = "/querySong.do/{pageNum}/{pageSize}")
    public String querySong(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        List<Song> songs = songService.findAll();
        for (Song song : songs) {
            Song tempSong = songService.findAlbumBySongId(song.getUuid());
            song.setAlbum(tempSong.getAlbum());
        }
        PageInfo pageInfo = new PageInfo(songs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/song/song-list";
    }

    @ApiOperation("根据歌曲名称查询")
    @PostMapping(value = "/findBySongName.do")
    public String findBySongName(@ApiParam("要查询的歌曲的名称") String songName, Model model) {
        List<Song> songs = songService.findBySongName(songName);
        for (Song song : songs) {
            Song tempSong = songService.findAlbumBySongId(song.getUuid());
            if (null != tempSong) {
                song.setAlbum(tempSong.getAlbum());
            }
        }
        PageHelper.startPage(1, 5);
        PageInfo pageInfo = new PageInfo(songs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/song/song-list";
    }

    @ApiOperation("上传歌曲")
    @PostMapping(value = "/upload.do")
    public String upload(@ApiParam("选择上传的文件") MultipartFile file, Model model) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        if (file.isEmpty()) {
            model.addAttribute("msg", "文件不存在");
            return "admin/song/file";
        } else {
            String fileName = file.getOriginalFilename();
            String prefix = fileName.substring(0, fileName.lastIndexOf("."));
            String[] songInfo = prefix.split("-");
            //设置歌曲名称
            String songName = songInfo[1].trim();
            //设置演唱的歌手
            String singer = songInfo[0];
            //设置歌曲上传的位置(虚拟)
            String filePath = "D:/idea/light_music/src/main/resources/static/music/mp3/";
            String path = filePath + fileName;
            File music = new File(path);
            //设置文件的大小(单位：MB)
            Double size = (double) file.getSize() / 1024 / 1024;
            //格式化
            DecimalFormat lft = new DecimalFormat("0.00");
            String fileSize = lft.format(size) + " MB";
            //检测该目录是否存在,若不存在新建该目录
            if (!music.getParentFile().exists()) {
                music.getParentFile().mkdirs();
            }
            //把MultipartFile类型转换成File类型
            file.transferTo(music);
            //解析成MP3File
            MP3File audioFile = (MP3File) AudioFileIO.read(music);
            //获取音频时长
            MP3AudioHeader audioHeader = (MP3AudioHeader) audioFile.getAudioHeader();
            String playTime = audioHeader.getTrackLengthAsString();
            //封装数据
            Song song = new Song();
            song.setSongName(songName);
            song.setSinger(singer);
            song.setPath(path);
            song.setFileSize(fileSize);
            song.setPlayTime(playTime);
            //添加歌曲信息
            songService.save(song);
            return "redirect:/admin/querySong.do/1/5";
        }
    }

    @ApiOperation("下架歌曲")
    @GetMapping(value = "/deleteSong.do/{uuid}")
    @ResponseBody
    public String deleteSong(@ApiParam("歌曲的id") @PathVariable("uuid") String uuid) {
        songService.delete(uuid);
        return "已下架";
    }

    @ApiOperation("查询所有的专辑")
    @GetMapping(value = "/queryAlbum.do/{pageNum}/{pageSize}")
    public String queryAlbum(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(albumService.findAll());
        model.addAttribute("pageInfo", pageInfo);
        return "admin/album/album-list";
    }

    @ApiOperation("根据专辑的名称模糊查询")
    @PostMapping(value = "/queryByAlbumName.do")
    public String queryByAlbumName(@ApiParam("要查询的专辑的名称") String albumName, Model model) {
        PageHelper.startPage(1, 3);
        PageInfo pageInfo = new PageInfo(albumService.findByAlbumName(albumName));
        model.addAttribute("pageInfo", pageInfo);
        return "admin/album/album-list";
    }

    @ApiOperation("添加新专辑")
    @PostMapping(value = "/addAlbum.do")
    public String addAlbum(@ApiParam("选择上传的文件") MultipartFile file, @ApiParam("专辑的对象") Album album, Model model) throws IOException {
        if (file.isEmpty()) {
            model.addAttribute("msg", "上传失败");
            return "admin/album/album-add";
        }
        //设置图片上传的位置(虚拟)
        String filename = file.getOriginalFilename();
        String path = "D:/idea/light_music/src/main/resources/static/music/album/";
        String image = path + filename;
        File f = new File(image);
        //检测该目录是否存在,若不存在新建该目录
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        //把MultipartFile类型转换成File类型
        file.transferTo(f);
        album.setImage(image);
        album.setCounts(0);
        albumService.save(album);
        model.addAttribute("msg", "上传成功");
        return "admin/album/album-add";
    }

    @ApiOperation("在专辑中收录新单曲")
    @PostMapping(value = "/addSongInAlbum.do")
    public String addSongInAlbum(@ApiParam("收录的歌曲的uuid值的集合") String[] songIds, @ApiParam("专辑的id值") Integer albumId) {
        albumService.saveNewSongs(songIds, albumId);
        return "redirect:/admin/queryAlbum.do/1/3";
    }

    @ApiOperation("查询已收录的歌曲")
    @GetMapping(value = "/querySongFromAlbum.do/{pageNum}/{pageSize}/{albumId}")
    public String querySongFromAlbum(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize, @ApiParam("要查询的专辑的id值") @PathVariable("albumId") Integer albumId, Model model) {
        PageHelper.startPage(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo(albumService.querySongFromAlbum(albumId));
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("albumId", albumId);
        return "admin/album/song-album-list";
    }

    @ApiOperation("在专辑中查询歌曲")
    @PostMapping(value = "/querySongInAlbum.do")
    public String querySongInAlbum(@ApiParam("要查询的专辑的id值") Integer albumId, @ApiParam("要查询的歌曲的名称") String queryName, Model model) {
        PageHelper.startPage(1, 3);
        List<Song> songs = albumService.querySongFromAlbum(albumId);
        for (Song song : songs) {
            if (queryName.equals(song.getSongName())) {
                PageInfo pageInfo = new PageInfo(songs);
                model.addAttribute("pageInfo", pageInfo);
            } else {
                ArrayList<Song> songArrayList = new ArrayList<>();
                PageInfo pageInfo = new PageInfo(songArrayList);
                model.addAttribute("pageInfo", pageInfo);
            }
        }
        model.addAttribute("albumId", albumId);
        return "admin/album/song-album-list";
    }

    @ApiOperation("移除单曲")
    @ResponseBody
    @GetMapping(value = "/removeSong.do/{songId}/{albumId}")
    public String removeSong(@ApiParam("要移除的单曲的uuid值") @PathVariable("songId") String songId, @ApiParam("专辑的id值") @PathVariable("albumId") Integer albumId) {
        albumService.removeSong(songId, albumId);
        return "已移除";
    }

    @ApiOperation("查询所有歌手")
    @GetMapping(value = "/querySinger.do/{pageNum}/{pageSize}")
    public String querySinger(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize,Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(singerService.findAll());
        model.addAttribute("pageInfo", pageInfo);
        return "admin/singer/singer-list";
    }

    @ApiOperation("根据歌手的名称查询歌手")
    @PostMapping(value = "/findSinger.do")
    public String findSinger(@ApiParam("要查询的歌手的名称") String queryName,Model model){
        PageHelper.startPage(1,3);
        PageInfo pageInfo = new PageInfo(singerService.findBySingers(queryName));
        model.addAttribute("pageInfo", pageInfo);
        return "admin/singer/singer-list";
    }

    @ApiOperation("添加歌手")
    @PostMapping(value = "/addSinger.do")
    public String addSinger(@ApiParam("选择上传的文件") MultipartFile file, @ApiParam("歌手的对象") Singer singer, Model model) throws IOException, BadHanyuPinyinOutputFormatCombination {
        if (file.isEmpty()) {
            model.addAttribute("msg", "上传失败");
            return "admin/singer/singer-add";
        }
        //设置图片上传的位置(虚拟)
        String filename = file.getOriginalFilename();
        String path = "D:/idea/light_music/src/main/resources/static/music/singer/";
        String image = path + filename;
        File f = new File(image);
        //检测该目录是否存在,若不存在新建该目录
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        //把MultipartFile类型转换成File类型
        file.transferTo(f);
        singer.setPath(image);
        singerService.save(singer);
        model.addAttribute("msg", "上传成功");
        return "admin/singer/singer-add";
    }

    @ApiOperation("更新歌手信息")
    @PostMapping(value = "/updateSinger.do")
    public String updateSinger(@ApiParam("选择上传的文件") MultipartFile file, @ApiParam("歌手的对象") Singer singer) throws IOException, BadHanyuPinyinOutputFormatCombination {
        Singer tempSinger = singerService.findByUuid(singer.getUuid());
        if (!file.isEmpty()) {
            //设置图片上传的位置(虚拟)
            String filename = file.getOriginalFilename();
            String path = "D:/idea/light_music/src/main/resources/static/music/singer/";
            String image = path + filename;
            File f = new File(image);
            //检测该目录是否存在,若不存在新建该目录
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            //把MultipartFile类型转换成File类型
            file.transferTo(f);
            singer.setPath(image);
        } else {
            singer.setPath(tempSinger.getPath());
        }
        singerService.update(singer);
        List<Song> songs = songService.findBySinger(tempSinger.getSinger());
        for (Song song : songs) {
            song.setSinger(song.getSinger().replace(tempSinger.getSinger(), singer.getSinger()));
            songService.update(song);
        }
        return "redirect:/admin/querySinger.do/1/3";
    }

    @ApiOperation("删除歌手")
    @ResponseBody
    @GetMapping(value = "/deleteSinger.do/{uuid}")
    public String deleteSinger(@ApiParam("歌手的uuid值") @PathVariable("uuid") String uuid) {
        singerService.delete(uuid);
        return "已删除";
    }

    @ApiOperation("查询所有评论")
    @GetMapping(value = "/queryComment.do/{pageNum}/{pageSize}")
    public String queryComment(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize,Model model) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments = commentService.findAll();
        for (Comment comment : comments) {
            List<Reply> replies = replyService.findByCommentId(comment.getUuid());
            comment.setReplies(replies);
        }
        PageInfo pageInfo = new PageInfo(comments);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/comment/comment-list";
    }

    @ApiOperation("根据用户的昵称查询评论")
    @PostMapping(value = "/queryCommentByUser.do")
    public String queryCommentByUser(@ApiParam("要查询的用户的昵称") String queryName, Model model){
        PageHelper.startPage(1,5);
        List<Comment> comments = commentService.findByNickname(queryName);
        for (Comment comment : comments) {
            List<Reply> replies = replyService.findByCommentId(comment.getUuid());
            comment.setReplies(replies);
        }
        PageInfo pageInfo = new PageInfo(comments);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/comment/comment-list";
    }

    @ApiOperation("删除评论")
    @ResponseBody
    @GetMapping(value = "/deleteComment.do/{commentId}")
    public String deleteComment(@ApiParam("评论的uuid值") @PathVariable("commentId") String commentId) {
        commentService.toRemoveComment(commentId);
        commentService.delete(commentId);
        return "已删除";
    }

    @ApiOperation("查询回复")
    @GetMapping(value = "/queryReply.do/{pageNum}/{pageSize}/{commentId}")
    public String queryReply(@ApiParam("当前页码") @PathVariable("pageNum") Integer pageNum, @ApiParam("每页的记录数") @PathVariable("pageSize") Integer pageSize,@ApiParam("评论的uuid值") @PathVariable("commentId") String commentId, Model model) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(replyService.findByCommentId(commentId));
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("commentId", commentId);
        return "admin/comment/reply-list";
    }

    @ApiOperation("根据用户的昵称查询回复")
    @PostMapping(value = "/queryReplyByUser.do")
    public String queryReplyByUser(@ApiParam("评论的uuid值") String commentId,@ApiParam("要查询的用户的昵称") String queryName,Model model){
        PageHelper.startPage(1,5);
        List<Reply> replies = replyService.findByCommentId(commentId);
        for (Reply reply : replies) {
            if(queryName.equals(reply.getNickname())){
                PageInfo pageInfo = new PageInfo(replies);
                model.addAttribute("pageInfo", pageInfo);
            }else {
                ArrayList<Reply> replyArrayList = new ArrayList<>();
                PageInfo pageInfo = new PageInfo(replyArrayList);
                model.addAttribute("pageInfo", pageInfo);
            }
        }
        model.addAttribute("commentId", commentId);
        return "admin/comment/reply-list";
    }

    @ApiOperation("删除回复")
    @ResponseBody
    @GetMapping(value = "/deleteReply.do/{replyId}")
    public String deleteReply(@ApiParam("回复的uuid值") @PathVariable("replyId") String replyId) {
        replyService.toRemoveReply(replyId);
        replyService.delete(replyId);
        return "已删除";
    }
}
