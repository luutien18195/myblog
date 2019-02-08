package com.example.myblog.controller;

import com.example.myblog.model.User;
import com.example.myblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    private UserService userService;

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
       new User().validate(user, bindingResult);
       if(bindingResult.hasFieldErrors()){
           return "register";
       }
       else{
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
        if(this.userService.findByUsernameAndPassword(username,password)!=null){
            User user = this.userService.findByUsernameAndPassword(username,password);
            session.setAttribute("current_user", user);
            return "redirect:/home";
        }else{
            model.addAttribute("error","invalid user!, please try again!");
            return "login";
        }

    }
}
