package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserRecentLoc is a Querydsl query type for UserRecentLoc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRecentLoc extends EntityPathBase<UserRecentLoc> {

    private static final long serialVersionUID = 1230564579L;

    public static final QUserRecentLoc userRecentLoc = new QUserRecentLoc("userRecentLoc");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> loc_no = createNumber("loc_no", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Long> userRecentLoc_no = createNumber("userRecentLoc_no", Long.class);

    public final StringPath uuid = createString("uuid");

    public QUserRecentLoc(String variable) {
        super(UserRecentLoc.class, forVariable(variable));
    }

    public QUserRecentLoc(Path<? extends UserRecentLoc> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserRecentLoc(PathMetadata metadata) {
        super(UserRecentLoc.class, metadata);
    }

}

