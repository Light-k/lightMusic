package com.light.light_music.MyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件工具类
 *
 * @Author : KangXu
 * @Date : 2020/7/18
 * @Package : com.light.light_music.MyUtils
 */

@Component
public class MyEmail {
    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     *  复杂邮件发送
     * @param multipart:是否开启附件上传
     * @param sendMan:发件人
     * @param receiver:收件人
     * @param subject:主题
     * @param fileName:上传的文件名
     * @param filePath:上传的文件所在路径
     * @param text:正文
     * @param html:是否解析html
     * @throws MessagingException
     */
    public void sendComplexMail(Boolean multipart, String sendMan, String receiver, String subject, String fileName, String filePath, String text, Boolean html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,multipart);
        helper.setFrom(sendMan);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.addAttachment(fileName,new File(filePath));
        helper.setText(text,html);
        mailSender.send(mimeMessage);
    }

    /**
     * 仅支持上传附件
     * @param multipart:是否开启附件上传
     * @param sendMan:发件人
     * @param receiver:收件人
     * @param subject:主题
     * @param fileName:上传的文件名
     * @param filePath:上传的文件所在路径
     * @param text:正文
     * @throws MessagingException
     */
    public void sendAttachmentMail(Boolean multipart, String sendMan, String receiver, String subject, String fileName, String filePath, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,multipart);
        helper.setFrom(sendMan);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.addAttachment(fileName,new File(filePath));
        helper.setText(text);
        mailSender.send(mimeMessage);
    }

    /**
     * 仅支持html解析
     * @param sendMan:发件人
     * @param receiver:收件人
     * @param subject:主题
     * @param text:正文
     * @param html:是否解析html
     * @throws MessagingException
     */
    public void sendHtmlMail(String sendMan, String receiver, String subject, String text, Boolean html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(sendMan);
        helper.setTo(receiver);
        helper.setSubject(subject);
        helper.setText(text,html);
        mailSender.send(mimeMessage);
    }

    /**
     * 简单邮件发送
     * @param sendMan:发件人
     * @param receiver:收件人
     * @param subject:主题
     * @param text:正文
     */
    public void sendSimpleMail(String sendMan,String receiver,String subject,String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sendMan);
        mailMessage.setTo(receiver);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }


}
