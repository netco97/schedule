package com.example.demo.home.controller;

import com.example.demo.schedule.domain.Schedule;
import com.example.demo.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final ScheduleService scheduleService;

    // ルートアクセス時に /home にリダイレクト
    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/home";
    }

    // ホームページ表示
    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        // 認証済みユーザーか確認
        if (authentication != null && authentication.isAuthenticated()) {
            // ユーザー名だけ取得してモデルに追加
            String username = authentication.getName();
            // 最近勤務表５個
            List<Schedule> recentSchedules = scheduleService.getRecentSchedules(5,username);

            model.addAttribute("username", username);
            model.addAttribute("recentSchedules", recentSchedules);
        } else {
            model.addAttribute("username", "ゲスト"); // 認証されていない場合
            
        }

        return "home/index"; // templates/home/index.html
    }
}
