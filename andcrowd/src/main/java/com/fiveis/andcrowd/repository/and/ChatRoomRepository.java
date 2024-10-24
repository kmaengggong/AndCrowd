package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.entity.and.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByRoomId(Long roomId);

    @Query("SELECT c FROM ChatRoom c WHERE c.andId = :andId")
    ChatRoom findByAndId(@Param("andId") Integer andId);

}
