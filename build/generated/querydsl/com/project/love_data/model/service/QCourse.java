package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = -338856270L;

    public static final QCourse course = new QCourse("course");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final StringPath cor_name = createString("cor_name");

    public final NumberPath<Long> cor_no = createNumber("cor_no", Long.class);

    public final StringPath cor_uuid = createString("cor_uuid");

    public final StringPath cost = createString("cost");

    public final StringPath est_type = createString("est_type");

    public final StringPath est_value = createString("est_value");

    public final StringPath info = createString("info");

    public final BooleanPath is_deleted = createBoolean("is_deleted");

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Integer> location_count = createNumber("location_count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final SetPath<String, StringPath> tagSet = this.<String, StringPath>createSet("tagSet", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath transportation = createString("transportation");

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QCourse(String variable) {
        super(Course.class, forVariable(variable));
    }

    public QCourse(Path<? extends Course> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourse(PathMetadata metadata) {
        super(Course.class, metadata);
    }

}

