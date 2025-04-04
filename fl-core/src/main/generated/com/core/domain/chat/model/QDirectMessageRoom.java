package com.core.domain.chat.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDirectMessageRoom is a Querydsl query type for DirectMessageRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDirectMessageRoom extends EntityPathBase<DirectMessageRoom> {

    private static final long serialVersionUID = -1016680058L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDirectMessageRoom directMessageRoom = new QDirectMessageRoom("directMessageRoom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> progressHomeId = createNumber("progressHomeId", Long.class);

    public final com.core.domain.user.model.QUser user1;

    public final com.core.domain.user.model.QUser user2;

    public QDirectMessageRoom(String variable) {
        this(DirectMessageRoom.class, forVariable(variable), INITS);
    }

    public QDirectMessageRoom(Path<? extends DirectMessageRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDirectMessageRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDirectMessageRoom(PathMetadata metadata, PathInits inits) {
        this(DirectMessageRoom.class, metadata, inits);
    }

    public QDirectMessageRoom(Class<? extends DirectMessageRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user1 = inits.isInitialized("user1") ? new com.core.domain.user.model.QUser(forProperty("user1")) : null;
        this.user2 = inits.isInitialized("user2") ? new com.core.domain.user.model.QUser(forProperty("user2")) : null;
    }

}

