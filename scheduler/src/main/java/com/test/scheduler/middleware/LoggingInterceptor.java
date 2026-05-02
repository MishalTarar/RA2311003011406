package com.test.scheduler.middleware;

import com.test.scheduler.service.LoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final LoggingService loggingService;

    public LoggingInterceptor(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;

        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        String logMessage = method + " " + uri + " " + status + " " + duration + "ms";

        if (ex != null) {
            logMessage += " | ERROR: " + ex.getMessage();
        }

        loggingService.sendLog("backend", "info", logMessage);

        System.out.println("FINAL LOG: " + logMessage);
    }
}