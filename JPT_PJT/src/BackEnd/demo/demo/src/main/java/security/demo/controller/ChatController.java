package security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import security.demo.domain.DTO.*;
import security.demo.domain.service.ChatService;
import security.demo.domain.service.UserService;

@RestController
@RequiredArgsConstructor
public class ChatController {

//    private final UserService userService;
    private final ChatService chatService;

    @PostMapping("/create/chatbox")
    public ResponseEntity<ChatBoxResponseDto> createbox(ChatBoxRequestDto chatBoxRequestDto) {
        System.out.println("create start");
        return ResponseEntity.ok(chatService.createChatBox(chatBoxRequestDto));
    }

    @PostMapping("/create/chat")
    public ResponseEntity<ChatResponseDto> createtalk(ChatRequestDto chatRequestDto) {
        return ResponseEntity.ok(chatService.createChat(chatRequestDto));
    }

    @GetMapping("/searchbox/{username}")
    public ResponseEntity<UserSearchChatBoxDto> searchChat(@PathVariable("username") String username) {
        return ResponseEntity.ok(chatService.searchChatbox(username));
    }

}
