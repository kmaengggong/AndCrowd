package com.fiveis.andcrowd.entity.and;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAndMember {

    private int memberId;

    private int andId;

    private int userId;

    private int andApplyId;

    private boolean isDeleted;

}
