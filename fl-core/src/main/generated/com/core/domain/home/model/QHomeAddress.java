package com.core.domain.home.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHomeAddress is a Querydsl query type for HomeAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHomeAddress extends EntityPathBase<HomeAddress> {

    private static final long serialVersionUID = 1908961609L;

    public static final QHomeAddress homeAddress = new QHomeAddress("homeAddress");

    public final com.core.domain.base.QBaseTimeEntity _super = new com.core.domain.base.QBaseTimeEntity(this);

    public final StringPath city = createString("city");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final StringPath detailAddress = createString("detailAddress");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final NumberPath<Integer> postCode = createNumber("postCode", Integer.class);

    public final StringPath state = createString("state");

    public final StringPath streetCode = createString("streetCode");

    public final StringPath streetName = createString("streetName");

    public QHomeAddress(String variable) {
        super(HomeAddress.class, forVariable(variable));
    }

    public QHomeAddress(Path<? extends HomeAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHomeAddress(PathMetadata metadata) {
        super(HomeAddress.class, metadata);
    }

}

