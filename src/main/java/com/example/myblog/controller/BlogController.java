package com.example.myblog.controller;

import com.example.myblog.model.Comment;
import com.example.myblog.model.Post;
import com.example.myblog.model.User;
import com.example.myblog.service.CommentService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Controller
@RequestMapping(value = {"/home","/"})
public class BlogController {
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/resources/static/uploads/";

    @Autowired
    public BlogController(PostService postService){
        this.postService = postService;
    }


    @GetMapping
    public String showHomePage(Model model, HttpSession session){
        List<Post> list = postService.findAllAndSort();

        //fake current_user (hack-mode)
//        session.setAttribute("current_user", userService.findById(0));
        //----------------------------

        model.addAttribute("users", userService.findAll());
        model.addAttribute("postList", list);
        return "home";
    }

    @PostMapping
    public String handleComment(Model model ,HttpSession session ,
                                @RequestParam("comment") String comment_content,
                                @RequestParam("date") String cre_date,
                                @RequestParam("postId") String postId){
        List<Post> list = postService.findAllAndSort();
        Comment comment = new Comment();
        User current_user = (User)session.getAttribute("current_user");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        try {
            Date date = format.parse(cre_date);
            comment.setCreatedDate(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        comment.setUser(userService.findById(current_user.getId()));
        comment.setPost(postService.findById(Integer.parseInt(postId)));
        comment.setContent(comment_content);

        commentService.save(comment);
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
        String fileName = "", dateR="";
        User user = (User) session.getAttribute("current_user");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
        try {
            Date date1 = formatter.parse(date);
            dateR = formatter.format(date1);
            post.setDate(dateR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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


        postService.save(post);
        return "redirect:/";
    }

}
