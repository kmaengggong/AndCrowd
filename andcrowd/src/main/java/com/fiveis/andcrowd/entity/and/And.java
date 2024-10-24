package com.fiveis.andcrowd.entity.and;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder @Entity
@DynamicInsert
@Table(name = "and_table")
@SQLDelete(sql = "UPDATE and_table SET is_deleted = true WHERE and_id = ?")
public class And {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int andId;

    @Column(nullable = true)
    private int userId;

    @Column(nullable = true)
    private int andCategoryId;

    @Column(nullable = true)
    private int crowdId;

    @Column(nullable = true)
    private String andTitle;

    @Column(nullable = true)
    private String andHeaderImg;

    @Column(nullable = true, length = 6000)
    private String andContent;

    @Column(nullable = true)
    private LocalDate andEndDate;

    @Column(nullable = true)
    private int needNumMem;

//    private String andImg1;
//
//    private String andImg2;
//
//    private String andImg3;
//
//    private String andImg4;
//
//    private String andImg5;

    @Column(nullable = true, columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime publishedAt;

    @Column(nullable = true, columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @ColumnDefault("0")
    private int andLikeCount;

    @ColumnDefault("0")
    private int andViewCount;

    private int andStatus; // 1:모집중, 2:반려, 3:종료, 4: 작성중, 0:심사중

    @Column(nullable = true)
    private int adId;

    @ColumnDefault("false")
    private boolean isDeleted;

    @PrePersist
    public void setDefaultValue(){
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
        this.andLikeCount = 0;
        this.andViewCount = 0;
//        this.andStatus = 1; // 처음 작성시 자동으로 심사 중인 1로 표기
    }

    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

}