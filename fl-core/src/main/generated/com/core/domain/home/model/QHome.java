package com.core.domain.home.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHome is a Querydsl query type for Home
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHome extends EntityPathBase<Home> {

    private static final long serialVersionUID = 2015153195L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHome home = new QHome("home");

    public final com.core.domain.base.QBaseTimeEntity _super = new com.core.domain.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final QHomeAddress homeAddress;

    public final QHomeInfo homeInfo;

    public final EnumPath<HomeStatus> homeStatus = createEnum("homeStatus", HomeStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<HomeImage, QHomeImage> images = this.<HomeImage, QHomeImage>createList("images", HomeImage.class, QHomeImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QHome(String variable) {
        this(Home.class, forVariable(variable), INITS);
    }

    public QHome(Path<? extends Home> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHome(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHome(PathMetadata metadata, PathInits inits) {
        this(Home.class, metadata, inits);
    }

    public QHome(Class<? extends Home> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.homeAddress = inits.isInitialized("homeAddress") ? new QHomeAddress(forProperty("homeAddress")) : null;
        this.homeInfo = inits.isInitialized("homeInfo") ? new QHomeInfo(forProperty("homeInfo")) : null;
    }

}

