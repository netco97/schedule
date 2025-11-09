package com.example.demo.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // ホーム画面表示
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "勤怠管理システムへようこそ！");
        return "home/index"; // home/index.html を表示
    }
}
