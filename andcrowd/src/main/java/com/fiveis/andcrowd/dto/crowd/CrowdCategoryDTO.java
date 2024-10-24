package com.fiveis.andcrowd.dto.crowd;

import com.fiveis.andcrowd.entity.crowd.CrowdCategory;
import lombok.*;

public class CrowdCategoryDTO {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find {
        private int crowdCategoryId;
        private String crowdCategoryName;
        private boolean isDeleted;

//        public static CrowdCategoryDTO.Find convertToCrowdCategoryFindDTO(CrowdCategory crowdCategory) {
//            return CrowdCategoryDTO.Find.builder()
//                    .crowdCategoryId(crowdCategory.getCrowdCategoryId())
//                    .crowdCategoryName(crowdCategory.getCrowdCategoryName())
//                    .build();
//        }
    }

//    @Getter
//    @Setter
//    @Builder
//    @ToString
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class Save {
//        private String crowdCategoryName;
//
//        public static CrowdCategory convertToCrowdCategoryEntity(CrowdCategoryDTO.Save crowdCategoryDTOSave){
//            return new CrowdCategory(crowdCategoryDTOSave.getCrowdCategoryName());
//        }
//    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private int crowdCategoryId;
        private String crowdCategoryName;
        private boolean isDeleted;

        // 기존에 DB에 저장된 정보를 불러와 DTO형태로 변환해주는 메서드
//        public static CrowdCategoryDTO.Update convertToCrowdCategoryUpdateDTO(CrowdCategory crowdCategory) {
//            return CrowdCategoryDTO.Update.builder()
//                    .crowdCategoryId(crowdCategory.getCrowdCategoryId())
//                    .crowdCategoryName(crowdCategory.getCrowdCategoryName())
//                    .build();
//        }

        // 수정사항 반영후 Save하기 위해 Entity로 변환해주는 메서드
//        public static CrowdCategory convertToCrowdCategoryEntity(CrowdCategoryDTO.Update crowdCategoryDTOUpdate){
//            return new CrowdCategory(crowdCategoryDTOUpdate.getCrowdCategoryId(),
//                                     crowdCategoryDTOUpdate.getCrowdCategoryName());
//        }
    }

    public static CrowdCategoryDTO.Find convertToCrowdCategoryFindDTO(CrowdCategory crowdCategory) {
        CrowdCategoryDTO.Find convertedDTO = Find.builder()
                .crowdCategoryId(crowdCategory.getCrowdCategoryId())
                .crowdCategoryName(crowdCategory.getCrowdCategoryName())
                .isDeleted(crowdCategory.isDeleted())
                .build();
        return convertedDTO;
    }

}
