package com.fiveis.andcrowd.entity.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1679909974L;

    public static final QUser user = new QUser("user");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<com.fiveis.andcrowd.enums.Role> role = createEnum("role", com.fiveis.andcrowd.enums.Role.class);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.fiveis.andcrowd.enums.SocialType> socialType = createEnum("socialType", com.fiveis.andcrowd.enums.SocialType.class);

    public final DateTimePath<java.time.LocalDateTime> userBirth = createDateTime("userBirth", java.time.LocalDateTime.class);

    public final StringPath userEmail = createString("userEmail");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath userKorName = createString("userKorName");

    public final NumberPath<Integer> userMarketing = createNumber("userMarketing", Integer.class);

    public final StringPath userNickname = createString("userNickname");

    public final StringPath userPassword = createString("userPassword");

    public final StringPath userPhone = createString("userPhone");

    public final NumberPath<Integer> userPrivacy = createNumber("userPrivacy", Integer.class);

    public final StringPath userProfileImg = createString("userProfileImg");

    public final DateTimePath<java.time.LocalDateTime> userRegister = createDateTime("userRegister", java.time.LocalDateTime.class);

    public final NumberPath<Integer> userTos = createNumber("userTos", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

