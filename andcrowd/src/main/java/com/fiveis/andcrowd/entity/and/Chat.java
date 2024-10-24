package com.fiveis.andcrowd.entity.and;

import com.fiveis.andcrowd.enums.ChatStatus;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long roomId;
    private String senderName;

    @Column(nullable = true)
    private String receiverName;

    private LocalDateTime publishedAt;
    private String message;
    private ChatStatus chatStatus;

    private String s3DataUrl; // 파일 업로드 url
    private String fileName; // 파일이름
    private String fileDir; // s3 파일 경로

}