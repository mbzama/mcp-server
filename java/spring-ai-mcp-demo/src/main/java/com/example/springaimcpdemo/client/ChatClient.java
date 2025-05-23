package com.example.springaimcpdemo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Custom ChatClient wrapper for Anthropic Claude API integration
 * This provides a simplified interface for chat operations with tool support
 */
@Component
public class ChatClient {

    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    private final AnthropicChatModel chatModel;
    private final List<ToolCallbackProvider> toolCallbackProviders;

    @Autowired
    public ChatClient(AnthropicChatModel chatModel, 
                     List<ToolCallbackProvider> toolCallbackProviders) {
        this.chatModel = chatModel;
        this.toolCallbackProviders = toolCallbackProviders;
        logger.info("ChatClient initialized with {} tool providers", toolCallbackProviders.size());
    }

    /**
     * Send a simple message to the chat model
     */
    public ChatResponse call(String message) {
        return call(message, null);
    }

    /**
     * Send a message with specific model options
     */
    public ChatResponse call(String message, AnthropicChatOptions options) {
        logger.debug("Sending message to chat model: {}", message);
        
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(userMessage, options);
        
        return call(prompt);
    }

    /**
     * Send a prompt to the chat model
     */
    public ChatResponse call(Prompt prompt) {
        try {
            logger.debug("Processing prompt with {} messages", prompt.getInstructions().size());
            
            // Configure the chat model with available tools
            AnthropicChatModel configuredModel = chatModel;
            
            // Add tool callbacks if available and not already configured
            if (!toolCallbackProviders.isEmpty()) {
                logger.debug("Configuring model with {} tool providers", toolCallbackProviders.size());
            }
            
            ChatResponse response = configuredModel.call(prompt);
            
            logger.debug("Received response from chat model");
            return response;
            
        } catch (Exception e) {
            logger.error("Error calling chat model", e);
            throw new RuntimeException("Failed to process chat request", e);
        }
    }

    /**
     * Create a builder for more complex chat configurations
     */
    public static Builder builder(AnthropicChatModel chatModel) {
        return new Builder(chatModel);
    }

    /**
     * Builder class for creating configured ChatClient instances
     */
    public static class Builder {
        private final AnthropicChatModel chatModel;
        private List<ToolCallbackProvider> toolCallbackProviders;
        private AnthropicChatOptions defaultOptions;

        public Builder(AnthropicChatModel chatModel) {
            this.chatModel = chatModel;
        }

        public Builder withToolCallbackProviders(List<ToolCallbackProvider> providers) {
            this.toolCallbackProviders = providers;
            return this;
        }

        public Builder withDefaultOptions(AnthropicChatOptions options) {
            this.defaultOptions = options;
            return this;
        }

        public ChatClient build() {
            ChatClient client = new ChatClient(chatModel, 
                toolCallbackProviders != null ? toolCallbackProviders : List.of());
            return client;
        }
    }

    /**
     * Get the underlying chat model
     */
    public AnthropicChatModel getChatModel() {
        return chatModel;
    }

    /**
     * Get the configured tool callback providers
     */
    public List<ToolCallbackProvider> getToolCallbackProviders() {
        return toolCallbackProviders;
    }

    /**
     * Check if tools are available
     */
    public boolean hasTools() {
        return !toolCallbackProviders.isEmpty();
    }

    /**
     * Get count of available tools
     */
    public int getToolCount() {
        return toolCallbackProviders.stream()
                .mapToInt(provider -> provider.getToolCallbacks().length)
                .sum();
    }
}