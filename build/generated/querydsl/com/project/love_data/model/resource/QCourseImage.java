package com.project.love_data.model.resource;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseImage is a Querydsl query type for CourseImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourseImage extends EntityPathBase<CourseImage> {

    private static final long serialVersionUID = -162254178L;

    public static final QCourseImage courseImage = new QCourseImage("courseImage");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> cor_no = createNumber("cor_no", Long.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final NumberPath<Long> img_no = createNumber("img_no", Long.class);

    public final StringPath img_url = createString("img_url");

    public final StringPath img_uuid = createString("img_uuid");

    public final BooleanPath is_deleted = createBoolean("is_deleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public QCourseImage(String variable) {
        super(CourseImage.class, forVariable(variable));
    }

    public QCourseImage(Path<? extends CourseImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseImage(PathMetadata metadata) {
        super(CourseImage.class, metadata);
    }

}

