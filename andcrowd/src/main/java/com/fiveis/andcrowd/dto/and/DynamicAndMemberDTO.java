package com.fiveis.andcrowd.dto.and;

import lombok.*;

public class DynamicAndMemberDTO {
    @Getter @Setter
    @Builder @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class Update {
        private int memberId;
        private int andId;
        private int userId;
        private int andApplyId;
        private boolean isDeleted;
    }

    @Getter @Setter
    @Builder @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class FindByAndMemberId {
        private int memberId;
        private int andId;
        private int userId;
        private int andApplyId;
        private boolean isDeleted;
    }

    @Getter @Setter
    @Builder @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class MemberList {
        private int memberId;
        private int andId;
        private int userId;
        private int andApplyId;
        private boolean isDeleted;
        private String nickname;
        private String profileImg;
    }


}
