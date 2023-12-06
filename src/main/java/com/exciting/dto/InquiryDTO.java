package com.exciting.dto;

import java.time.LocalDateTime;

import com.exciting.entity.InquiryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InquiryDTO {

    private int inquiry_num;
    private String b_type;
    private String b_title;
    private String b_content;
    private String member_id;
    private LocalDateTime postDate;
    private int ref;
    private int lev;
    private int seq;
   

    public InquiryDTO(final InquiryEntity inquiry) {
        super();
        this.inquiry_num = inquiry.getInquiry_num();
        this.b_type = inquiry.getB_type();
        this.b_title = inquiry.getB_title();
        this.b_content = inquiry.getB_content();
        this.member_id = inquiry.getMember_id();
        this.postDate = inquiry.getPostDate();
        this.ref = inquiry.getRef();
        this.lev = inquiry.getLev();
        this.seq = inquiry.getSeq();
    }

    public static InquiryEntity toEntity(final InquiryDTO inquiry) {
        return InquiryEntity.builder()
                .inquiry_num(inquiry.getInquiry_num())
                .b_type(inquiry.getB_type())
                .b_title(inquiry.getB_title())
                .b_content(inquiry.getB_content())
                .member_id(inquiry.getMember_id())
                .postDate(inquiry.getPostDate())
                .ref(inquiry.getRef())
                .lev(inquiry.getLev())
                .seq(inquiry.getSeq())
                .build();
    }
}