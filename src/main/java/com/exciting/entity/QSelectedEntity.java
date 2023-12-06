package com.exciting.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSelectedEntity is a Querydsl query type for SelectedEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSelectedEntity extends EntityPathBase<SelectedEntity> {

    private static final long serialVersionUID = 1342728719L;

    public static final QSelectedEntity selectedEntity = new QSelectedEntity("selectedEntity");

    public final NumberPath<Integer> amuse_id = createNumber("amuse_id", Integer.class);

    public final StringPath member_id = createString("member_id");

    public final NumberPath<Integer> selected_id = createNumber("selected_id", Integer.class);

    public QSelectedEntity(String variable) {
        super(SelectedEntity.class, forVariable(variable));
    }

    public QSelectedEntity(Path<? extends SelectedEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSelectedEntity(PathMetadata metadata) {
        super(SelectedEntity.class, metadata);
    }

}

