package com.fiveis.andcrowd.entity.and;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAndCategory is a Querydsl query type for AndCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAndCategory extends EntityPathBase<AndCategory> {

    private static final long serialVersionUID = 139785318L;

    public static final QAndCategory andCategory = new QAndCategory("andCategory");

    public final NumberPath<Integer> andCategoryId = createNumber("andCategoryId", Integer.class);

    public final StringPath andCategoryName = createString("andCategoryName");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public QAndCategory(String variable) {
        super(AndCategory.class, forVariable(variable));
    }

    public QAndCategory(Path<? extends AndCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAndCategory(PathMetadata metadata) {
        super(AndCategory.class, metadata);
    }

}

