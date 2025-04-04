package com.core.domain.user.model;

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

    private static final long serialVersionUID = 2141946027L;

    public static final QUser user = new QUser("user");

    public final com.core.domain.base.QBaseTimeEntity _super = new com.core.domain.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final StringPath email = createString("email");

    public final StringPath fcmToken = createString("fcmToken");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    public final StringPath job = createString("job");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final StringPath nationality = createString("nationality");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileUrl = createString("profileUrl");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<SignupType> signupType = createEnum("signupType", SignupType.class);

    public final EnumPath<UserState> userState = createEnum("userState", UserState.class);

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

