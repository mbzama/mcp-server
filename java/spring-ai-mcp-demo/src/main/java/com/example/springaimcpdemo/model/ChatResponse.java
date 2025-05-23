package com.example.springaimcpdemo.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response model for chat interactions
 */
public class ChatResponse {
    private String response;
    private String model;
    private LocalDateTime timestamp;
    private List<String> toolsUsed;
    private boolean success;
    private String error;

    public ChatResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }

    public ChatResponse(String response) {
        this();
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getToolsUsed() {
        return toolsUsed;
    }

    public void setToolsUsed(List<String> toolsUsed) {
        this.toolsUsed = toolsUsed;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        this.success = false;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "response='" + response + '\'' +
                ", model='" + model + '\'' +
                ", timestamp=" + timestamp +
                ", toolsUsed=" + toolsUsed +
                ", success=" + success +
                ", error='" + error + '\'' +
                '}';
    }
}