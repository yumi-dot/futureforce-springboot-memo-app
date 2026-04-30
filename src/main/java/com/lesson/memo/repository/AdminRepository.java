package com.lesson.memo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lesson.memo.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    //メアドから検索
    Admin findByEmail(String email);
}