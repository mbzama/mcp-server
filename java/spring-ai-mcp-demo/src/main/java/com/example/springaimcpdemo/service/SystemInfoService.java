package com.example.springaimcpdemo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

/**
 * System information service providing system-related tools for the LLM
 */
@Service
public class SystemInfoService {

    @Tool(description = "Get current date and time")
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Tool(description = "Get system information including Java version and OS details")
    public String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        info.append("Operating System: ").append(System.getProperty("os.name")).append("\n");
        info.append("OS Version: ").append(System.getProperty("os.version")).append("\n");
        info.append("Architecture: ").append(System.getProperty("os.arch")).append("\n");
        info.append("Available Processors: ").append(Runtime.getRuntime().availableProcessors()).append("\n");
        info.append("Max Memory: ").append(Runtime.getRuntime().maxMemory() / 1024 / 1024).append(" MB");
        return info.toString();
    }

    @Tool(description = "Generate a greeting message")
    public String generateGreeting(String name) {
        if (name == null || name.trim().isEmpty()) {
            name = "there";
        }
        return String.format("Hello %s! Welcome to the Spring AI MCP Demo. Current time: %s", 
                           name.trim(), getCurrentDateTime());
    }
}