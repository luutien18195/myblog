package com.example.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/home","/"})
public class BlogController {
    @GetMapping
    public String showHomePage(Model model){
        return "home";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String showPostPage(Model model){
        return "new_post";
    }
}
