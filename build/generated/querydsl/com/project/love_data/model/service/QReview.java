package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 81366511L;

    public static final QReview review = new QReview("review");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> corNo = createNumber("corNo", Long.class);

    public final BooleanPath is_deleted = createBoolean("is_deleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> reported_count = createNumber("reported_count", Long.class);

    public final NumberPath<Long> rev_dislike = createNumber("rev_dislike", Long.class);

    public final NumberPath<Long> rev_like = createNumber("rev_like", Long.class);

    public final StringPath revContent = createString("revContent");

    public final NumberPath<Long> revIdx = createNumber("revIdx", Long.class);

    public final NumberPath<Long> revNo = createNumber("revNo", Long.class);

    public final StringPath revUuid = createString("revUuid");

    public final NumberPath<Integer> sc_loc = createNumber("sc_loc", Integer.class);

    public final NumberPath<Integer> sc_move = createNumber("sc_move", Integer.class);

    public final NumberPath<Integer> sc_revisit = createNumber("sc_revisit", Integer.class);

    public final NumberPath<Integer> sc_time = createNumber("sc_time", Integer.class);

    public final NumberPath<Float> sc_total = createNumber("sc_total", Float.class);

    public final StringPath user_name = createString("user_name");

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Long> view_count = createNumber("view_count", Long.class);

    public QReview(String variable) {
        super(Review.class, forVariable(variable));
    }

    public QReview(Path<? extends Review> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReview(PathMetadata metadata) {
        super(Review.class, metadata);
    }

}

