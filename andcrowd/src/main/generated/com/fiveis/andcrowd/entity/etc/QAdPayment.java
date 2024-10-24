package com.fiveis.andcrowd.entity.etc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdPayment is a Querydsl query type for AdPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdPayment extends EntityPathBase<AdPayment> {

    private static final long serialVersionUID = -649121647L;

    public static final QAdPayment adPayment = new QAdPayment("adPayment");

    public final NumberPath<Integer> adId = createNumber("adId", Integer.class);

    public final NumberPath<Integer> adPaymentId = createNumber("adPaymentId", Integer.class);

    public final NumberPath<Integer> adPaymentStatus = createNumber("adPaymentStatus", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> expiredAt = createDateTime("expiredAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> projectId = createNumber("projectId", Integer.class);

    public final NumberPath<Integer> projectType = createNumber("projectType", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> purchasedAt = createDateTime("purchasedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QAdPayment(String variable) {
        super(AdPayment.class, forVariable(variable));
    }

    public QAdPayment(Path<? extends AdPayment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdPayment(PathMetadata metadata) {
        super(AdPayment.class, metadata);
    }

}

