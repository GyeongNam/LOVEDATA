package com.project.love_data.model.resource;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationImage is a Querydsl query type for LocationImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocationImage extends EntityPathBase<LocationImage> {

    private static final long serialVersionUID = 1913877444L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationImage locationImage = new QLocationImage("locationImage");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final NumberPath<Long> img_no = createNumber("img_no", Long.class);

    public final StringPath img_url = createString("img_url");

    public final StringPath img_uuid = createString("img_uuid");

    public final BooleanPath is_deleted = createBoolean("is_deleted");

    public final com.project.love_data.model.service.QLocation location;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public QLocationImage(String variable) {
        this(LocationImage.class, forVariable(variable), INITS);
    }

    public QLocationImage(Path<? extends LocationImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationImage(PathMetadata metadata, PathInits inits) {
        this(LocationImage.class, metadata, inits);
    }

    public QLocationImage(Class<? extends LocationImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.location = inits.isInitialized("location") ? new com.project.love_data.model.service.QLocation(forProperty("location")) : null;
    }

}

