package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserLikeLoc is a Querydsl query type for UserLikeLoc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserLikeLoc extends EntityPathBase<UserLikeLoc> {

    private static final long serialVersionUID = -89026137L;

    public static final QUserLikeLoc userLikeLoc = new QUserLikeLoc("userLikeLoc");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> loc_no = createNumber("loc_no", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Long> userLikeLoc_no = createNumber("userLikeLoc_no", Long.class);

    public final StringPath uuid = createString("uuid");

    public QUserLikeLoc(String variable) {
        super(UserLikeLoc.class, forVariable(variable));
    }

    public QUserLikeLoc(Path<? extends UserLikeLoc> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserLikeLoc(PathMetadata metadata) {
        super(UserLikeLoc.class, metadata);
    }

}

