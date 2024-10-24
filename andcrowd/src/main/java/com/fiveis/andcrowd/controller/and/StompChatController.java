package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.enums.ChatStatus;
import com.fiveis.andcrowd.dto.and.ChatMessageDTO;
import com.fiveis.andcrowd.entity.and.Chat;
import com.fiveis.andcrowd.service.and.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate simpMessagingTemplate; //특정 Broker로 메세지를 전달
    private final Map<String, Long> map = new HashMap<>();
    private final ChatService chatService;

    @MessageMapping(value = "/chat/enter")
    public void enter(@Payload ChatMessageDTO message){
        List<String> liveUser = new ArrayList<>();
        map.put(message.getSenderName(), message.getRoomId());
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue().equals(message.getRoomId())) {
                liveUser.add(entry.getKey());
            }
        }
        message.setUserList(liveUser);
        message.setMessage(message.getSenderName() + "님이 채팅방에 참여하였습니다.");
        message.setChatStatus(ChatStatus.JOIN);
        message.setPublishedAt(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/out")
    public void out(@Payload ChatMessageDTO message){
        List<String> liveUser = new ArrayList<>();
        map.remove(message.getSenderName());
        for (Map.Entry<String, Long> entry : map.entrySet()) {
            if (entry.getValue().equals(message.getRoomId())) {
                liveUser.add(entry.getKey());
            }
        }
        message.setUserList(liveUser);
        message.setMessage(message.getSenderName() + "님이 채팅방에 나가셨습니다.");
        message.setChatStatus(ChatStatus.LEAVE);
        message.setPublishedAt(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }


    @MessageMapping("/message")
    public void sendMessage(@Payload Chat message) {
        // 메시지 저장
        chatService.saveChatMessage(message);

        // 클라이언트에게 메시지 전달
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/private-message")
    public Chat receivePrivateMessage(@Payload Chat message){
        chatService.saveChatMessage(message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message); // /user/David/private
        return message;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String senderName = (String) headerAccessor.getSessionAttributes().get("senderName");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");

        if (senderName != null) {
            map.remove(senderName);
            ChatMessageDTO leaveMessage = new ChatMessageDTO();
            leaveMessage.setSenderName(senderName);
            leaveMessage.setRoomId(roomId);
            leaveMessage.setChatStatus(ChatStatus.LEAVE);
            simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomId, leaveMessage);
        }
    }

}
