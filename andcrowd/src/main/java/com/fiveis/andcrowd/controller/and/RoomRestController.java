package com.fiveis.andcrowd.controller.and;

import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.dto.user.UserDTO;
import com.fiveis.andcrowd.entity.and.ChatRoom;
import com.fiveis.andcrowd.entity.user.User;
import com.fiveis.andcrowd.repository.and.AndJPARepository;
import com.fiveis.andcrowd.repository.and.ChatRoomRepository;
import com.fiveis.andcrowd.repository.and.DynamicAndMemberRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import com.fiveis.andcrowd.service.and.ChatRoomService;
import com.fiveis.andcrowd.service.and.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/and")
// @CrossOrigin(origins = "http://localhost:3000") // 프론트엔드 도메인 설정
@Log4j2
public class RoomRestController {
    private final ChatRoomRepository chatRoomRepository;
    private final AndJPARepository andRepository;
    private final UserJPARepository userRepository;
    private final DynamicAndMemberRepository memberRepository;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

//    @GetMapping("/rooms")
//    public List<ChatRoom> getChatRooms(Authentication authentication) {
//        return chatRoomService.findByUserNickname(authentication.getName());
//    }

    @RequestMapping(value="/chat/rooms/{nickname}", method = RequestMethod.GET)
    public List<ChatRoom> getChatRooms(@PathVariable("nickname") String nickname) {
        List<ChatRoom> chatRoomList = chatRoomService.findByUserNickname(nickname);
        return chatRoomList;
    }

    @RequestMapping(value="/{andId}/chat", method = RequestMethod.GET)
    public ChatRoom getChatRoom(@PathVariable("andId") int andId) {
        ChatRoom chatRoom = chatRoomService.findByAndId(andId);
        return chatRoom;
    }

    @GetMapping("/{andId}/check-member")
    public ResponseEntity<Map<String, Boolean>> checkMember(@RequestParam String nickname, @PathVariable int andId) {
        List<DynamicAndMemberDTO.FindByAndMemberId> members = memberRepository.findAll(andId);
        System.out.println("들어온닉네임: "+ nickname);
        Optional<User> user = userRepository.findByUserNickname(nickname);
        System.out.println("members: " + members);
        System.out.println("user: " + user);

        Map<String, Boolean> response = new HashMap<>();

        if (user.isPresent()) {
            int userId = user.get().getUserId();

            List<Integer> userIdsInMembers = members.stream()
                    .map(DynamicAndMemberDTO.FindByAndMemberId::getUserId)
                    .collect(Collectors.toList());

            boolean isMember = userIdsInMembers.contains(userId);

            response.put("isMember", isMember);
        } else {
            response.put("isMember", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{andId}/check-member/{userId}")
    public ResponseEntity<Boolean> checkMember(@PathVariable int userId, @PathVariable int andId) {
        List<DynamicAndMemberDTO.FindByAndMemberId> members = memberRepository.findAll(andId);

        List<Integer> userIdsInMembers = members.stream()
                .map(DynamicAndMemberDTO.FindByAndMemberId::getUserId)
                .collect(Collectors.toList());

        boolean isMember = userIdsInMembers.contains(userId);

        return ResponseEntity.ok(isMember);
    }


    @PutMapping("/{andId}/chat/room/{roomId}/name-update")
    public ResponseEntity<String> updateChatRoomName(
            @PathVariable Long roomId,
            @RequestBody String newName
    ) {
        chatRoomService.updateChatRoomName(roomId, newName);
        return ResponseEntity.ok("Chat room name updated successfully.");
    }

    @GetMapping("{andId}/chat/{roomId}/member")
    public List<UserDTO.UserChatInfo> chatMember(@PathVariable Long roomId) {
        List<UserDTO.UserChatInfo> chatMember = chatRoomService.chatMember(roomId);
        return chatMember;
    }

    @GetMapping("chat/{nickname}/img")
    public ResponseEntity<String> getUserImg(@PathVariable String nickname){
        String userImg = chatRoomService.nicknameToImg(nickname);
        return ResponseEntity.ok(userImg);
    }
}

