package com.example.myblog.controller;

import com.example.myblog.model.Post;
import com.example.myblog.model.User;
import com.example.myblog.service.PostService;
import com.example.myblog.service.UserService;
import com.example.myblog.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = {"/home","/"})
public class BlogController {
    private PostService postService;

    @Autowired
    private UserService userService;
    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/resources/static/uploads/";

    @Autowired
    public BlogController(PostService postService){
        this.postService = postService;
    }


    @GetMapping
    public String showHomePage(Model model, HttpSession session){
        List<Post> list = postService.findAllAndSort();

        //fake current_user
//        session.setAttribute("current_user", userService.findById(0));
        //----------------------------

        model.addAttribute("users", userService.findAll());
        model.addAttribute("postList", list);
        return "home";
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public String showPostPage(Model model){
        model.addAttribute("post", new Post());
        return "new_post";
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String createNewPost(Model model, @ModelAttribute("post") Post post,
                                @RequestParam String date, @RequestParam(value = "file") MultipartFile file,
                                HttpSession session){
        Random rd = new Random();
        int randomNum = rd.nextInt(1000000);
        String fileName = "";
        User user = (User) session.getAttribute("current_user");
        if(!file.isEmpty()){
            try {
                fileName = randomNum+"_"+file.getOriginalFilename();
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + fileName);
                Files.write(path,bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        post.setUser(userService.findUserByUserName(user.getUsername()));
        post.setImage(fileName);
        post.setDate(date);
        postService.save(post);
        return "redirect:/";
    }

}
