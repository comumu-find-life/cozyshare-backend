package com.core.domain.home.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHomeInfo is a Querydsl query type for HomeInfo
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QHomeInfo extends BeanPath<HomeInfo> {

    private static final long serialVersionUID = 1196922745L;

    public static final QHomeInfo homeInfo = new QHomeInfo("homeInfo");

    public final NumberPath<Integer> bathRoomCount = createNumber("bathRoomCount", Integer.class);

    public final NumberPath<Integer> bedroomCount = createNumber("bedroomCount", Integer.class);

    public final NumberPath<Integer> bill = createNumber("bill", Integer.class);

    public final NumberPath<Integer> bond = createNumber("bond", Integer.class);

    public final BooleanPath canParking = createBoolean("canParking");

    public final EnumPath<com.core.domain.user.model.Gender> gender = createEnum("gender", com.core.domain.user.model.Gender.class);

    public final StringPath introduce = createString("introduce");

    public final StringPath options = createString("options");

    public final NumberPath<Integer> rent = createNumber("rent", Integer.class);

    public final NumberPath<Integer> residentCount = createNumber("residentCount", Integer.class);

    public final EnumPath<HomeType> type = createEnum("type", HomeType.class);

    public QHomeInfo(String variable) {
        super(HomeInfo.class, forVariable(variable));
    }

    public QHomeInfo(Path<? extends HomeInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHomeInfo(PathMetadata metadata) {
        super(HomeInfo.class, metadata);
    }

}

