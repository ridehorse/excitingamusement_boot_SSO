package com.exciting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAimageEntity is a Querydsl query type for AimageEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAimageEntity extends EntityPathBase<AimageEntity> {

    private static final long serialVersionUID = -1124535794L;

    public static final QAimageEntity aimageEntity = new QAimageEntity("aimageEntity");

    public final NumberPath<Integer> aimg_id = createNumber("aimg_id", Integer.class);

    public final NumberPath<Integer> amuse_id = createNumber("amuse_id", Integer.class);

    public final StringPath url = createString("url");

    public QAimageEntity(String variable) {
        super(AimageEntity.class, forVariable(variable));
    }

    public QAimageEntity(Path<? extends AimageEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAimageEntity(PathMetadata metadata) {
        super(AimageEntity.class, metadata);
    }

}

