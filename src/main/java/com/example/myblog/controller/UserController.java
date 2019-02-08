package com.example.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String showLoginPage(Model model){

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String showRegisterPage(Model model){

        return "register";
    }
}
