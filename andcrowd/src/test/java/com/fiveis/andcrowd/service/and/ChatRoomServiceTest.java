//package com.fiveis.andcrowd.service.and;
//
//
//import com.fiveis.andcrowd.dto.and.ChatRoomDTO;
//import com.fiveis.andcrowd.entity.and.ChatRoom;
//import com.fiveis.andcrowd.repository.and.ChatRoomRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import java.util.List;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class ChatRoomServiceTest {
//
//    @Autowired
//    private ChatRoomService chatRoomService;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//
//    @Test
//    public void testFindByUserNickname() {
//        String userNickname = "testUser"; // 테스트에 사용할 사용자 닉네임
//
//        List<ChatRoom> chatRooms = chatRoomService.findByUserNickname(userNickname);
//
//        assertThat(chatRooms).isNotNull();
//        assertThat(chatRooms.size()).isEqualTo(1); // 예상되는 채팅방 개수
//    }
//
//    @Test
//    public void testCreateChatRoomDTO() {
//        // ChatRoomDTO 생성 및 필요한 파라미터 설정
//        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
//        chatRoomDTO.setAndId(1L); // andId 1 로 가정
//
//        String userNickname = "testUser"; // 테스트에 사용할 사용자 닉네임
//
//        ChatRoom createdRoom = chatRoomService.createChatRoomDTO(chatRoomDTO, userNickname);
//
//        assertThat(createdRoom).isNotNull();
//        // 생성된 채팅방에 대한 추가적인 검증 로직 필요
//    }
//}
