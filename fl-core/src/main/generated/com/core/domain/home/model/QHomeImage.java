package com.core.domain.home.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHomeImage is a Querydsl query type for HomeImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomeImage extends EntityPathBase<HomeImage> {

    private static final long serialVersionUID = -1550135312L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHomeImage homeImage = new QHomeImage("homeImage");

    public final com.core.domain.base.QBaseTimeEntity _super = new com.core.domain.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final QHome home;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public QHomeImage(String variable) {
        this(HomeImage.class, forVariable(variable), INITS);
    }

    public QHomeImage(Path<? extends HomeImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHomeImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHomeImage(PathMetadata metadata, PathInits inits) {
        this(HomeImage.class, metadata, inits);
    }

    public QHomeImage(Class<? extends HomeImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.home = inits.isInitialized("home") ? new QHome(forProperty("home"), inits.get("home")) : null;
    }

}

