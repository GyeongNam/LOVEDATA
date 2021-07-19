package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserLikeCor is a Querydsl query type for UserLikeCor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserLikeCor extends EntityPathBase<UserLikeCor> {

    private static final long serialVersionUID = -89034771L;

    public static final QUserLikeCor userLikeCor = new QUserLikeCor("userLikeCor");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> cor_no = createNumber("cor_no", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Long> userLikeCor_no = createNumber("userLikeCor_no", Long.class);

    public final StringPath uuid = createString("uuid");

    public QUserLikeCor(String variable) {
        super(UserLikeCor.class, forVariable(variable));
    }

    public QUserLikeCor(Path<? extends UserLikeCor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserLikeCor(PathMetadata metadata) {
        super(UserLikeCor.class, metadata);
    }

}

