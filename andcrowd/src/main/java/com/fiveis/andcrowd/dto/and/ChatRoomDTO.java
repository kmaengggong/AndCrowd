package com.fiveis.andcrowd.dto.and;

import com.fiveis.andcrowd.entity.and.ChatRoom;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ChatRoomDTO {

    private Long roomId;
    private String name;
    private Long andId;

    public static List<ChatRoomDTO> createList(List<ChatRoom> list) {
        List<ChatRoomDTO> result = new ArrayList<>();
        for (ChatRoom chatRoom : list) {
            result.add(ChatRoomDTO.builder()
                    .roomId(chatRoom.getRoomId())
                    .name(chatRoom.getName())
                    .andId((long) chatRoom.getAndId()) // 엔티티 변경에 맞게 수정
                    .build());
        }
        return result;
    }

    public ChatRoom toChatRoom(int userId) {
        return ChatRoom.builder()
                .name(name)
//                .userId(userId)
                .build();
    }
}
