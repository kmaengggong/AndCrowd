package com.fiveis.andcrowd.entity.crowd;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCrowd is a Querydsl query type for Crowd
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrowd extends EntityPathBase<Crowd> {

    private static final long serialVersionUID = 712171060L;

    public static final QCrowd crowd = new QCrowd("crowd");

    public final NumberPath<Integer> adId = createNumber("adId", Integer.class);

    public final NumberPath<Integer> andId = createNumber("andId", Integer.class);

    public final NumberPath<Integer> crowdCategoryId = createNumber("crowdCategoryId", Integer.class);

    public final StringPath crowdContent = createString("crowdContent");

    public final DatePath<java.time.LocalDate> crowdEndDate = createDate("crowdEndDate", java.time.LocalDate.class);

    public final NumberPath<Integer> crowdGoal = createNumber("crowdGoal", Integer.class);

    public final NumberPath<Integer> crowdId = createNumber("crowdId", Integer.class);

    public final NumberPath<Integer> crowdStatus = createNumber("crowdStatus", Integer.class);

    public final StringPath crowdTitle = createString("crowdTitle");

    public final StringPath headerImg = createString("headerImg");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final NumberPath<Integer> likeSum = createNumber("likeSum", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> publishedAt = createDateTime("publishedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QCrowd(String variable) {
        super(Crowd.class, forVariable(variable));
    }

    public QCrowd(Path<? extends Crowd> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCrowd(PathMetadata metadata) {
        super(Crowd.class, metadata);
    }

}

