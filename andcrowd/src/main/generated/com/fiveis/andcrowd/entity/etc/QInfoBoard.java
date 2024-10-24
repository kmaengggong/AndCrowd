package com.fiveis.andcrowd.entity.etc;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInfoBoard is a Querydsl query type for InfoBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInfoBoard extends EntityPathBase<InfoBoard> {

    private static final long serialVersionUID = 719660806L;

    public static final QInfoBoard infoBoard = new QInfoBoard("infoBoard");

    public final StringPath infoContent = createString("infoContent");

    public final NumberPath<Integer> infoId = createNumber("infoId", Integer.class);

    public final StringPath infoTitle = createString("infoTitle");

    public final BooleanPath infoType = createBoolean("infoType");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final DateTimePath<java.time.LocalDateTime> publishedAt = createDateTime("publishedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public QInfoBoard(String variable) {
        super(InfoBoard.class, forVariable(variable));
    }

    public QInfoBoard(Path<? extends InfoBoard> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInfoBoard(PathMetadata metadata) {
        super(InfoBoard.class, metadata);
    }

}

