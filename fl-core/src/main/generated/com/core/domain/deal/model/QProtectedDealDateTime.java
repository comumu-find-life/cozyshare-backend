package com.core.domain.deal.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProtectedDealDateTime is a Querydsl query type for ProtectedDealDateTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProtectedDealDateTime extends EntityPathBase<ProtectedDealDateTime> {

    private static final long serialVersionUID = 1295883062L;

    public static final QProtectedDealDateTime protectedDealDateTime = new QProtectedDealDateTime("protectedDealDateTime");

    public final DateTimePath<java.time.LocalDateTime> cancelAt = createDateTime("cancelAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> completeAt = createDateTime("completeAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> dealAt = createDateTime("dealAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> startAt = createDateTime("startAt", java.time.LocalDateTime.class);

    public QProtectedDealDateTime(String variable) {
        super(ProtectedDealDateTime.class, forVariable(variable));
    }

    public QProtectedDealDateTime(Path<? extends ProtectedDealDateTime> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProtectedDealDateTime(PathMetadata metadata) {
        super(ProtectedDealDateTime.class, metadata);
    }

}

