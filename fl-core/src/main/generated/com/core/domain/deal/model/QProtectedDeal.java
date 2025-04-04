package com.core.domain.deal.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProtectedDeal is a Querydsl query type for ProtectedDeal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProtectedDeal extends EntityPathBase<ProtectedDeal> {

    private static final long serialVersionUID = 75018875L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProtectedDeal protectedDeal = new QProtectedDeal("protectedDeal");

    public final com.core.domain.base.QBaseTimeEntity _super = new com.core.domain.base.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDateTime = _super.createDateTime;

    public final EnumPath<DealState> dealState = createEnum("dealState", DealState.class);

    public final NumberPath<Double> deposit = createNumber("deposit", Double.class);

    public final NumberPath<Long> dmId = createNumber("dmId", Long.class);

    public final NumberPath<Long> getterId = createNumber("getterId", Long.class);

    public final NumberPath<Long> homeId = createNumber("homeId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDateTime = _super.modifiedDateTime;

    public final QProtectedDealDateTime protectedDealDateTime;

    public final NumberPath<Long> providerId = createNumber("providerId", Long.class);

    public QProtectedDeal(String variable) {
        this(ProtectedDeal.class, forVariable(variable), INITS);
    }

    public QProtectedDeal(Path<? extends ProtectedDeal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProtectedDeal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProtectedDeal(PathMetadata metadata, PathInits inits) {
        this(ProtectedDeal.class, metadata, inits);
    }

    public QProtectedDeal(Class<? extends ProtectedDeal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.protectedDealDateTime = inits.isInitialized("protectedDealDateTime") ? new QProtectedDealDateTime(forProperty("protectedDealDateTime")) : null;
    }

}

