package security.demo.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.demo.domain.DTO.*;
import security.demo.domain.Entity.Chat;
import security.demo.domain.Entity.Chatbox;
import security.demo.domain.Entity.User;
import security.demo.domain.repository.ChatRepository;
import security.demo.domain.repository.ChatboxRepository;
import security.demo.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatboxRepository chatboxRepository;
    private final ChatRepository chatRepository;

    @Transactional
    public ChatBoxResponseDto createChatBox(ChatBoxRequestDto chatBoxRequestDto, String username) {
        System.out.println("find user");
        User user = userRepository.findByUsername(username).get();
        System.out.println(user.getUsername());
        Chatbox chatbox = Chatbox.builder()
                .user(user)
                .title(chatBoxRequestDto.getTitle()).build();
        Chatbox savedChatbox = chatboxRepository.save(chatbox);
        return ChatBoxResponseDto.builder()
                .username(savedChatbox.getUser().getUsername())
                .id(savedChatbox.getId())
                .title(savedChatbox.getTitle()).build();
    }

    public ChatResponseDto createChat(ChatRequestDto chatRequestDto, String username) {
        Chatbox chatbox = chatboxRepository.findByIdAndTitle(chatRequestDto.getTalkboxId(), chatRequestDto.getTitle());
        Chat chat = Chat.builder()
                .writer(username)
                .talk(chatRequestDto.getTalk())
                .chatbox(chatbox).build();
        Chat savedChat = chatRepository.save(chat);

        return ChatResponseDto.builder()
                .writer(savedChat.getWriter())
                .talk(savedChat.getTalk()).build();
    }

    public UserSearchChatBoxDto searchChatbox(String username) {
        User user = userRepository.findByUsername(username).get();
        List<Chatbox> chatboxes = chatboxRepository.findByUser(user);
        List<ChatBoxResponseDto> newchatbox = new ArrayList<>();
        for (Chatbox chatbox : chatboxes) {
            List<ChatResponseDto> newchat = new ArrayList<>();
            for (Chat chat : chatbox.getTalklist()) {
                ChatResponseDto chatResponseDto = ChatResponseDto.builder()
                        .talk(chat.getTalk())
                        .writer(chat.getWriter()).build();
                newchat.add(chatResponseDto);
            }
            ChatBoxResponseDto chatBoxResponseDto = ChatBoxResponseDto.builder()
                    .id(chatbox.getId())
                    .username(chatbox.getUser().getUsername())
                    .title(chatbox.getTitle())
                    .talklist(newchat).build();
            newchatbox.add(chatBoxResponseDto);
        }
        return UserSearchChatBoxDto.builder()
                .username(username)
                .chatbox(newchatbox).build();
    }

}
