package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.ChatMessageDTO;
import com.fiveis.andcrowd.dto.and.ChatResponse;
import com.fiveis.andcrowd.entity.and.Chat;
import com.fiveis.andcrowd.entity.and.ChatRoom;
import com.fiveis.andcrowd.repository.and.ChatRepository;
import com.fiveis.andcrowd.repository.and.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatResponse addChat(ChatMessageDTO chatMessageDTO){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(chatMessageDTO.getRoomId()).orElseThrow(()->new IllegalArgumentException("DB ERROR"));
        Chat chater = chatRepository.save(chatMessageDTO.toChat(chatRoom));
        return ChatResponse.success(chater);
    }


    public List<ChatMessageDTO> listChat(Long roomId){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(()->new IllegalArgumentException("DB ERROR"));
        List<Chat> chats = chatRepository.findByRoomId(roomId);
        List<ChatMessageDTO> list = new ArrayList<>();

        for(Chat chat : chats){
            if (chat.getReceiverName() == null) { // receiverName이 null인 경우에만
                ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                        .message(chat.getMessage())
                        .senderName(chat.getSenderName())
                        .receiverName(chat.getReceiverName())
                        .publishedAt(chat.getPublishedAt())
                        .chatStatus(chat.getChatStatus())
                        .fileName(chat.getFileName())
                        .s3DataUrl(chat.getS3DataUrl())
                        .fileDir(chat.getFileDir())
                        .build();
                list.add(chatMessageDTO);
            }
        }
        return list;
    }

    public List<ChatMessageDTO> privateChatList(Long roomId, String sender, String receiver){
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow(()->new IllegalArgumentException("DB ERROR"));
        List<Chat> chats = chatRepository.findByRoomId(roomId);
        List<ChatMessageDTO> list = new ArrayList<>();

        for(Chat chat : chats){
            if ((Objects.equals(receiver, chat.getReceiverName()) && Objects.equals(sender, chat.getSenderName()))
                    || (Objects.equals(sender, chat.getReceiverName()) && Objects.equals(receiver, chat.getSenderName()))) {
                ChatMessageDTO chatMessageDTO = ChatMessageDTO.builder()
                        .message(chat.getMessage())
                        .senderName(chat.getSenderName())
                        .receiverName(chat.getReceiverName())
                        .publishedAt(chat.getPublishedAt())
                        .chatStatus(chat.getChatStatus())
                        .fileName(chat.getFileName())
                        .s3DataUrl(chat.getS3DataUrl())
                        .fileDir(chat.getFileDir())
                        .build();
                list.add(chatMessageDTO);
            }
        }

        return list;
    }


    public void saveChatMessage(Chat chat){
        chat.setPublishedAt(LocalDateTime.now());
        chatRepository.save(chat);
    }
}