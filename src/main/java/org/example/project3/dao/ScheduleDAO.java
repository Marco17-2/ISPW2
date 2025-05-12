package org.example.project3.dao;

import org.example.project3.model.Schedule;

public interface ScheduleDAO {
    void addSchedule(Schedule schedule);
    void updateSchedule(Schedule schedule);
    void deleteSchedule(Schedule schedule);
}
