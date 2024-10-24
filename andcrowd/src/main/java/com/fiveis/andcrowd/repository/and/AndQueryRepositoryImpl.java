package com.fiveis.andcrowd.repository.and;

import com.fiveis.andcrowd.dto.and.AndDTO;
import com.fiveis.andcrowd.entity.and.And;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import java.util.List;

import static com.fiveis.andcrowd.entity.and.QAnd.and;

@Repository
public class AndQueryRepositoryImpl implements AndQueryRepository{

    private final EntityManager em;
    private final JPAQueryFactory query; // QueryDSL (JPA 쿼리)

    public AndQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em); // 주입된 EntityManager를 사용하여 JPAQueryFactory 인스턴스 생성 및 저장
    }

    public Slice<AndDTO.Find> findAllByCategoryAndStatusAndSort(
            Integer andCategoryId, Integer andStatus, String sortField, String sortOrder, String searchKeyword, Pageable pageable) {

        BooleanExpression categoryExpression = eqCategory(andCategoryId);
        if (andCategoryId != null && andCategoryId == 0) {
            // andCategoryId가 0인 경우, 조건식을 null로 변경하여 전체 데이터를 조회하도록 처리
            categoryExpression = null;
        }

        BooleanExpression searchExpression = eqAndTitle(searchKeyword);

        List<AndDTO.Find> results = query
                .select(Projections.fields(AndDTO.Find.class,
                        and.andId,
                        and.userId,
                        and.andCategoryId,
                        and.crowdId,
                        and.andTitle,
                        and.andHeaderImg,
                        and.andContent,
                        and.andEndDate,
                        and.needNumMem,
//                        and.andImg1,
//                        and.andImg2,
//                        and.andImg3,
//                        and.andImg4,
//                        and.andImg5,
                        and.publishedAt,
                        and.updatedAt,
                        and.andLikeCount,
                        and.andViewCount,
                        and.andStatus,
                        and.adId,
                        and.isDeleted
                ))
                .from(and)
                .where(
                        categoryExpression,
                        eqAndStatus(andStatus),
                        searchExpression,
                        eqIsDeletedFalse()
                )
                .orderBy(buildOrderSpecifier(sortField, sortOrder))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanExpression eqCategory(Integer andCategoryId) {
        return andCategoryId != null ? and.andCategoryId.eq(andCategoryId) : null;
    }

    private BooleanExpression eqIsDeletedFalse() {
        return and.isDeleted.eq(false);
    }

    private BooleanExpression eqAndStatus(Integer andStatus) {
        if (andStatus != null) {
            if (andStatus == 13) { // 13 입력시 (1:모집중 or 3:종료)인 글 반환
                return and.andStatus.in(1, 3);
            } else {
                return and.andStatus.eq(andStatus);
            }
        }
        return null;
    }

    private BooleanExpression eqAndTitle(String searchKeyword) {
        return StringUtils.hasText(searchKeyword) ? and.andTitle.contains(searchKeyword) : null;
    }

    private OrderSpecifier<?> buildOrderSpecifier(String sortField, String sortOrder) {
        if (StringUtils.isEmpty(sortField) || StringUtils.isEmpty(sortOrder)) {
            return and.publishedAt.desc();
        }

        OrderSpecifier<?> orderSpecifier;
        switch (sortField) {
            case "publishedAt":
                orderSpecifier = and.publishedAt.desc();
                break;
            case "andEndDate":
                orderSpecifier = and.andEndDate.asc();
                break;
            case "andLikeCount":
                orderSpecifier = and.andLikeCount.desc();
                break;
            case "andViewCount":
                orderSpecifier = and.andViewCount.desc();
                break;
            default:
                orderSpecifier = and.publishedAt.desc();
        }
        return orderSpecifier;
    }
}