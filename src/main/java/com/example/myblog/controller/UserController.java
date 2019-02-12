package com.example.myblog.controller;

import com.example.myblog.model.Comment;
import com.example.myblog.model.User;
import com.example.myblog.service.CommentService;
import com.example.myblog.service.PostService;
import com.example.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;


    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createNewAccount(Model model,@Valid @ModelAttribute("user") User user,
                              BindingResult bindingResult){
       this.userService.validate(user, bindingResult);
       if(bindingResult.hasFieldErrors()){
           return "register";
       }
       else{
           user.setAvatar("https://cdn0.iconfinder.com/data/icons/social-flat-rounded-rects/512/anonymous-512.png");
           this.userService.save(user);
           return "redirect:/login";
       }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(Model model){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password, HttpSession session){
        if(this.userService.findUserByUsernameAndPassword(username,password)!=null){
            User user = this.userService.findUserByUsernameAndPassword(username,password);
            session.setAttribute("current_user", user);
            return "redirect:/home";
        }else{
            model.addAttribute("error","invalid user!, please try again!");
            return "login";
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpSession session){
        session.removeAttribute("current_user");
        return "redirect:/";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String showUserPage(@PathVariable int id, Model model){
        model.addAttribute("d_user",this.userService.findById(id));
        model.addAttribute("posts", this.postService.findPostsByUserIdAndOrderByIdDesc(id));
        model.addAttribute("users", this.userService.findAll());
        return "user";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public String updateAvatar(@PathVariable int id, Model model, HttpSession session,
                               @RequestParam(value = "avatar") MultipartFile multipartFile){
        String directory = BlogController.uploadDirectory;
        Random rd = new Random();
        int randomNumber = rd.nextInt(1000000);
        String fileName = "";
        User user = (User) session.getAttribute("current_user");
        User q_user = null;
        if(!multipartFile.isEmpty()){
            try {
                fileName = randomNumber +"-avatar-"+ multipartFile.getOriginalFilename();
                byte[] bytes = multipartFile.getBytes();
                Path path = Paths.get(directory + fileName);

                Files.write(path,bytes);
                q_user = userService.findById(user.getId());
                q_user.setAvatar("/images/"+fileName);
                userService.save(q_user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        session.setAttribute("current_user",q_user);
        model.addAttribute("d_user",this.userService.findById(id));
        model.addAttribute("posts", this.userService.findById(id).getPosts());
        model.addAttribute("users", this.userService.findAll());
        return "user";
    }

    @RequestMapping(value = "/user/{id}/comment", method = RequestMethod.POST)
    public String commentResolve(@PathVariable int id, Model model, HttpSession session,
                                 @RequestParam("comment") String comment_content,
                                 @RequestParam("postId") String postId,
                                 @RequestParam("date") String date_str){
        Comment comment = new Comment();
        User user = (User) session.getAttribute("current_user");
        comment.setContent(comment_content);
        comment.setPost(postService.findById(Integer.parseInt(postId)));
        comment.setUser(userService.findById(user.getId()));

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        try {
            Date date = format.parse(date_str);
            comment.setCreatedDate(format.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        commentService.save(comment);

        model.addAttribute("d_user",this.userService.findById(id));
        model.addAttribute("posts", this.userService.findById(id).getPosts());
        model.addAttribute("users", this.userService.findAll());
        return "user";
    }


}
