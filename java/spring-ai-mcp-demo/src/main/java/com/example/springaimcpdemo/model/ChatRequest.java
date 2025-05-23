package com.example.springaimcpdemo.model;

/**
 * Request model for chat interactions
 */
public class ChatRequest {
    private String message;
    private String model = "claude-3-haiku-20240307";
    private Boolean useTools = Boolean.TRUE;

    public ChatRequest() {}

    public ChatRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getUseTools() {
        return useTools != null ? useTools : Boolean.TRUE;
    }

    public void setUseTools(Boolean useTools) {
        this.useTools = useTools;
    }

    @Override
    public String toString() {
        return "ChatRequest{" +
                "message='" + message + '\'' +
                ", model='" + model + '\'' +
                ", useTools=" + useTools +
                '}';
    }
}