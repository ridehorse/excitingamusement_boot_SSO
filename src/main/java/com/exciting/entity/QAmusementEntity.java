package com.exciting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAmusementEntity is a Querydsl query type for AmusementEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAmusementEntity extends EntityPathBase<AmusementEntity> {

    private static final long serialVersionUID = 929659243L;

    public static final QAmusementEntity amusementEntity = new QAmusementEntity("amusementEntity");

    public final StringPath a_country = createString("a_country");

    public final StringPath a_img = createString("a_img");

    public final StringPath a_info = createString("a_info");

    public final NumberPath<Double> a_lat = createNumber("a_lat", Double.class);

    public final NumberPath<Double> a_lng = createNumber("a_lng", Double.class);

    public final StringPath a_name = createString("a_name");

    public final StringPath a_note = createString("a_note");

    public final DateTimePath<java.util.Date> a_opening = createDateTime("a_opening", java.util.Date.class);

    public final StringPath a_time = createString("a_time");

    public final NumberPath<Integer> a_view = createNumber("a_view", Integer.class);

    public final NumberPath<Integer> amuse_id = createNumber("amuse_id", Integer.class);

    public final NumberPath<Float> avg_grade = createNumber("avg_grade", Float.class);

    public final NumberPath<Integer> review_cnt = createNumber("review_cnt", Integer.class);

    public QAmusementEntity(String variable) {
        super(AmusementEntity.class, forVariable(variable));
    }

    public QAmusementEntity(Path<? extends AmusementEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAmusementEntity(PathMetadata metadata) {
        super(AmusementEntity.class, metadata);
    }

}

