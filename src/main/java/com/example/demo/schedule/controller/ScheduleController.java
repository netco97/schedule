package com.example.demo.schedule.controller;

import com.example.demo.schedule.domain.Schedule;
import com.example.demo.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 勤務表ページ表示
    @GetMapping("/")
    public String list(Model model) {
        // すべてのスケジュール取得
        List<Schedule> schedules = scheduleService.getAllSchedules()
            .stream()
            .filter(s -> s.getWorkDate() != null) // null安全
            .toList();

        model.addAttribute("schedules", schedules);

        // 1~30日の日付リストを作成
        model.addAttribute("days", IntStream.rangeClosed(1,30).boxed().toList());

        return "schedule/list"; // schedule/list.html を表示
    }

    // 新しいスケジュール追加
    @PostMapping("/add")
    public String addSchedule(Schedule schedule) {
        // workDateがnullの場合は今日の日付を設定
        if (schedule.getWorkDate() == null) {
            schedule.setWorkDate(java.time.LocalDate.now());
        }
        scheduleService.addSchedule(schedule);
        return "redirect:/"; // 再表示
    }
}
