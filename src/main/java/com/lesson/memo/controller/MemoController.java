package com.lesson.memo.controller;


import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lesson.memo.model.Memo;
import com.lesson.memo.model.Priority;
import com.lesson.memo.repository.MemoRepository;

@Controller
@RequestMapping("/memo")
public class MemoController {

    @Autowired
    private MemoRepository memoRepository;

    @GetMapping
    public String list(Model model) {
        List<Memo> memos = memoRepository.findAll();
        memos.sort((a, b) -> {
            return getOrder(a.getPriority()) - getOrder(b.getPriority());
        });
        model.addAttribute("memos", memos);
        return "memo-list";
    }
    
    //修正追加以下
    private int getOrder(Priority priority) {
        switch (priority) {
            case HIGH:
                return 1;
            case MIDDLE:
                return 2;
            case LOW:
                return 3;
            default:
                return 4;
        }
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("memo", new Memo());
        model.addAttribute("priorities",Priority.values());
        return "memo-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Memo memo,
                        BindingResult result, 
                        Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("priorities", Priority.values());
            return "memo-form";
        }
        memo.setCreatedAt(LocalDateTime.now());
        memo.setUpdatedAt(LocalDateTime.now());
        memoRepository.save(memo);
        
        return "redirect:/memo";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        Memo memo = memoRepository.findById(id).orElse(null);
        
        if (memo == null) {
            return "not-found"; // エラー画面にリダイレクト
        }

        model.addAttribute("memo", memo);
        return "memo-detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Memo memo = memoRepository.findById(id).orElse(null);
        if (memo == null) {
            return "not-found";
        }
        model.addAttribute("memo", memo);
        return "memo-form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
            @ModelAttribute @Valid Memo memo,
            BindingResult result) {

    	if (result.hasErrors()) {
            return "memo-form";
        }

        Memo memoToUpdate = memoRepository.findById(id).orElse(null);

        if (memoToUpdate == null) {
            return "not-found";
        }

        memoToUpdate.setTitle(memo.getTitle());
        memoToUpdate.setContent(memo.getContent());
        memoToUpdate.setUpdatedAt(LocalDateTime.now());
        memoToUpdate.setPriority(memo.getPriority());
        memoRepository.save(memoToUpdate);

        return "redirect:/memo/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return "redirect:/memo";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String keyword,
                         Model model) {

        List<Memo> memos;

        if (keyword == null || keyword.isBlank()) {
            memos = memoRepository.findAll();
        } else {
            memos = memoRepository.findByTitleContainingOrContentContaining(keyword, keyword);
        }
            memos.sort((a, b) -> {
            	if(a.getPriority() != b.getPriority()) {
            		return getOrder(a.getPriority()) - getOrder(b.getPriority());
            	}
            	return b.getUpdatedAt().compareTo(a.getUpdatedAt());
            });

        model.addAttribute("memos", memos);
        model.addAttribute("keyword", keyword);

        return "memo-list";
    }
}
