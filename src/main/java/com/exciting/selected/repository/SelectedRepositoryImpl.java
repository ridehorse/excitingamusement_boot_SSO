package com.exciting.selected.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.dto.SelectedAmusementAimageDTO;
import com.exciting.entity.QAimageEntity;
import com.exciting.entity.QAmusementEntity;
import com.exciting.entity.QSelectedEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class SelectedRepositoryImpl implements CustomSelectedRepository{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<SelectedAmusementAimageDTO> selectedList(Map<String, Object> map) {
		QSelectedEntity selected = QSelectedEntity.selectedEntity;
        QAmusementEntity amusement = QAmusementEntity.amusementEntity;
        QAimageEntity aimage = QAimageEntity.aimageEntity;
        String memberId = (String)map.get("member_id");
        Integer selectedId = (Integer)map.get("selected_id");
        Integer limitCount = (Integer)map.get("limitCount");
        
        JPAQueryFactory query = new JPAQueryFactory(em);

        JPAQuery<SelectedAmusementAimageDTO> jpaQuery = query
                .select(Projections.fields(SelectedAmusementAimageDTO.class,
                        selected.selected_id, selected.amuse_id, amusement.a_name, amusement.a_country,
                        amusement.a_time, aimage.url))
                .from(selected)
                .innerJoin(amusement)
                .on(selected.amuse_id.eq(amusement.amuse_id))
                .innerJoin(aimage)
                .on(amusement.amuse_id.eq(aimage.amuse_id))
                .where(selected.member_id.eq(memberId));

        if (selectedId != null) {
            jpaQuery.where(selected.selected_id.lt(selectedId));
        }

        	jpaQuery.groupBy(selected.amuse_id,aimage.url)
                .orderBy(selected.selected_id.desc())
                .limit(limitCount);

    	return jpaQuery.fetch();
	}

	@Override
	public Long count(Map<String, Object> map) {
		QSelectedEntity selected = QSelectedEntity.selectedEntity;
        QAmusementEntity amusement = QAmusementEntity.amusementEntity;
        QAimageEntity aimage = QAimageEntity.aimageEntity;
        String member_id = (String)map.get("member_id");
        System.out.println("SelectedRepositoryImpl.count() // map : "+map);
        System.out.println("SelectedRepositoryImpl.count() // member_id : "+member_id);
        Integer selected_id = (Integer)map.get("selected_id");
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        
        JPAQuery<Long> jpaQuery = queryFactory
                .select(selected.amuse_id.countDistinct().as("cnt"))
                .from(selected)
                .innerJoin(amusement)
                .on(selected.amuse_id.eq(amusement.amuse_id))
                .innerJoin(aimage)
                .on(amusement.amuse_id.eq(aimage.amuse_id))
                .where(selected.member_id.eq(member_id));

            if (selected_id != null) {
                jpaQuery.where(selected.selected_id.lt(selected_id));
            }

            return jpaQuery.fetchFirst();
	}

}
