package com.project.love_data.model.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1252575312L;

    public static final QUser user = new QUser("user");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final SetPath<com.project.love_data.model.service.Comment, com.project.love_data.model.service.QComment> cmtSet = this.<com.project.love_data.model.service.Comment, com.project.love_data.model.service.QComment>createSet("cmtSet", com.project.love_data.model.service.Comment.class, com.project.love_data.model.service.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath profile_pic = createString("profile_pic");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final SetPath<String, StringPath> roleSet = this.<String, StringPath>createSet("roleSet", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath social_info = createString("social_info");

    public final BooleanPath user_Activation = createBoolean("user_Activation");

    public final StringPath user_birth = createString("user_birth");

    public final StringPath user_email = createString("user_email");

    public final BooleanPath user_email_re = createBoolean("user_email_re");

    public final StringPath user_name = createString("user_name");

    public final StringPath user_nic = createString("user_nic");

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public final StringPath user_phone = createString("user_phone");

    public final StringPath user_pw = createString("user_pw");

    public final BooleanPath user_sex = createBoolean("user_sex");

    public final BooleanPath user_social = createBoolean("user_social");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

