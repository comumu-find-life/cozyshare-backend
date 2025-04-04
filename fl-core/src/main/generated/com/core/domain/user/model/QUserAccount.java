package com.core.domain.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAccount is a Querydsl query type for UserAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAccount extends EntityPathBase<UserAccount> {

    private static final long serialVersionUID = -1327916286L;

    public static final QUserAccount userAccount = new QUserAccount("userAccount");

    public final ListPath<PointHistory, QPointHistory> chargeHistories = this.<PointHistory, QPointHistory>createList("chargeHistories", PointHistory.class, QPointHistory.class, PathInits.DIRECT2);

    public final StringPath depositorName = createString("depositorName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath paypalInformation = createString("paypalInformation");

    public final NumberPath<Double> point = createNumber("point", Double.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserAccount(String variable) {
        super(UserAccount.class, forVariable(variable));
    }

    public QUserAccount(Path<? extends UserAccount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAccount(PathMetadata metadata) {
        super(UserAccount.class, metadata);
    }

}

