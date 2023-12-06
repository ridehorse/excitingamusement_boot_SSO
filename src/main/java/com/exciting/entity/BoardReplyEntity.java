package com.exciting.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name="boardreply")
public class BoardReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_num")
    private int reply_num;

    @Column(name = "board_id")
    private int board_id;

    @Column(name = "member_id")
    private String member_id;

    @Column(name = "b_reply")
    private String b_reply;

    @Column(name = "postdate")
    private LocalDateTime postdate;

    @Column(name = "ref")
    private int ref;

    @Column(name = "seq")
    private int seq;

    @Column(name = "lev")
    private int lev;
}
