package com.fiveis.andcrowd.entity.crowd;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder @Entity
@DynamicInsert
@Table(name = "crowd_category")
public class CrowdCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int crowdCategoryId;

    @Column(nullable = false)
    private String crowdCategoryName;

    @Column(nullable = false)
    private boolean isDeleted;

    //SaveDTO를 통해 Entity를 초기화 하기 위한 생성자
//    public CrowdCategory(String crowdCategoryName){
//        this.crowdCategoryName = crowdCategoryName;
//    }
}