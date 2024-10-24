package com.fiveis.andcrowd.entity.crowd;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class Crowd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crowdId;

    private int adId;

    private int andId;

    @Column(nullable = false)
    private int crowdCategoryId;

    @Column(nullable = false, length = 6000)
    private String crowdContent;

    @Column(nullable = false)
    private LocalDate crowdEndDate;

    @Column(nullable = false)
    private int crowdGoal;

//    private String crowdImg1;
//    private String crowdImg2;
//    private String crowdImg3;
//    private String crowdImg4;
//    private String crowdImg5;

//    @ColumnDefault("0")
    private int crowdStatus; // 1:모집중, 2:반려, 3:종료, 4: 작성중, 0:심사중

    @Column(nullable = false)
    private String crowdTitle;

    //@Column(nullable = false)
    private String headerImg;

    @ColumnDefault("false")
    private boolean isDeleted;

    @ColumnDefault("0")
    private int likeSum;

    @Column(nullable = true, columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime publishedAt;

    @Column(nullable = true, columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int userId;

    @ColumnDefault("0")
    private int viewCount;

    @PrePersist
    public void setDefaultValue(){
//        this.crowdStatus = 0; // 펀딩글 첫 업로드시 자동으로 심사중 표기
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isDeleted = false;
        this.viewCount = 0;
        this.likeSum = 0;
        this.crowdStatus = 0;
    }

    @PreUpdate
    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

//    public void setCrowdImg1(String crowdImg1) {
//        this.crowdImg1 = crowdImg1;
//    }
//
//    public void setCrowdImg2(String crowdImg2) {
//        this.crowdImg2 = crowdImg2;
//    }
//
//    public void setCrowdImg3(String crowdImg3) {
//        this.crowdImg3 = crowdImg3;
//    }
//
//    public void setCrowdImg4(String crowdImg4) {
//        this.crowdImg4 = crowdImg4;
//    }
//
//    public void setCrowdImg5(String crowdImg5) {
//        this.crowdImg5 = crowdImg5;
//    }
    public Crowd updateCrowd(String crowdTitle,
                             String crowdContent,
                             int crowdCategoryId,
                             int crowdStatus,
                             int crowdGoal,
                             LocalDate crowdEndDate,
                             LocalDateTime updatedAt){
        this.crowdTitle = crowdTitle;
        this.crowdContent = crowdContent;
        this.crowdCategoryId = crowdCategoryId;
        this.crowdStatus = crowdStatus;
        this.crowdGoal = crowdGoal;
        this.crowdEndDate = crowdEndDate;
        this.updatedAt = updatedAt;
        return this;
    }

    public Crowd updateCrowdStatus(int crowdStatus) {
        this.crowdStatus = crowdStatus;
        return this;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    } 코드 중복

    public void setCrowdEndDate(LocalDate crowdEndDate) {
        this.crowdEndDate = crowdEndDate;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}