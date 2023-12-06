package com.exciting.dto;

import java.time.LocalDateTime;

import com.exciting.dto.AnnouncementDTO;
import com.exciting.entity.AnnouncementEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnnouncementDTO {
    private int announcement_num;
    private String c_type;
    private String c_title;
    private String c_content;
    private LocalDateTime postdate;

    public AnnouncementDTO(final AnnouncementEntity announcement) {
        super();
        this.announcement_num = announcement.getAnnouncement_num();
        this.c_type = announcement.getC_type();
        this.c_title = announcement.getC_title();
        this.c_content = announcement.getC_content();
        this.postdate = announcement.getPostdate();
    }

    public static AnnouncementEntity toEntity(final AnnouncementDTO dto) {
        return AnnouncementEntity.builder()
            .announcement_num(dto.getAnnouncement_num())
            .c_type(dto.getC_type())
            .c_title(dto.getC_title())
            .c_content(dto.getC_content())
            .postdate(dto.getPostdate())
            .build();
    }

}