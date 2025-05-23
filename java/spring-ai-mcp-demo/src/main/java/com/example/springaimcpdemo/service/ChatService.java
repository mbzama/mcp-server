package com.example.springaimcpdemo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springaimcpdemo.client.ChatClient;
import com.example.springaimcpdemo.model.ChatRequest;

/**
 * Service for handling chat interactions with the AI model
 */
@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatClient chatClient;

    @Autowired
    public ChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Process a chat request with tool support
     */
    public com.example.springaimcpdemo.model.ChatResponse processChat(ChatRequest request) {
        com.example.springaimcpdemo.model.ChatResponse response = 
            new com.example.springaimcpdemo.model.ChatResponse();
        
        try {
            logger.info("Processing chat request: {}", request);

            // Enable function calling (tools) if requested
            if (request.getUseTools()) {
                logger.debug("Tools are enabled for this request");
            }

            // Create the prompt with user message
            Prompt prompt = new Prompt(new UserMessage(request.getMessage()));

            // Get AI response using our custom ChatClient
            org.springframework.ai.chat.model.ChatResponse aiResponse = chatClient.call(prompt);
            
            // Extract response content safely
            String responseContent = "";
            if (aiResponse != null && aiResponse.getResult() != null && aiResponse.getResult().getOutput() != null) {
                responseContent = aiResponse.getResult().getOutput().toString();
            }
            response.setResponse(responseContent);
            response.setModel(request.getModel());

            // Extract tool usage information if available
            try {
                if (aiResponse.getResult() != null && aiResponse.getResult().getOutput() != null) {
                    // Try to extract tool calls if they exist
                    // Note: Tool call extraction may vary based on Spring AI version
                    logger.debug("Response received successfully");
                }
            } catch (Exception e) {
                logger.debug("Could not extract tool usage information: {}", e.getMessage());
            }

            logger.info("Chat processed successfully");

        } catch (Exception e) {
            logger.error("Error processing chat request", e);
            response.setError("Error processing your request: " + e.getMessage());
        }

        return response;
    }

    /**
     * Get available models (for future extension)
     */
    public List<String> getAvailableModels() {
        return List.of("claude-3-haiku-20240307", "claude-3-sonnet-20240229", "claude-3-opus-20240229");
    }
}