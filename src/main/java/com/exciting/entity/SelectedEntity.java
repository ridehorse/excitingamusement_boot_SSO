package com.exciting.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "selected")
@IdClass(SelectedEntityId.class)
@Data
public class SelectedEntity{
	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_id", unique = true, nullable = false)
    private int selected_id;

    @Id
    @Column(name = "amuse_id", nullable = false)
    private int amuse_id;

    @Id
    @Column(name = "member_id", nullable = false, length = 45)
    private String member_id;

}






