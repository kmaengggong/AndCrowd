package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.ChatMessageDTO;
import com.fiveis.andcrowd.service.and.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/and/message")
@RestController
@Slf4j
public class ChatRestController {
    private final ChatService chatService;

    @PostMapping()
    public void addChat(@RequestBody ChatMessageDTO chatMessageDTO){
        log.info(chatMessageDTO.getMessage()+" "+chatMessageDTO.getSenderName());
        chatService.addChat(chatMessageDTO);
    }

    @GetMapping("/{roomId}")
    public List<ChatMessageDTO> listChat(@PathVariable Long roomId){
        log.info("roomId : " + roomId);
        List<ChatMessageDTO> list = chatService.listChat(roomId);
        return list;
    }

    @GetMapping("/{roomId}/private/{sender}/{receiver}")
    public List<ChatMessageDTO> privateChatList (@PathVariable Long roomId, @PathVariable String sender, @PathVariable String receiver){
        List<ChatMessageDTO> list = chatService.privateChatList(roomId, sender, receiver);
        return list;
    }

}
