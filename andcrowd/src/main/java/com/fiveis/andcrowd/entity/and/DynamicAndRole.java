package com.fiveis.andcrowd.entity.and;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAndRole {

    private int andRoleId;

    private int andId;

    private String andRole;

    private int andRoleLimit;

    private boolean isDeleted;

}
