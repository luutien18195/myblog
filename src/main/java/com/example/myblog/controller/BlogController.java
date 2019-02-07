package com.example.myblog.controller;

import com.example.myblog.model.Post;
import com.example.myblog.service.PostService;
import com.example.myblog.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(value = {"/home","/"})
public class BlogController {
    private PostService postService;
    public static String uploadDirectory = "user.dir" + "/uploads";

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
    public String createNewPost(Model model, @ModelAttribute("post") Post post,
                                @RequestParam String date, @RequestParam(value = "file") MultipartFile file){
        if(file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + file.getOriginalFilename());
                Files.write(path,bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        post.setDate(date);
        postService.save(post);
        return "redirect:/";
    }

    public File getFolderUpload() {
        File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
        if (!folderUpload.exists()) {
            folderUpload.mkdirs();
        }
        return folderUpload;
    }
}
