//package com.fiveis.andcrowd.controller.user;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fiveis.andcrowd.dto.crowd.CrowdOrderDetailsDTO;
//import com.fiveis.andcrowd.entity.and.And;
//import com.fiveis.andcrowd.entity.crowd.CrowdOrderDetails;
//import com.fiveis.andcrowd.entity.user.*;
//import com.fiveis.andcrowd.enums.Role;
//import com.fiveis.andcrowd.service.and.AndService;
//import com.fiveis.andcrowd.service.crowd.CrowdOrderDetailsService;
//import com.fiveis.andcrowd.service.user.*;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class DynamicUserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private WebApplicationContext context;
//    @Autowired
//    UserService userService;
//    @Autowired
//    AndService andService;
//    @Autowired
//    DynamicUserController dynamicUserController;
//    @Autowired
//    DynamicUserAndService dynamicUserAndService;
//    @Autowired
//    DynamicUserFollowService dynamicUserFollowService;
//    @Autowired
//    DynamicUserLikeService dynamicUserLikeService;
//    @Autowired
//    DynamicUserMakerService dynamicUserMakerService;
//    @Autowired
//    DynamicUserOrderService dynamicUserOrderService;
//    @Autowired
//    CrowdOrderDetailsService crowdOrderDetailsService;
//
//    // User 테스트 데이터
//    int userId = 1;
//    String userEmail = "asdf@gmail.com";
//    String userPassword = "qwer";
//    String userKorName = "김명호";
//    String userNickname = "NICK";
//    String userPhone = "010-1234-5678";
//    LocalDateTime userBirth = LocalDateTime.of(1997, 2, 14, 0, 0, 0);
//    LocalDateTime userRegister = LocalDateTime.now();
//    int userTos = 1;
//    int userPrivacy = 1;
//    int userMarketing = 1;
//    Role role = Role.ROLE_ADMIN;
//
//    // 그 외 테스트 데이터
//    int andId = 1;
//    int projectId = 5000;
//
//    @BeforeEach
//    public void setMockMvcAndSaveUser(){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//
//        userService.save(User.builder()
//                .userId(userId)
//                .userEmail(userEmail)
//                .userPassword(userPassword)
//                .userKorName(userKorName)
//                .userNickname(userNickname)
//                .userPhone(userPhone)
//                .userBirth(userBirth)
//                .userRegister(userRegister)
//                .userTos(userTos)
//                .userPrivacy(userPrivacy)
//                .userMarketing(userMarketing)
//                .authority(role)
//                .build());
//
//        // And 테이블 중복 생성 및 중복 데이터 추가 시 오류
//        andService.save(And.builder()
//                        .andId(andId)
//                        .userId(userId)
//                        .andCategoryId(0)
//                        .crowdId(0)
//                        .andTitle("andTitle")
//                        .andHeaderImg("andHeaderImg")
//                        .andContent("andContent")
//                        .andEndDate(LocalDateTime.now())
//                        .needNumMem(0)
//                        .andLikeCount(0)
//                        .andViewCount(0)
//                        .andStatus(0)
//                        .adId(0)
//                        .build());
//    }
//
//    @Test
//    @Transactional
//    public void findUserAndTest() throws Exception{
//        // Given
//        String url = "/user/1/and";
//
//        dynamicUserAndService.save(User.toTableName(userEmail),
//                DynamicUserAnd.builder()
//                        .andId(andId)
//                        .build());
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].andId").value(andId));
//    }
//
//    @Test
//    @Transactional
//    public void saveUserAndTest() throws Exception{
//        // Given
//        String url = "/user/1/and";
//        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
//                .andId(andId)
//                .build();
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .post(url)
//                .content(toJson(dynamicUserAnd))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(content().string("UserAnd Saved!"));
//    }
//
//    @Test
//    @Transactional
//    public void findUserFollowTest() throws Exception{
//        // Given
//        String url = "/user/1/follow";
//        int newUserId = 2;
//        String newUserEmail = "qwer@gmail.com";
//
//        userService.save(User.builder()
//                .userId(newUserId)
//                .userEmail(newUserEmail)
//                .userPassword(userPassword)
//                .userKorName(userKorName)
//                .userNickname(userNickname)
//                .userPhone(userPhone)
//                .userBirth(userBirth)
//                .userRegister(userRegister)
//                .userTos(userTos)
//                .userPrivacy(userPrivacy)
//                .userMarketing(userMarketing)
//                .authority(role)
//                .build());
//
//        dynamicUserFollowService.save(User.toTableName(userEmail),
//                DynamicUserFollow.builder()
//                        .userId(newUserId)
//                        .build());
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].userNickname").value(userNickname));
//    }
//
//    @Test
//    @Transactional
//    public void saveUserFollowTest() throws Exception{
//        // Given
//        String url = "/user/1/follow";
//        DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
//                .userId(userId)
//                .build();
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .post(url)
//                .content(toJson(dynamicUserFollow))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(content().string("UserFollow Saved!"));
//    }
//
//    @Test
//    @Transactional
//    public void findUserLikeTest() throws Exception{
//        // Given
//        String url = "/user/1/like";
//
//        dynamicUserLikeService.save(User.toTableName(userEmail),
//                DynamicUserLike.builder()
//                        .projectId(andId)
//                        .projectType(0)
//                        .build());
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].projectId").value(andId));
//    }
//
//    @Test
//    @Transactional
//    public void saveUserLikeTest() throws Exception{
//        // Given
//        String url = "/user/1/like";
//        DynamicUserLike dynamicUserLike = DynamicUserLike.builder()
//                .projectId(projectId)
//                .build();
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .post(url)
//                .content(toJson(dynamicUserLike))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(content().string("UserLike Saved!"));
//    }
//
//    @Test
//    @Transactional
//    public void findUserMakerTest() throws Exception{
//        // Given
//        String url = "/user/1/maker";
//
//        // 원래는 Project 생성 시 자동으로 userMaker에 추가되어야 함.
//        dynamicUserMakerService.save(User.toTableName(userEmail),
//                DynamicUserMaker.builder()
//                        .projectId(andId)
//                        .projectType(0)
//                        .build());
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].projectId").value(andId));
//    }
//
//    @Test
//    @Transactional
//    public void saveUserMakerTest() throws Exception{
//        // Given
//        String url = "/user/1/maker";
//        DynamicUserMaker dynamicUserMaker = DynamicUserMaker.builder()
//                .projectId(projectId)
//                .build();
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .post(url)
//                .content(toJson(dynamicUserMaker))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(content().string("UserMaker Saved!"));
//    }
//
//    @Test
//    @Transactional
//    public void findUserOrderTest() throws Exception{
//        // Given
//        String url = "/user/1/order";
//
//        crowdOrderDetailsService.save(CrowdOrderDetails.builder()
//                        .userId(userId)
//                        .crowdId(99)
//                        .rewardId(1)
//                        .sponsorId(1)
//                        .purchaseName("김명호")
//                        .purchasePhone("010-1234-5678")
//                        .purchaseAddress("강남구")
//                        .purchaseDate(LocalDateTime.now())
//                        .purchaseStatus("결제완료")
//                        .isDeleted(false)
//                        .build());
//
//        List<CrowdOrderDetailsDTO.FindById> orderFindByIdList = crowdOrderDetailsService.findAll();
//        List<Integer> orderIdList = new ArrayList<>();
//        for(CrowdOrderDetailsDTO.FindById orderFindById : orderFindByIdList){
//            if(orderFindById.getUserId() == userId) orderIdList.add(orderFindById.getPurchaseId());
//        }
//
//        for(int orderId : orderIdList){
//            dynamicUserOrderService.save(User.toTableName(userEmail),
//                    DynamicUserOrder.builder()
//                            .orderId(orderId)
//                            .build());
//        }
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .get(url)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].purchaseId").value(orderIdList.get(0)));
//    }
//
//    @Test
//    @Transactional
//    public void saveUserOrderTest() throws Exception{
//        // Given
//        String url = "/user/1/order";
//        DynamicUserOrder dynamicUserOrder = DynamicUserOrder.builder()
//                .orderId(15)
//                .build();
//
//        // When
//        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders
//                .post(url)
//                .content(toJson(dynamicUserOrder))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON));
//
//        // Then
//        result.andExpect(status().isOk())
//                .andExpect(content().string("UserOrder Saved!"));
//    }
//
//    private <T> String toJson(T data) throws JsonProcessingException{
//        return objectMapper.writeValueAsString(data);
//    }
//}
