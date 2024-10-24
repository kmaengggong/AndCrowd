package com.fiveis.andcrowd.entity.crowd;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCrowdCategory is a Querydsl query type for CrowdCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrowdCategory extends EntityPathBase<CrowdCategory> {

    private static final long serialVersionUID = 360221522L;

    public static final QCrowdCategory crowdCategory = new QCrowdCategory("crowdCategory");

    public final NumberPath<Integer> crowdCategoryId = createNumber("crowdCategoryId", Integer.class);

    public final StringPath crowdCategoryName = createString("crowdCategoryName");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public QCrowdCategory(String variable) {
        super(CrowdCategory.class, forVariable(variable));
    }

    public QCrowdCategory(Path<? extends CrowdCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCrowdCategory(PathMetadata metadata) {
        super(CrowdCategory.class, metadata);
    }

}

