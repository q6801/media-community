package com.example.mediacommunity.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

@Slf4j
public class LoggingTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> callerThreadContext = MDC.getCopyOfContextMap();
        return () -> {
            MDC.setContextMap(callerThreadContext);
            runnable.run();
        };
    }
}
