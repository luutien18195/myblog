package com.example.myblog.controller;

import com.example.myblog.model.Post;
import com.example.myblog.service.PostService;
import com.example.myblog.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(value = {"/home","/"})
public class BlogController {
    private PostService postService;

    @Autowired
    public BlogController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public String showHomePage(Model model){
        List<Post> list = postService.findAllAndSort();

        model.addAttribute("postList", list);
        return "home";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String showPostPage(Model model){
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String createNewPost(Model model, @ModelAttribute("post") Post post, @RequestParam String date){
        post.setDate(date);
        postService.save(post);
        String message, status;
        message = "successfully";

        model.addAttribute("message", message);
        return "new_post";
    }
}
