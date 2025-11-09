package com.example.demo.schedule.service;

import com.example.demo.schedule.domain.Schedule;
import com.example.demo.schedule.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    // 勤務追加
    public void addSchedule(Schedule schedule) {
        scheduleMapper.insert(schedule);
    }

    // 全勤務取得
    public List<Schedule> getAllSchedules() {
        return scheduleMapper.findAll();
    }
}
