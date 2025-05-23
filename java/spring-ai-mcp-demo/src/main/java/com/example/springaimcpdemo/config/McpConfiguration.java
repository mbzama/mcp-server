package com.example.springaimcpdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springaimcpdemo.service.CalculatorService;
import com.example.springaimcpdemo.service.SystemInfoService;

/**
 * Configuration class for MCP (Model Context Protocol) setup
 */
@Configuration
public class McpConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(McpConfiguration.class);

    /**
     * Configures calculator tools for the MCP server
     */
    @Bean
    public ToolCallbackProvider calculatorTools(CalculatorService calculatorService) {
        logger.info("Registering calculator tools for MCP server");
        return MethodToolCallbackProvider.builder()
                .toolObjects(calculatorService)
                .build();
    }

    /**
     * Configures system info tools for the MCP server
     */
    @Bean
    public ToolCallbackProvider systemInfoTools(SystemInfoService systemInfoService) {
        logger.info("Registering system info tools for MCP server");
        return MethodToolCallbackProvider.builder()
                .toolObjects(systemInfoService)
                .build();
    }
}