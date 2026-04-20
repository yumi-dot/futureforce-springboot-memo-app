package com.lesson.memo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.service.AdminService;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/signup")
    public String signup(Admin admin) {

        adminService.register(admin);

        return "redirect:/admin/signup";
    }
    
    @GetMapping("/admin/signup")
    public String showSignupForm() {
        return "admin/signup";
    }
    
    @GetMapping("/admin/signin")
    public String showSigninForm() {
        return "admin/signin";
    }
}