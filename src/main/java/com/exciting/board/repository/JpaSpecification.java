package com.exciting.board.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.exciting.dto.BoardFavoriteDTO;
import com.exciting.entity.BoardFavoriteEntity;
import com.exciting.entity.BoardImgEntity;

public class JpaSpecification {
//	public static Specification<?> equalImgData(int key,int value) {
//        return new Specification<BoardImgEntity>() {
//			@Override
//            public Predicate toPredicate(Root<BoardImgEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                // 1) equal
//					
//                return criteriaBuilder.equal(root.get(key), value);
//            }
//        };
//    }
	
	
	public static Specification<?> LikeSearch(String searchValue,String type) {
        return (root, query, criteriaBuilder) -> {
            if (searchValue == null || searchValue.isEmpty() ) {
                return criteriaBuilder.notEqual(root.get(type), "답변");
            } else {
                return criteriaBuilder.like(root.get(type), "%" + searchValue + "%");
            }
        };
    }
	
	
	
	public static Specification<BoardImgEntity> equalInteger(String key, Integer value) {
	    return (root, query, criteriaBuilder) -> {
	        if (key != null || value != null) {
	            return criteriaBuilder.equal(root.get(key), value);
	        } else {
	            return criteriaBuilder.equal(root.get(key), value);
	        }
	    };
	}
	
	public static Specification<?> equalString(String key, String value) {
	    return (root, query, criteriaBuilder) -> {
	    	System.out.println(value);
	    	System.out.println(value!=("전체"));
	        if (key != null && value != null && !value.equals("전체")) {
	        	System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!"+key+" "+value);
	            return criteriaBuilder.equal(root.get(key), value);
	        } else {
	            return criteriaBuilder.isNotNull(root.get(key));
	        }
	    };
	}
	
//	
}
