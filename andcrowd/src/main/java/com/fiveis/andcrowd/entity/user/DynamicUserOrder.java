package com.fiveis.andcrowd.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicUserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uOrderId;  // 유저 주문 ID

    @Column(nullable = false)
    private int orderId;  // 주문 ID
}