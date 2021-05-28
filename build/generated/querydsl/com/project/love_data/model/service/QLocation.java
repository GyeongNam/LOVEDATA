package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = -1248924532L;

    public static final QLocation location = new QLocation("location");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final StringPath addrDetail = createString("addrDetail");

    public final SetPath<Comment, QComment> cmtSet = this.<Comment, QComment>createSet("cmtSet", Comment.class, QComment.class, PathInits.DIRECT2);

    public final ListPath<com.project.love_data.model.resource.Image, com.project.love_data.model.resource.QImage> imgList = this.<com.project.love_data.model.resource.Image, com.project.love_data.model.resource.QImage>createList("imgList", com.project.love_data.model.resource.Image.class, com.project.love_data.model.resource.QImage.class, PathInits.DIRECT2);

    public final StringPath info = createString("info");

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final StringPath loc_name = createString("loc_name");

    public final NumberPath<Long> loc_no = createNumber("loc_no", Long.class);

    public final StringPath loc_uuid = createString("loc_uuid");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath roadAddr = createString("roadAddr");

    public final StringPath siDo = createString("siDo");

    public final StringPath siGunGu = createString("siGunGu");

    public final SetPath<String, StringPath> tagSet = this.<String, StringPath>createSet("tagSet", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath tel = createString("tel");

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public final StringPath zipNo = createString("zipNo");

    public QLocation(String variable) {
        super(Location.class, forVariable(variable));
    }

    public QLocation(Path<? extends Location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocation(PathMetadata metadata) {
        super(Location.class, metadata);
    }

}

