package com.fiveis.andcrowd.entity.etc;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder @Entity
@DynamicInsert
@Table(name = "info_board")
public class InfoBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int infoId; // 공지글Id

    @Column(nullable = false)
    private int userId; // 유저Id(편집권한: 관리자, 읽기전용: 관리자 외)

    @Column(nullable = false)
    private boolean infoType; // 공지 종류 (true(1): 공지, false(0): 새소식)

    @Column(nullable = false)
    private String infoTitle; // 공지 제목

    @Column(nullable = false)
    private String infoContent; // 공지 본문

    @Column(nullable = false)
    private LocalDateTime publishedAt; // 업로드 일자

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정 일자

    @ColumnDefault("false")
    private boolean isDeleted; // 삭제여부 (true: 삭제안됨 , false: 삭제됨 )

    @PrePersist
    public void setDefaultValue() {
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

}
