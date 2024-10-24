package com.fiveis.andcrowd.entity.and;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnd is a Querydsl query type for And
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnd extends EntityPathBase<And> {

    private static final long serialVersionUID = -1845257400L;

    public static final QAnd and = new QAnd("and1");

    public final NumberPath<Integer> adId = createNumber("adId", Integer.class);

    public final NumberPath<Integer> andCategoryId = createNumber("andCategoryId", Integer.class);

    public final StringPath andContent = createString("andContent");

    public final DatePath<java.time.LocalDate> andEndDate = createDate("andEndDate", java.time.LocalDate.class);

    public final StringPath andHeaderImg = createString("andHeaderImg");

    public final NumberPath<Integer> andId = createNumber("andId", Integer.class);

    public final NumberPath<Integer> andLikeCount = createNumber("andLikeCount", Integer.class);

    public final NumberPath<Integer> andStatus = createNumber("andStatus", Integer.class);

    public final StringPath andTitle = createString("andTitle");

    public final NumberPath<Integer> andViewCount = createNumber("andViewCount", Integer.class);

    public final NumberPath<Integer> crowdId = createNumber("crowdId", Integer.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Integer> needNumMem = createNumber("needNumMem", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> publishedAt = createDateTime("publishedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAnd(String variable) {
        super(And.class, forVariable(variable));
    }

    public QAnd(Path<? extends And> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnd(PathMetadata metadata) {
        super(And.class, metadata);
    }

}

