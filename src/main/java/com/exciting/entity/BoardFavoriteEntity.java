package com.exciting.entity;

import javax.persistence.*;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="favoriteboard")
public class BoardFavoriteEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="favorite_num")
    private int favorite_num;
	
    private int board_id;

    @Column(nullable = false)
    private String member_id;
    
    private int favorite;
    
    private int hate;
}
