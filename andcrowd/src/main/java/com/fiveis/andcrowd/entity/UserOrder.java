package com.fiveis.andcrowd.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uOrderId;  // 유저 주문 ID

    @Column(nullable = false)
    private int orderID;  // 주문 ID
}
