package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorLocMapper is a Querydsl query type for CorLocMapper
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCorLocMapper extends EntityPathBase<CorLocMapper> {

    private static final long serialVersionUID = -1509106670L;

    public static final QCorLocMapper corLocMapper = new QCorLocMapper("corLocMapper");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> cl_No = createNumber("cl_No", Long.class);

    public final StringPath clm_uuid = createString("clm_uuid");

    public final NumberPath<Long> cor_no = createNumber("cor_no", Long.class);

    public final NumberPath<Integer> loc_index = createNumber("loc_index", Integer.class);

    public final NumberPath<Long> loc_no = createNumber("loc_no", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public QCorLocMapper(String variable) {
        super(CorLocMapper.class, forVariable(variable));
    }

    public QCorLocMapper(Path<? extends CorLocMapper> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorLocMapper(PathMetadata metadata) {
        super(CorLocMapper.class, metadata);
    }

}

