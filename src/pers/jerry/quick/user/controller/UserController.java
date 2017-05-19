// Copyright (c) 1998-2017 Core Solutions Limited. All rights reserved.
// ============================================================================
// CURRENT VERSION CNT.5.0.1
// ============================================================================
// CHANGE LOG
// CNT.5.0.1 : 2017-XX-XX, jerry.zhao, creation
// ============================================================================

/**
 * @author jerry.zhao
 *
 */
package pers.jerry.quick.user.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.jerry.quick.user.domain.User;
import pers.jerry.quick.user.service.UserService;
import pers.jerry.quick.util.CookieUtils;
import pers.jerry.quick.util.MailUtils;
import pers.jerry.quick.util.UserUtils;
import pers.jerry.quick.util.ValidationUtils;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

    // go login page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin() {
        return "user/userLogin";
    }

    // go signin page
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String userSignin() {
        return "user/userSignUp";
    }

    @RequestMapping(value = "/handleUserLogin", method = RequestMethod.POST)
    @ResponseBody
    public String handleUserLogin(User user, HttpServletRequest request) throws NoSuchAlgorithmException {
        if (ValidationUtils.checkLoginForm(user)) {
            final String passwordMD5 = UserUtils.toMD5Code(user.getPassword());
            user.setPasswordMD5(passwordMD5);
            final User loginUser = userService.getLoginUser(user);
            if (loginUser != null) {
                return "success";
            }
        }
        return "fail";

    }

    // handle signin page
    @RequestMapping(value = "/handleUserSignin", method = RequestMethod.POST)
    @ResponseBody
    public String handleUserSignin(String captcha, User user, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (ValidationUtils.checkSignUpForm(user, captcha)) {
            if (ValidationUtils.checkCpatchaValidity(captcha,
                    (String) request.getSession().getAttribute(user.getEmail()))) {
                request.getSession().removeAttribute(user.getEmail());
                user.setCreatedate(new Timestamp(System.currentTimeMillis()));
                user.setLastvisit(new Timestamp(System.currentTimeMillis()));
                user.setLastip(UserUtils.getIpAddr(request));
                user.setUserGroup(User.USER);
                final String passwordMD5 = UserUtils.toMD5Code(user.getPassword());
                user.setPasswordMD5(passwordMD5);
                userService.insertUser(user);
                final String userCookie = user.getId() + "_" + user.getName() + "_" + passwordMD5;
                final String cookieValue = UserUtils.base64Encoder(userCookie);
                CookieUtils.addCookie(response, User.JERRY_HOME_USER_COOKIE, cookieValue, null);
                request.getSession().removeAttribute(User.USER);
                return "success";
            } else {
                return "captchaError";
            }
        }
        return "fail";
    }

    @RequestMapping(value = "/sendCaptcha", method = RequestMethod.GET)
    @ResponseBody
    public String sendCaptcha(String email, HttpSession session) {
        try {
            final String captcha = MailUtils.sendMail(email);
            session.setAttribute(email, captcha);
            return "success";
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (final MessagingException e) {
            e.printStackTrace();
        }
        return "fail";
    }

}
