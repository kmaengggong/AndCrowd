package com.fiveis.andcrowd.dto.and;
import com.fiveis.andcrowd.entity.and.And;
import com.fiveis.andcrowd.entity.and.AndCategory;
import lombok.*;

public class AndCategoryDTO {
    @Getter @Setter
    @Builder @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class Update {
        private int andCategoryId;
        private String andCategoryName;
        private boolean isDeleted;
    }

    @Getter @Setter
    @Builder @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class FindByCategoryId {
        private int andCategoryId;
        private String andCategoryName;
        private boolean isDeleted;
    }

    public static AndCategoryDTO.FindByCategoryId convertToAndCategoryFindDTO(AndCategory andCategory) {
        AndCategoryDTO.FindByCategoryId convertedDTO = FindByCategoryId.builder()
                .andCategoryId(andCategory.getAndCategoryId())
                .andCategoryName(andCategory.getAndCategoryName())
                .isDeleted(andCategory.isDeleted())
                .build();
        return convertedDTO;
    }

}
