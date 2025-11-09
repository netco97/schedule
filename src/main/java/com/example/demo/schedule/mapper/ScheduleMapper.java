package com.example.demo.schedule.mapper;

import com.example.demo.schedule.domain.Schedule;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ScheduleMapper {
    void insert(Schedule schedule);  // 新規スケジュール追加
    List<Schedule> findAll();        // 全スケジュール取得
}
