package com.project.love_data.model.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCalender is a Querydsl query type for Calender
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCalender extends EntityPathBase<Calender> {

    private static final long serialVersionUID = 966674577L;

    public static final QCalender calender = new QCalender("calender");

    public final com.project.love_data.model.base.QTimeEntity _super = new com.project.love_data.model.base.QTimeEntity(this);

    public final BooleanPath all_day = createBoolean("all_day");

    public final NumberPath<Long> cal_no = createNumber("cal_no", Long.class);

    public final StringPath color = createString("color");

    public final StringPath end = createString("end");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath place = createString("place");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath start = createString("start");

    public final StringPath text = createString("text");

    public final StringPath title = createString("title");

    public final NumberPath<Long> user_no = createNumber("user_no", Long.class);

    public QCalender(String variable) {
        super(Calender.class, forVariable(variable));
    }

    public QCalender(Path<? extends Calender> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCalender(PathMetadata metadata) {
        super(Calender.class, metadata);
    }

}

