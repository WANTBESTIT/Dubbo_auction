package org.web.auction.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.web.auction.service.UserService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Controller
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; //login.html
    }

    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, Model model){
        String errorException = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (errorException!=null){
            if (UnknownAccountException.class.getName().equals(errorException)) {
                model.addAttribute("errorMsg","帐号错误");
            } else if(IncorrectCredentialsException.class.getName().equals(errorException)){
                model.addAttribute("errorMsg","密码错误");
            } else if("InValidateCode".equals(errorException)){
                model.addAttribute("errorMsg","验证码错误");
            }
        }
        return "login";
    }
//    @RequestMapping("/doLogin")
//    public String doLogin(String username,
//                          String password,
//                          String valideCode,
//                          HttpSession session, Model model){
//        //1. 先验证验证码
//        if (!valideCode.equals(session.getAttribute("vrifyCode"))) {
//            model.addAttribute("msg", "验证码不正确");
//            return "login";  //login.html
//        }
//        //2.查询数据库：username,password
//        User loginUser = userService.login(username, password);
//        if (loginUser != null) {
//            session.setAttribute("user", loginUser);
//            return "redirect:/queryAuctions";  //AuctionController---> index.jsp
//        } else {
//            model.addAttribute("msg", "用户名或密码不正确");
//            return "login";
//        }
//    }

//    @RequestMapping("/logout")
//    public String logout(HttpSession session){
//        session.invalidate();
//        return "login";
//    }

    @Autowired
    private DefaultKaptcha captchaProducer;

    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            httpServletRequest.getSession().setAttribute("vrifyCode", createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
