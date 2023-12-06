package com.exciting.list.repository;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.entity.QAimageEntity;
import com.exciting.entity.QAmusementEntity;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ListRepositoryImpl implements CustomListRepository{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<AmusementAimageDTO> selectList(Map<String, Object> map) {
		QAmusementEntity am = QAmusementEntity.amusementEntity;
        QAimageEntity ai = QAimageEntity.aimageEntity;
        String searchName = (String)(map.get("searchName"));
        String searchCountry = (String)(map.get("searchCountry"));
        String order = (String)(map.get("order"));
        Integer start = (Integer)(map.get("start"));
        Integer end = (Integer)(map.get("end"));

        JPAQueryFactory query = new JPAQueryFactory(em);

        JPAQuery<AmusementAimageDTO> jpaQuery = query.select(
                Projections.constructor(AmusementAimageDTO.class,
                        am.amuse_id,
                        am.a_name,
                        am.a_country,
                        am.a_time,
                        am.a_lat,
                        am.a_lng,
                        ai.url
                ))
                .from(am)
                .innerJoin(ai)
                .on(am.amuse_id.eq(ai.amuse_id));

        if (searchName != null && !searchName.isEmpty()) {
            jpaQuery.where(am.a_name.likeIgnoreCase("%" + searchName + "%"));
        }

        if (searchCountry != null && !searchCountry.isEmpty()) {
            jpaQuery.where(am.a_country.eq(searchCountry));
        }

        jpaQuery.groupBy(am.amuse_id, am.a_name, am.a_country, am.a_time, am.a_lat, am.a_lng, ai.url);

        if (order != null && !order.isEmpty()) {
            OrderSpecifier<?> orderSpecifier;
            switch (order) {
                case "신설순":
                    orderSpecifier = am.a_opening.desc();
                    break;
                case "조회순":
                    orderSpecifier = am.a_view.desc();
                    break;
                default:
                    orderSpecifier = am.amuse_id.desc();
                    break;
            }
            jpaQuery.orderBy(orderSpecifier);
        } else {
            jpaQuery.orderBy(am.amuse_id.desc());
        }

        jpaQuery.offset(start)
                .limit(end);

        return jpaQuery.fetch();
	}
	
}
