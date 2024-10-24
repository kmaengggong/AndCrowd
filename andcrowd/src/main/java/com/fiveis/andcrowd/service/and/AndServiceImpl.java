package com.fiveis.andcrowd.service.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.dto.and.DynamicAndMemberDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserFollowDTO;
import com.fiveis.andcrowd.dto.user.DynamicUserLikeDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.user.DynamicUserAnd;
import com.fiveis.andcrowd.entity.user.DynamicUserFollow;
import com.fiveis.andcrowd.entity.user.DynamicUserLike;
import com.fiveis.andcrowd.entity.user.DynamicUserMaker;
import com.fiveis.andcrowd.repository.and.*;
import com.fiveis.andcrowd.repository.crowd.CrowdJPARepository;
import com.fiveis.andcrowd.repository.user.DynamicUserAndRepository;
import com.fiveis.andcrowd.repository.user.DynamicUserMakerRepository;
import com.fiveis.andcrowd.repository.user.UserJPARepository;
import com.fiveis.andcrowd.service.user.DynamicUserFollowService;
import com.fiveis.andcrowd.service.user.DynamicUserLikeService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fiveis.andcrowd.dto.and.AndDTO.convertToAndFindDTO;
import static com.fiveis.andcrowd.entity.user.User.toTableName;

@Service
public class AndServiceImpl implements AndService{

    private final AndJPARepository andJPARepository;
    private final AndDynamicRepository andDynamicRepository;
    private final ChatRoomService chatRoomService;
    private final DynamicAndQnaRepository dynamicAndQnaRepository;
    private final DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository;
    private final DynamicAndBoardRepository dynamicAndBoardRepository;
    private final DynamicUserAndRepository dynamicUserAndRepository;
    private final UserJPARepository userJPARepository;
    private final DynamicAndMemberRepository andMemberRepository;
    private final DynamicUserLikeService dynamicUserLikeService;
    private final DynamicUserFollowService dynamicUserFollowService;
    private final DynamicUserMakerRepository dynamicUserMakerRepository;
    private final JPAQueryFactory query;
    private final CrowdJPARepository crowdJPARepository;


    @Autowired
    public AndServiceImpl(AndJPARepository andJPARepository,
                          AndDynamicRepository andDynamicRepository,
                          DynamicAndQnaRepository dynamicAndQnaRepository,
                          DynamicAndQnaReplyRepository dynamicAndQnaReplyRepository,
                          DynamicAndBoardRepository dynamicAndBoardRepository,
                          ChatRoomService chatRoomService,
                          DynamicUserAndRepository dynamicUserAndRepository,
                          UserJPARepository userJPARepository,
                          DynamicAndMemberRepository andMemberRepository,
                          DynamicUserLikeService dynamicUserLikeService,
                          DynamicUserFollowService dynamicUserFollowService,
                          DynamicUserMakerRepository dynamicUserMakerRepository,
                          EntityManager em,
                          CrowdJPARepository crowdJPARepository){
        this.andJPARepository = andJPARepository;
        this.andDynamicRepository = andDynamicRepository;
        this.dynamicAndQnaRepository = dynamicAndQnaRepository;
        this.dynamicAndQnaReplyRepository = dynamicAndQnaReplyRepository;
        this.dynamicAndBoardRepository = dynamicAndBoardRepository;
        this.chatRoomService = chatRoomService;
        this.dynamicUserAndRepository = dynamicUserAndRepository;
        this.userJPARepository = userJPARepository;
        this.andMemberRepository = andMemberRepository;
        this.dynamicUserLikeService = dynamicUserLikeService;
        this.dynamicUserFollowService = dynamicUserFollowService;
        this.dynamicUserMakerRepository = dynamicUserMakerRepository;
        this.query = new JPAQueryFactory(em);
        this.crowdJPARepository = crowdJPARepository;
    }


//    @Override
//    public Optional<AndFindByIdDTO> findById(int andId) {
//        return andJPARepository.findById(andId);
//    }

    @Override
    public Optional<AndDTO.Find> findById(int andId) {
        Optional<And> andOptional = andJPARepository.findById(andId);
        return andOptional.map(AndDTO::convertToAndFindDTO);
    }

    @Override
    public List<AndDTO.FindAllByUserId> findAllByUserId(int userId) {
        return andJPARepository.findAllByUserId(userId);
    }

    @Override
    public List<AndDTO.Find> findAllByIsDeletedFalse() {
        List<And> andList = andJPARepository.findAllByIsDeletedFalse();
        List<AndDTO.Find> findAllNotDeletedList = new ArrayList<>();

        for (And and : andList) {
            AndDTO.Find dto = convertToAndFindDTO(and);
            findAllNotDeletedList.add(dto);
        }


        return findAllNotDeletedList;
    }

    @Override
    public List<AndDTO.Find> findAll() {
        List<And> andList = andJPARepository.findAll();
        List<AndDTO.Find> findList = new ArrayList<>();

        for (And and : andList) {
            AndDTO.Find dto = convertToAndFindDTO(and);
            findList.add(dto);
        }

        return findList;
    }


    @Override
    public void deleteById(int andId) {
        dynamicAndBoardRepository.deleteAll(andId);
        dynamicAndQnaReplyRepository.deleteAll(andId);
        dynamicAndQnaRepository.deleteAll(andId);
        andJPARepository.deleteById(andId);
    }

    @Override
    public void save(And and) {
        And savedAnd = andJPARepository.save(and);
        andDynamicRepository.createDynamicAndQnaTable(savedAnd.getAndId());
        andDynamicRepository.createDynamicAndMemberTable(savedAnd.getAndId());
        andDynamicRepository.createDynamicAndBoardTable(savedAnd.getAndId());
        andDynamicRepository.createDynamicAndRoleTable(savedAnd.getAndId());
        andDynamicRepository.createDynamicAndApplicantTable(savedAnd.getAndId());
        andDynamicRepository.createDynamicAndQnaReplyTable(savedAnd.getAndId());

        String userEmail = toTableName(userJPARepository.findByUserId(savedAnd.getUserId()).get().getUserEmail());
        DynamicUserAnd dynamicUserAnd = DynamicUserAnd.builder()
                .andId(savedAnd.getAndId())
                .build();
        dynamicUserAndRepository.save(userEmail, dynamicUserAnd);

        DynamicUserMaker dynamicUserMaker = DynamicUserMaker.builder()
                .projectId(savedAnd.getAndId())
                .projectType(0)
                .build();
        dynamicUserMakerRepository.save(userEmail, dynamicUserMaker);

        DynamicAndMemberDTO.Update userMember = DynamicAndMemberDTO.Update.builder()
                .userId(savedAnd.getUserId())
                .andId(savedAnd.getAndId())
                .andApplyId(0)
                .build();
        andMemberRepository.save(userMember);
    }

    @Override
    public void update(And and) {
        And updatedAnd = andJPARepository.findById(and.getAndId()).get();
        updatedAnd.setAndTitle(and.getAndTitle());
        updatedAnd.setAndContent(and.getAndContent());
        updatedAnd.setAndEndDate(and.getAndEndDate());
        updatedAnd.setAndCategoryId(and.getAndCategoryId());
        updatedAnd.setNeedNumMem(and.getNeedNumMem());
        updatedAnd.setUpdatedAt(LocalDateTime.now());
        updatedAnd.setAndStatus(and.getAndStatus());
        andJPARepository.save(updatedAnd);
    }

    @Override
    @Transactional
    public void updateStatus(int andId, int andStatus) {
        if(andStatus == 1){
            chatRoomService.createAndChatroom(andId);
        }
        Optional<And> optionalAnd = andJPARepository.findById(andId);
        if (optionalAnd.isPresent()) {
            And updatedAnd = optionalAnd.get();
            updatedAnd.setAndStatus(andStatus);
            // 변경된 And 객체를 다시 저장
            andJPARepository.save(updatedAnd);

            // 크라우드에도 연계된 andId 저장
            int crowdId = updatedAnd.getCrowdId();
            if(crowdId != 0){
                crowdJPARepository.updateAndId(crowdId, andId);
            }
        }
    }

    @Override
    @Transactional
    public void updateNeedNumMem(int andId, int needNumMem) {
        andJPARepository.updateNeedNumMem(andId, needNumMem);
    }

    @Override
    public void updateStatusForExpiredItems() {
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        // 오늘 날짜보다 이전인 항목들을 가져와서 상태 코드를 3으로 업데이트
        List<And> expiredItems = andJPARepository.findExpiredAnds(today, 3);
        for (And item : expiredItems) {
            item.setAndStatus(3);
            andJPARepository.save(item);
        }
    }

    @Override
    @Transactional
    public int updateView(int andId) {
        return andJPARepository.updateView(andId);
    }

    @Override
    @Transactional
    public void increaseLike(Integer andId) {
        andJPARepository.increaseLike(andId);
    }

    @Override
    @Transactional
    public void decreaseLike(Integer andId) {
        andJPARepository.decreaseLike(andId);
    }

    @Override
    @Transactional
    public void updateLike(Integer andId, int userId){
        String userEmail = userJPARepository.findByUserId(userId).get().getUserEmail();
        String convertedUserEmail = toTableName(userEmail);
        DynamicUserLikeDTO.Find like = dynamicUserLikeService.findByProject(convertedUserEmail, andId, 0);

        if (like == null) {
            increaseLike(andId);
            DynamicUserLike dynamicUserLike = DynamicUserLike.builder()
                    .projectId(andId)
                    .projectType(0)
                    .build();
            dynamicUserLikeService.save(convertedUserEmail, dynamicUserLike);
        } else {
            decreaseLike(andId);
            dynamicUserLikeService.deleteByProjectId(convertedUserEmail, andId, 0);
        }
    }

    @Override
    @Transactional
    public boolean isLiked(int andId, int userId) {
        String userEmail = userJPARepository.findByUserId(userId).get().getUserEmail();
        DynamicUserLikeDTO.Find like = dynamicUserLikeService.findByProject(userEmail, andId, 0);

        if (like == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void updateFollow(int myId, int userId) {
        String userEmail = userJPARepository.findByUserId(myId).get().getUserEmail();
        String convertedUserEmail = toTableName(userEmail);
        DynamicUserFollowDTO.Find follow = dynamicUserFollowService.findByUserId(convertedUserEmail, userId);

        if (follow == null) {
            DynamicUserFollow dynamicUserFollow = DynamicUserFollow.builder()
                    .userId(userId)
                    .build();
            dynamicUserFollowService.save(convertedUserEmail, dynamicUserFollow);
        } else {
            dynamicUserFollowService.deleteByUserId(convertedUserEmail, userId);
        }

    }

    @Override
    public boolean isFollowed(int myId, int userId) {
        String userEmail = userJPARepository.findByUserId(myId).get().getUserEmail();
        String convertedUserEmail = toTableName(userEmail);
        DynamicUserFollowDTO.Find follow = dynamicUserFollowService.findByUserId(convertedUserEmail, userId);

        if (follow == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int totalCount(String searchKeyword) {
        return andJPARepository.totalCount(searchKeyword);
    }

}
