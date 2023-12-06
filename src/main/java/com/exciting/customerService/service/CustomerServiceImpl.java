package com.exciting.customerService.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.exciting.board.repository.BoardImgRepository;
import com.exciting.board.repository.JpaSpecification;
import com.exciting.customerService.repository.AnnouncementRepository;
import com.exciting.customerService.repository.FaqRepository;
import com.exciting.customerService.repository.InquiryRepository;
import com.exciting.dto.AnnouncementDTO;
import com.exciting.dto.FaqDTO;
import com.exciting.dto.InquiryDTO;
import com.exciting.entity.AnnouncementEntity;
import com.exciting.entity.BoardEntity;
import com.exciting.entity.BoardImgEntity;
import com.exciting.entity.FaqEntity;
import com.exciting.entity.InquiryEntity;
import com.exciting.utils.ChangeJson;
import com.exciting.utils.ChangeTEXT;

import lombok.Builder;

@Service
public class CustomerServiceImpl implements CustomerService {

	 private AnnouncementRepository announcementRepository;
	 private BoardImgRepository boardImgRepository;
	 private InquiryRepository inquiryRepository;
	 private FaqRepository faqRepository;

	  public CustomerServiceImpl(AnnouncementRepository announcementRepository, BoardImgRepository boardImgRepository
			  , InquiryRepository inquiryRepository, FaqRepository faqRepository) {
	    this.announcementRepository = announcementRepository;
	    this.boardImgRepository = boardImgRepository;
	    this.inquiryRepository = inquiryRepository;
	    this.faqRepository=faqRepository;	  }

	/*
	 * 
	 * 공지사항리스트 AnnouncementList 출력 show Start
	 * 
	 * */
	public Page<AnnouncementDTO> getAnnouncementList(final AnnouncementEntity entity,int pageNum,String search) {
		Sort sort = Sort.by("postdate").descending();
		
		String type ="c_title";
		PageRequest pageRequest = getPageRequestLogic(sort, 10, pageNum);
		Specification<AnnouncementEntity> spec = (Specification<AnnouncementEntity>) JpaSpecification.LikeSearch(search,type);
		
		Page<AnnouncementEntity> announcementList = announcementRepository.findAll(spec,pageRequest); 
		List<AnnouncementDTO> announcementDTOList = announcementList.getContent().stream().map(AnnouncementDTO::new)
				.collect(Collectors.toList());		
		
		//Pageable pageable = PageRequest.of(pageNum, 10,sort);
		
		Page<AnnouncementDTO> pageList = new PageImpl<>(announcementDTOList, pageRequest, announcementList.getTotalElements());
		
		return pageList;
	}
	
	/*
	 * 
	 * 공지사항리스트 AnnouncementList 출력 show END
	 * 
	 * */
	
	/*
	 * 
	 * 공지사항이미지저장 AnnouncementImage announcementimg insert Start
	 * 
	 * */
	
	public void customerImgInsert(final BoardImgEntity boardImgEntity) {
		
		try {
			Integer announcement_num = boardImgEntity.getAnnouncement_num();
			Integer inquiry_num = boardImgEntity.getInquiry_num();
			
			if(announcement_num != null && announcement_num!= 0 )
				boardImgRepository.save(boardImgEntity);
			else if(inquiry_num != null && inquiry_num!= 0 )
				boardImgRepository.save(boardImgEntity);
				
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("customerImgInsert Service Error");
		}
		
	}
	
	/*
	 * 
	 * 공지사항이미지저장 AnnouncementImage announcementimg insert END
	 * 
	 * */
	
	/*
	 * 
	 * 공지사항글쓰기 Announcement Write insert Start
	 * 
	 * */
	public int insertAnnouncement(final AnnouncementEntity entity) {
		
		AnnouncementEntity insertEntity = announcementRepository.save(entity);
		
		int announcement_num = insertEntity.getAnnouncement_num();
		
		return announcement_num;
	}

	/*
	 * 
	 * 공지사항글쓰기 Announcement Write insert END
	 * 
	 * */
	
	/*
	 * 
	 * 공지사항 세부 announcement Detail Start
	 * 
	 */
	
	public AnnouncementEntity getAnnouncementOne(final AnnouncementEntity entity) {
	
		
		Optional<AnnouncementEntity> data = null;
			
		int announcement_num = entity.getAnnouncement_num();
		try {
			data = announcementRepository.findByAnnouncementNum(announcement_num);
			if(data.isPresent()) {
				return data.get();
			}else {
				throw new RuntimeException("getAnnouncementOne is Empty");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getAnnouncementOne Error");
		}
		
		
		
	}
	
	/*
	 * 
	 * 공지사항 세부 announcement Detail END
	 * 
	 */
	
	/*
	 * 
	 * 공지사항 이미지불러오기 announcement Image img GET Start
	 * 
	 */
	
	public List<BoardImgEntity> getAnnouncementImg(final AnnouncementEntity entity){
		
		List<BoardImgEntity> boardImgList = null;
		int announcement_num = entity.getAnnouncement_num();
		try {
			
			boardImgList = boardImgRepository.findByAnnouncement(announcement_num);
			
			return boardImgList;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getAnnouncementOne Error");
		}
	}
	
	/*
	 * 
	 * 공지사항 이미지불러오기 announcement Image img GET END
	 * 
	 */
	
	
	/*
	 * 
	 * 공지사항 이미지지우기 announcement inquiry Image img Delete Start
	 * 
	 */
	public List<BoardImgEntity> customerImgDelete(final BoardImgEntity boardImgEntity){
		
		//이미지파일 삭제를 위한 깊은복사리스트
		List<BoardImgEntity> deleteImgData = new ArrayList<>();

		try {

			// 삭제전 정보 가져오기

			//게시글 삭제일떄
			if (boardImgEntity.getBoardimg_num() != null) {
				
				// 기존에 있던 이미지 삭제
				Optional<BoardImgEntity> deleteImgDataOne = boardImgRepository
						.findById(boardImgEntity.getBoardimg_num());
				
				BoardImgEntity entity = new BoardImgEntity();
				
				entity.setBoardimg(deleteImgDataOne.get().getBoardimg());
				
				deleteImgData.add(entity);
				
			} else if (boardImgEntity.getAnnouncement_num() != null) {
				//Announcement 삭제로 인한 이미지 삭제
				//게시글 전체 이미지 불러오기
				List<BoardImgEntity> deleteImgDataList = boardImgRepository.findByAnnouncement(boardImgEntity.getAnnouncement_num());

				deleteImgData = deleteImgDataList;
			}else if ( boardImgEntity.getInquiry_num() != null) {
				//inquiry 삭제로 인한 이미지 삭제
				//게시글 전체 이미지 불러오기
				Integer inquiry_num =  boardImgEntity.getInquiry_num();
				InquiryDTO dto = new InquiryDTO();
				dto.setInquiry_num(inquiry_num);

				List<BoardImgEntity> deleteImgDataList = boardImgRepository.boardInquiryNum(dto.getInquiry_num());
				deleteImgData = deleteImgDataList;
			}

			// 이미지 DB에서 삭제
			if (deleteImgData != null) {
				boardImgRepository.delete(boardImgEntity);
			} else {
				throw new RuntimeException("BoardImgDelete Service NotFound");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardImgDelete Service Error");
		}
		// 저장해뒀던 기존 데이타 반환
		return deleteImgData;


	}
	
	/*
	 * 
	 * 공지사항 이미지지우기 announcement inquiry Image Delete GET END
	 * 
	 */
	
	/*
	 * 
	 * 공지사항수정 updateAnnouncement Start
	 * 
	 */
	
	public void updateAnnounment(final AnnouncementEntity entity) {
		try {
			
			int announcement_num = entity.getAnnouncement_num();
			//수정을위한 기존글 로딩
			Optional<AnnouncementEntity> OriginData = announcementRepository.findById(announcement_num);
			
			if(OriginData.isPresent()) {
				
				OriginData.get().setC_content(entity.getC_content());
				OriginData.get().setC_title(entity.getC_title());
				OriginData.get().setPostdate(LocalDateTime.now());
				
				announcementRepository.save(OriginData.get());
				
			}else {
				throw new RuntimeException("updateAnnounment Service NotFoundData");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("updateAnnounment Service Error");
		}
	}

	
	
	/*
	 * 
	 * 공지사항수정 updateAnnouncement End
	 * 
	 */
	
	
	/*
	 * 
	 * 공지사항삭제 DeleteAnnouncement Start
	 * 
	 */
	
	@Override
	public void deleteAnnouncement(final AnnouncementEntity entity) {
		
		try {
			
			announcementRepository.delete(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("deleteAnnouncement Service Error");
		}
	}

	
	
	/*
	 * 
	 * 공지사항리스트 AnnouncementList 출력 show END
	 * 
	 * */
	
	/*
	 * 
	 * 문의상담글쓰기 insertInquiry Start
	 * 
	 * */
	
	@Override
	@Transactional
	public int insertInquiry(final InquiryEntity entity) {
		try {
			
			entity.setB_title("(답변 대기중)"+entity.getB_title());
			entity.setPostDate(LocalDateTime.now());
			InquiryEntity insertData =  inquiryRepository.save(entity);
			
			int inquiry_num = insertData.getInquiry_num();
			
			//ref 업데이트를 위한 작업
			insertData.setRef(inquiry_num);
			
			inquiryRepository.save(insertData);
			
			
			return inquiry_num;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("insertInquiry Service Error");
		}
		
	}
	
	/*
	 * 
	 * 문의상담글쓰기 insertInquiry END
	 * 
	 * */
	
	/*
	 * 
	 * 문의상담리스트 getInquieyList Start
	 * 
	 * */
	
	@Override
	public Page<InquiryDTO> getInquieyList(final InquiryEntity entity,int pageNum,String search,String type){
		try {
			
			
			Sort sort = Sort.by("postDate").descending();
			
			Specification<InquiryEntity> spec =  (Specification<InquiryEntity>) JpaSpecification.LikeSearch(search,type);
			PageRequest pageRequest = getPageRequestLogic(sort,10, pageNum);
			
			Page<InquiryEntity> inquriyList = inquiryRepository.findAll(spec,pageRequest);
			
			List<InquiryDTO> list = inquriyList.getContent()
				    .stream()
				    .map(InquiryDTO::new)
				    .collect(Collectors.toList());			
			Page<InquiryDTO> pageList = new PageImpl<>( list , pageRequest, inquriyList.getTotalElements());
			
//			List<AnnouncementDTO> announcementDTOList = announcementList.getContent().stream().map(AnnouncementDTO::new)
//					.collect(Collectors.toList());		
//			
//			//Pageable pageable = PageRequest.of(pageNum, 10,sort);
//			
//			Page<AnnouncementDTO> pageList = new PageImpl<>(announcementDTOList, pageRequest, announcementList.getTotalElements());
//			
//			PageRequest pagerequest = PageRequest();
			
			return pageList;
					
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInquieyList Service Error");
		}
		
		
	}
	
	/*
	 * 
	 * 문의상담리스트 getInquieyList END
	 * 
	 * */
	
	
	/*
	 * 
	 * 문의상담글 getInquiryDetail Start
	 * 
	 * */
	public List<Optional<InquiryEntity>> getInquiryDetail(final InquiryEntity entity) {
		try {
			List<Optional<InquiryEntity>> inquiryDetail = inquiryRepository.findByRef(entity.getInquiry_num());
			
			if(inquiryDetail.isEmpty()) 
				throw new RuntimeException("getInquiryDetail Service inquiryDetail NotFound");
			else
				return inquiryDetail;
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("getInquiryDetail Service Error",e);
		}
		
	}
	/*
	 * 
	 * 문의상담글 getInquiryDetail END
	 * 
	 * */
	
	
	/*
	 * 
	 * 문의상담글이미지 selectInquiryImg Start
	 * 
	 * */
	
	
	//여기임
	
	public List<BoardImgEntity> selectInquiryImg(final InquiryDTO dto){
		
		try {
			
			List<BoardImgEntity> InquiryImg = boardImgRepository.findInquiryNum(dto.getInquiry_num());
			
			return InquiryImg;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("selectInquiryImg Service Error");
		}
	}
	
	/*
	 * 
	 * 문의상담글 selectInquiryImg END
	 * 
	 * */
	
	/*
	 * 
	 * 문의상담글답면 InquieyAnswer Start
	 * 
	 * */
	
	@Transactional
	public  void InquieyAnswer(final InquiryEntity entity){
		
		
		try {
			
			int originRef = entity.getRef(); 
			entity.setPostDate(LocalDateTime.now());
			entity.setInquiry_num(0);
			InquiryEntity insertData = inquiryRepository.save(entity);
			int inquiry_num = insertData.getInquiry_num();
			
			Optional<InquiryEntity> updateData = inquiryRepository.findById(inquiry_num);
			
			if(updateData.isPresent()) {
				int updateInquiry_num = updateData.get().getInquiry_num();
				updateData.get().setRef(originRef);
				updateData.get().setB_type("답변");
				updateData.get().setPostDate(LocalDateTime.now());
				inquiryRepository.save(updateData.get());
				
			}else {
				throw new RuntimeException("InquieyAnswer Service insertData NotFound");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("InquieyAnswer Service Error");
		}
		//답변 세이브
		
		
		
	}
	/*
	 * 
	 * 문의상담글답면 InquieyAnswer END
	 * 
	 * */
	
	/*
	 * 
	 * 문의상담글삭제 deleteInquiry Start
	 * 
	 * */
	
	public void deleteInquiry(final InquiryEntity entity) {
		try {
			
			inquiryRepository.delete(entity);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new RuntimeException("deleteInquiry Service Error");		
		}
		
	}
	/*
	 * 	
	 * 문의상담글삭제 deleteInquiry END
	 * 
	 * */
	
	
	/*
	 * 
	 * getFaqList Start
	 * 
	 * 
	 * */
	public Page<FaqDTO> getFaqList(final FaqEntity entity,int pageNum){
		
		Sort sort = Sort.unsorted();
		
		PageRequest pageRequest = getPageRequestLogic(sort,5,pageNum);
		
		String type = entity.getF_type();
		Specification<FaqEntity> spec = (Specification<FaqEntity>) JpaSpecification.equalString("f_type", type);
		Page<FaqEntity> faqFirstList = faqRepository.findAll(spec,pageRequest);
		List<FaqDTO> faqSecondList = faqFirstList.stream().map(FaqDTO::new).collect(Collectors.toList());
		
		for(FaqDTO f :faqSecondList ) {
			f.setContent(ChangeTEXT.ToTextarea(f.getContent()));
			f.setTitle(ChangeTEXT.ToTextarea(f.getTitle()));
		}
		
		Page<FaqDTO> faqpageList = new PageImpl<>( faqSecondList , pageRequest, faqFirstList.getTotalElements());
		
		
		return faqpageList;
	}
	
	/*
	 * 
	 * getFaqList End
	 * 
	 * 
	 * */
	
	
	
	/////////////////////////////////////////////////////////////////////////////////
	
/*
 *
 *	메소드 구간
 * 
 * */
	
	

	//pageRequest 처리
	public PageRequest getPageRequestLogic(Sort sort,int count,int pageNum) {
		
		try {
			pageNum = pageNum-1;
			PageRequest pageRequest =PageRequest.of(pageNum, count, sort);
			
			return pageRequest;
		} catch (Exception e) {
			throw new RuntimeException("getPageRequestLogic Error");
		}
		
	}
	
	
	
	


	
	
	
	


	
	
}
