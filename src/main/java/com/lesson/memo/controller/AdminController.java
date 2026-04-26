package com.lesson.memo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Controller
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin/signup")
    public String signup(Admin admin) {
    	admin.setCreatedAt(LocalDateTime.now());
    	admin.setUpdatedAt(LocalDateTime.now());
    	
    	String encodedPassword = passwordEncoder.encode(admin.getPassword());

        adminRepository.save(admin);

        return "redirect:/admin/signin";
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