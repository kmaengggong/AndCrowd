package com.fiveis.andcrowd.dto.and;

import com.fiveis.andcrowd.entity.and.Chat;
import com.fiveis.andcrowd.entity.and.ChatRoom;
import com.fiveis.andcrowd.enums.ChatStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {

    private Long roomId;
    private String senderName;
    private String receiverName;
    private String message;

    private LocalDateTime publishedAt;
    private List<String> userList;
    private ChatStatus chatStatus;

    private String s3DataUrl; // 파일 업로드 url
    private String fileName; // 파일이름
    private String fileDir; // s3 파일 경로

    public Chat toChat(ChatRoom chatRoom){
        return Chat.builder().message(message)
                .senderName(senderName)
                .receiverName(receiverName)
                .publishedAt(publishedAt)
                .roomId(roomId)
                .chatStatus(chatStatus)
                .fileName(fileName)
                .s3DataUrl(s3DataUrl)
                .fileDir(fileDir)
                .build();

    }

}
