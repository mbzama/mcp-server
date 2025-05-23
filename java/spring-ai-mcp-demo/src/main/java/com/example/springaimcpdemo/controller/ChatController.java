package com.example.springaimcpdemo.controller;

import com.example.springaimcpdemo.model.ChatRequest;
import com.example.springaimcpdemo.model.ChatResponse;
import com.example.springaimcpdemo.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling chat UI and API endpoints
 */
@Controller
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Main chat page
     */
    @GetMapping("/")
    public String chatPage(Model model) {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setUseTools(Boolean.TRUE); // Ensure it's properly initialized
        model.addAttribute("chatRequest", chatRequest);
        model.addAttribute("availableModels", chatService.getAvailableModels());
        return "chat";
    }

    /**
     * Handle chat form submission
     */
    @PostMapping("/chat")
    public String handleChat(@ModelAttribute ChatRequest request, Model model) {
        logger.info("Received chat request via form: {}", request);

        ChatResponse response = chatService.processChat(request);

        model.addAttribute("chatRequest", request);
        model.addAttribute("chatResponse", response);
        model.addAttribute("availableModels", chatService.getAvailableModels());

        return "chat";
    }

    /**
     * REST API endpoint for chat
     */
    @PostMapping("/api/chat")
    @ResponseBody
    public ResponseEntity<ChatResponse> apiChat(@RequestBody ChatRequest request) {
        logger.info("Received chat request via API: {}", request);

        ChatResponse response = chatService.processChat(request);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/api/health")
    @ResponseBody
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Spring AI MCP Demo is running!");
    }

    /**
     * Get available models endpoint
     */
    @GetMapping("/api/models")
    @ResponseBody
    public ResponseEntity<java.util.List<String>> getModels() {
        return ResponseEntity.ok(chatService.getAvailableModels());
    }
}