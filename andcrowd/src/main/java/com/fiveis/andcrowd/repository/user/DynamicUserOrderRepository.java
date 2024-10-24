package com.fiveis.andcrowd.repository.user;

import com.fiveis.andcrowd.dto.user.DynamicUserOrderDTO;
import com.fiveis.andcrowd.entity.user.DynamicUserOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DynamicUserOrderRepository {
    void createDynamicUserOrderTable(String userEmail);
    List<DynamicUserOrderDTO.Find> findAll(String userEmail);
    DynamicUserOrderDTO.Find findById(@Param("userEmail") String userEmail, @Param("uOrderId") int uOrderId);
    DynamicUserOrderDTO.Find findByOrderId(@Param("userEmail") String userEmail, @Param("orderId") int orderId);

    void save(@Param("userEmail") String userEmail, @Param("dynamicUserOrder") DynamicUserOrder dynamicUserOrder);
    void deleteById(@Param("userEmail") String userEmail, @Param("uOrderId") int uOrderId);
    void deleteByOrderId(@Param("userEmail") String userEmail, @Param("orderId") int orderId);
    void deleteTableByUserEmail(@Param("userEmail") String userEmail);
}
