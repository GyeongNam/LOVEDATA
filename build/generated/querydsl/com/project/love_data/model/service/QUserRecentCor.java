package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserRecentCor is a Querydsl query type for UserRecentCor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRecentCor extends EntityPathBase<UserRecentCor> {

    private static final long serialVersionUID = 1230555945L;

    public static final QUserRecentCor userRecentCor = new QUserRecentCor("userRecentCor");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> cor_no = createNumber("cor_no", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Long> userRecentCor_no = createNumber("userRecentCor_no", Long.class);

    public final StringPath uuid = createString("uuid");

    public QUserRecentCor(String variable) {
        super(UserRecentCor.class, forVariable(variable));
    }

    public QUserRecentCor(Path<? extends UserRecentCor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserRecentCor(PathMetadata metadata) {
        super(UserRecentCor.class, metadata);
    }

}

