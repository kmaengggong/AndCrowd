package com.fiveis.andcrowd.entity.and;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChat is a Querydsl query type for Chat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChat extends EntityPathBase<Chat> {

    private static final long serialVersionUID = -1368350713L;

    public static final QChat chat = new QChat("chat");

    public final EnumPath<com.fiveis.andcrowd.enums.ChatStatus> chatStatus = createEnum("chatStatus", com.fiveis.andcrowd.enums.ChatStatus.class);

    public final StringPath fileDir = createString("fileDir");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public final DateTimePath<java.time.LocalDateTime> publishedAt = createDateTime("publishedAt", java.time.LocalDateTime.class);

    public final StringPath receiverName = createString("receiverName");

    public final NumberPath<Long> roomId = createNumber("roomId", Long.class);

    public final StringPath s3DataUrl = createString("s3DataUrl");

    public final StringPath senderName = createString("senderName");

    public QChat(String variable) {
        super(Chat.class, forVariable(variable));
    }

    public QChat(Path<? extends Chat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChat(PathMetadata metadata) {
        super(Chat.class, metadata);
    }

}

