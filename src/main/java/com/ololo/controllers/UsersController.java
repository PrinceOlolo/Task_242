package com.ololo.controllers;

import com.ololo.model.User;
import com.ololo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UsersController {

    private final UserService userService;
   // @GetMapping("/")
   // public String index(){
    //    return "redirect:/admin/users";
   // }

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/user/page")
    public String userP(Principal principal, Model model) {
    User user = (User) userService.findByEmail(principal.getName());
    model.addAttribute("user", user);
        return "users/userPage";
    }

//    @GetMapping("/user/{id}")
//    public String userPage(@PathVariable("id") int id, Model model) {
//        model.addAttribute("user", userService.findOne(id));
//        return "users/userPage";
//    }

    @GetMapping("/admin/users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "users/show";
    }

    @GetMapping("/admin/users/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/admin/users")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/new";

        userService.saveUser(user);

        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findOne(id));
        return "users/edit";
    }

    @PatchMapping("/admin/users/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "users/edit";

        userService.update(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }
}
