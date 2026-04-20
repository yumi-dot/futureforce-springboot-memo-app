package com.lesson.memo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(Admin admin) {

    	String password = admin.getPassword();
	    String encodedPassword = passwordEncoder.encode(password);

	    admin.setPassword(encodedPassword);

	    adminRepository.save(admin);
    }
}