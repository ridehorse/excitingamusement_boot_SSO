package com.exciting.index.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.exciting.dto.AmusementAimageDTO;
import com.exciting.entity.QAimageEntity;
import com.exciting.entity.QAmusementEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository
public class IndexRepositoryImpl implements CustomIndexRepository{
  @PersistenceContext
    private EntityManager entityManager;
  @PersistenceContext
  	private EntityManager em;
  
@Override
public List<AmusementAimageDTO> selectListAmuseInfoKoreaDSL() {
	QAmusementEntity amusement = QAmusementEntity.amusementEntity;
	QAimageEntity aimage = QAimageEntity.aimageEntity;

	JPAQueryFactory queryFactory = new JPAQueryFactory(em);

	JPAQuery<AmusementAimageDTO> jpaQuery = queryFactory
	        .select(Projections.constructor(AmusementAimageDTO.class,
	                amusement.amuse_id,
	                amusement.a_name,
	                amusement.a_country,
	                amusement.a_time,
	                aimage.url))
	        .from(amusement)
	        .join(aimage)
	        .on(amusement.amuse_id.eq(aimage.amuse_id))
	        .where(amusement.a_country.eq("대한민국"))
	        .groupBy(amusement.amuse_id,   // GROUP BY 절에 나열되지 않은 열 추가
                    amusement.a_name,      // GROUP BY 절에 나열되지 않은 열 추가
                    amusement.a_country,   // GROUP BY 절에 나열되지 않은 열 추가
                    amusement.a_time,      // GROUP BY 절에 나열되지 않은 열 추가
                    aimage.url)            // GROUP BY 절에 나열되지 않은 열 추가
	        .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
	        .limit(4);

	return jpaQuery.fetch();
}
@Override
public List<AmusementAimageDTO> selectListAmuseInfoForeignDSL() {
	QAmusementEntity amusement = QAmusementEntity.amusementEntity;
	QAimageEntity aimage = QAimageEntity.aimageEntity;

	JPAQueryFactory queryFactory = new JPAQueryFactory(em);

	JPAQuery<AmusementAimageDTO> jpaQuery = queryFactory
	        .select(Projections.constructor(AmusementAimageDTO.class,
	                amusement.amuse_id,
	                amusement.a_name,
	                amusement.a_country,
	                amusement.a_time,
	                aimage.url))
	        .from(amusement)
	        .join(aimage)
	        .on(amusement.amuse_id.eq(aimage.amuse_id))
	        .where(amusement.a_country.ne("대한민국"))
	        .groupBy(amusement.amuse_id,   // GROUP BY 절에 나열되지 않은 열 추가
                    amusement.a_name,      // GROUP BY 절에 나열되지 않은 열 추가
                    amusement.a_country,   // GROUP BY 절에 나열되지 않은 열 추가
                    amusement.a_time,      // GROUP BY 절에 나열되지 않은 열 추가
                    aimage.url)            // GROUP BY 절에 나열되지 않은 열 추가
	        .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
	        .limit(4);

	return jpaQuery.fetch();
}
}
