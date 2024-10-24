package com.fiveis.andcrowd.entity.etc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAd is a Querydsl query type for Ad
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAd extends EntityPathBase<Ad> {

    private static final long serialVersionUID = -493685835L;

    public static final QAd ad = new QAd("ad");

    public final NumberPath<Integer> adId = createNumber("adId", Integer.class);

    public final StringPath adName = createString("adName");

    public final NumberPath<Integer> adPrice = createNumber("adPrice", Integer.class);

    public QAd(String variable) {
        super(Ad.class, forVariable(variable));
    }

    public QAd(Path<? extends Ad> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAd(PathMetadata metadata) {
        super(Ad.class, metadata);
    }

}

