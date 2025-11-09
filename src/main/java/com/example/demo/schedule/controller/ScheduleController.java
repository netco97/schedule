package com.example.demo.schedule.controller;

import com.example.demo.schedule.domain.Schedule;
import com.example.demo.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 勤務表一覧ページ表示
    @GetMapping("/schedule/list")
    public String list(Model model) {
        List<Schedule> schedules = scheduleService.getAllSchedules()
            .stream()
            .filter(s -> s.getWorkDate() != null)
            .toList();

        model.addAttribute("schedules", schedules);
        model.addAttribute("days", IntStream.rangeClosed(1, 30).boxed().toList());

        return "schedule/list";
    }

    // 新しいスケジュール追加
    @PostMapping("/schedule/add")
    public String addSchedule(Schedule schedule, Principal principal) {
        // ログイン　ユーザー名をスケジュールに設定
        schedule.setUsername(principal.getName());

        if (schedule.getWorkDate() == null) {
            schedule.setWorkDate(java.time.LocalDate.now());
        }

        scheduleService.addSchedule(schedule);
        return "redirect:/schedule/list";
    }
}
