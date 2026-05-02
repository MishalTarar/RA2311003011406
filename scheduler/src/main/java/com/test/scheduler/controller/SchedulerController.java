package com.test.scheduler.controller;

import com.test.scheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping
    public List<Map<String, Object>> getSchedule() {
        return schedulerService.generateSchedule();
    }
}