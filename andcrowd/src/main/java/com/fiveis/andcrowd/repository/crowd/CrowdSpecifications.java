package com.fiveis.andcrowd.repository.crowd;

import com.fiveis.andcrowd.entity.crowd.Crowd;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CrowdSpecifications {

    public static Specification<Crowd> findByCriteria(Integer crowdCategoryId, Integer crowdStatus, String keyword) {
        return (root, query, criteriaBuilder) -> {
            // 시작 스펙 설정
            Predicate predicate = criteriaBuilder.conjunction();

            // crowdCategoryId가 null 또는 0이면 조건 무시
            if (crowdCategoryId != null && crowdCategoryId != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("crowdCategoryId"), crowdCategoryId));
            }

            // crowdStatus가 null이 아니면 조건 추가
            if (crowdStatus != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("crowdStatus"), crowdStatus));
            }

            // 13이면 1 또는 3인 조건 추가
            if (crowdStatus != null && crowdStatus == 13) {
                Predicate orPredicate = criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("crowdStatus"), 1),
                        criteriaBuilder.equal(root.get("crowdStatus"), 3)
                );
                predicate = criteriaBuilder.and(predicate, orPredicate);
            }

            // keyword가 null이 아니면 crowdTitleContaining 조건 추가
            if (keyword != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("crowdTitle"), "%" + keyword + "%"));
            }

            return predicate;
        };
    }
}
