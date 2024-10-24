package com.fiveis.andcrowd.entity.and;

import jakarta.persistence.*;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder @Entity
    public class AndCategory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(nullable = false)
        private int andCategoryId;

        @Column(nullable = false)
        private String andCategoryName;

        @Column(nullable = false)
        private boolean isDeleted;
    }
