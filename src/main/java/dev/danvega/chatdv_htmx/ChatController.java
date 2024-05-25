package dev.danvega.chatdv_htmx;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxResponse;
import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
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

    /*
     * This is the method we were using if we were just returning the response
     */
    @PostMapping("/old-generate")
    public String oldGenerate(@RequestParam String message, Model model) {
        String response = chatClient.call(message);
        model.addAttribute("response",response);

        // template :: fragmentName
        return "response :: responseFragment";
    }


    /*
     * this method allows us to return 2 views in one call
     */
    @HxRequest
    @PostMapping("/generate")
    public HtmxResponse generate(@RequestParam String message, Model model) {
        log.info("User Message: {}", message);
        String response = chatClient.call(message);
        model.addAttribute("response",response);
        model.addAttribute("message",message);

        return HtmxResponse.builder()
                .view("response :: responseFragment")
                .view("todays-message-list :: messageFragment")
                .build();
    }

}
