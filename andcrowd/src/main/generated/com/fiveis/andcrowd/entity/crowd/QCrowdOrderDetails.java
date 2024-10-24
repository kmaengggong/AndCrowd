package com.fiveis.andcrowd.entity.crowd;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCrowdOrderDetails is a Querydsl query type for CrowdOrderDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrowdOrderDetails extends EntityPathBase<CrowdOrderDetails> {

    private static final long serialVersionUID = -1117498520L;

    public static final QCrowdOrderDetails crowdOrderDetails = new QCrowdOrderDetails("crowdOrderDetails");

    public final NumberPath<Integer> crowdId = createNumber("crowdId", Integer.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath merchantUid = createString("merchantUid");

    public final StringPath purchaseAddress = createString("purchaseAddress");

    public final NumberPath<Integer> purchaseAmount = createNumber("purchaseAmount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> purchaseDate = createDateTime("purchaseDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> purchaseId = createNumber("purchaseId", Integer.class);

    public final StringPath purchaseName = createString("purchaseName");

    public final StringPath purchasePhone = createString("purchasePhone");

    public final StringPath purchaseStatus = createString("purchaseStatus");

    public final NumberPath<Integer> rewardId = createNumber("rewardId", Integer.class);

    public final StringPath rewardName = createString("rewardName");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QCrowdOrderDetails(String variable) {
        super(CrowdOrderDetails.class, forVariable(variable));
    }

    public QCrowdOrderDetails(Path<? extends CrowdOrderDetails> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCrowdOrderDetails(PathMetadata metadata) {
        super(CrowdOrderDetails.class, metadata);
    }

}

