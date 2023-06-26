package security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import security.demo.domain.DTO.*;
import security.demo.domain.Entity.User;
import security.demo.domain.service.ChatService;
import security.demo.global.jwt.annotation.JwtAuth;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/create/chatbox")
    public ResponseEntity<ChatBoxResponseDto> createbox(@JwtAuth User user,ChatBoxRequestDto chatBoxRequestDto) {
        System.out.println("create start");
        return ResponseEntity.ok(chatService.createChatBox(chatBoxRequestDto, user.getUsername()));
    }

    @PostMapping("/create/chat")
    public ResponseEntity<ChatResponseDto> createtalk(@JwtAuth User writer, ChatRequestDto chatRequestDto) {
        return ResponseEntity.ok(chatService.createChat(chatRequestDto, writer.getUsername()));
    }

    @GetMapping("/searchbox")
    public ResponseEntity<UserSearchChatBoxDto> searchChat(@JwtAuth User user) {
        return ResponseEntity.ok(chatService.searchChatbox(user.getUsername()));
    }

}
