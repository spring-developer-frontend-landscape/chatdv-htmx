package dev.danvega.chatdv_htmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("")
    public String home() {
        return "index";
    }

    @PostMapping("/generate")
    public String generate(@RequestParam String message, Model model) {
        log.info("User Message: {}", message);
        String response = chatClient.call(message);
        model.addAttribute("response",response);
        model.addAttribute("message",message);

        // template :: fragmentName
        return "response :: responseFragment";
    }

}
