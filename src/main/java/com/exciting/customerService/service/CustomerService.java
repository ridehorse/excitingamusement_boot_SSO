package com.exciting.customerService.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.exciting.dto.AnnouncementDTO;
import com.exciting.dto.FaqDTO;
import com.exciting.dto.InquiryDTO;
import com.exciting.entity.AnnouncementEntity;
import com.exciting.entity.BoardEntity;
import com.exciting.entity.BoardImgEntity;
import com.exciting.entity.FaqEntity;
import com.exciting.entity.InquiryEntity;

	

public interface CustomerService{
	Page<AnnouncementDTO> getAnnouncementList(final AnnouncementEntity entity,int pageNum,String Search );
	void customerImgInsert(final BoardImgEntity boardImgEntity);
	int insertAnnouncement(final AnnouncementEntity entity);
	AnnouncementEntity getAnnouncementOne(final AnnouncementEntity entity);
	List<BoardImgEntity> getAnnouncementImg(final AnnouncementEntity entity);
	List<BoardImgEntity> customerImgDelete(final BoardImgEntity entity);
	void updateAnnounment(final AnnouncementEntity entity);
	void deleteAnnouncement(final AnnouncementEntity entity);
	int insertInquiry(final InquiryEntity entity);
	Page<InquiryDTO> getInquieyList(final InquiryEntity entity, int pageNum, String search, String type);
	List<Optional<InquiryEntity>> getInquiryDetail(final InquiryEntity entity);
	List<BoardImgEntity> selectInquiryImg(final InquiryDTO dto);
	void InquieyAnswer(final InquiryEntity entity);
	void deleteInquiry(final InquiryEntity entity);
	Page<FaqDTO> getFaqList(final FaqEntity entity,int pageNum);
}
