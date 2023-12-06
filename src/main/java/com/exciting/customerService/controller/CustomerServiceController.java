package com.exciting.customerService.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exciting.customerService.service.CustomerService;
import com.exciting.dto.AnnouncementDTO;
import com.exciting.dto.BoardImgDTO;
import com.exciting.dto.FaqDTO;
import com.exciting.dto.InquiryDTO;
import com.exciting.dto.ResponseDTO;
import com.exciting.entity.AnnouncementEntity;
import com.exciting.entity.BoardImgEntity;
import com.exciting.entity.FaqEntity;
import com.exciting.entity.InquiryEntity;
import com.exciting.login.dto.MemberDTO;
import com.exciting.login.entity.Member;
import com.exciting.utils.ChangeJson;
import com.exciting.utils.FileUtils;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/customer")
public class CustomerServiceController {
	
	
	private CustomerService service;
	public CustomerServiceController(CustomerService service) {
		this.service = service;
	}
	

	private static final String BOARD_UPLOAD_PATH;
	
	
	static {
        
 
		try {
			String dirPath = "C:\\static\\uploads";;
		    BOARD_UPLOAD_PATH = dirPath;
		    FileUtils.createDirectory(BOARD_UPLOAD_PATH);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to get the upload path", e);
		}

    }
	
	

	@GetMapping("/announcement")
	@ResponseBody
	public Page<AnnouncementDTO> Announcement(AnnouncementDTO announcementDTO, @RequestParam Map<String, Object> map) {

		AnnouncementEntity entity = AnnouncementDTO.toEntity(announcementDTO);
		int pageNum = Integer.parseInt(String.valueOf(map.get("pageNum")));
		String Search = String.valueOf(map.get("search"));

		Page<AnnouncementDTO> announcementList = service.getAnnouncementList(entity, pageNum, Search);

		return announcementList;

		// 텍스트 처리

	}

	/*
	 * 
	 * 이미지 Image 업로드 Upload Start
	 * 
	 */

	@PostMapping("/imageUpload")
	@ResponseBody
	public void imageUpload(@RequestParam(value = "file", required = false) List<MultipartFile> mf,
			@RequestParam(value="announcement_num", required=false) Integer announcement_num,
		    @RequestParam(value="inquiry_num", required=false) Integer inquiry_num) {
		
		try {
			if (mf != null) {
				String firstFileName = mf.get(0).getOriginalFilename();
				boolean isFileNotEmpty = firstFileName != null && !firstFileName.equals("");
				if (isFileNotEmpty) {

					for (MultipartFile file : mf) {
						BoardImgEntity boardImgEntity = new BoardImgEntity();

						String originalFileName = System.currentTimeMillis() + file.getOriginalFilename();

						String uploadDir = BOARD_UPLOAD_PATH;
						String safeFile = uploadDir + "/" + originalFileName;

						//받은값이 있을시 넣음

						if(announcement_num != null && announcement_num != 0)
							boardImgEntity.setAnnouncement_num(announcement_num);
						else if(inquiry_num != null && inquiry_num != 0)
							boardImgEntity.setInquiry_num(inquiry_num);

						//파일 이름저장
						boardImgEntity.setBoardimg(originalFileName);

						//DB에 저장
						service.customerImgInsert(boardImgEntity);

						//폴더에 저장
						file.transferTo(new File(safeFile));
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("BoardImgInsert Error");
		}

	}

	/*
	 * 
	 * 이미지 Image 업로드 Upload End
	 * 
	 */
	

	/*
	 * 
	 * 이미지 삭제 Image Delete Start
	 * 
	 */
	
	@DeleteMapping("/deleteBoardImg")
	@ResponseBody
	public void deleteBoardImg(@RequestParam(value = "announcement_num", required = false) Integer announcement_num,
			@RequestParam(value = "inquiry_num", required = false) Integer inquiry_num,
			@RequestParam(value="boardimg_num", required=false) Integer boardimg_num) {

		BoardImgEntity boardImgEntity = new BoardImgEntity();
		if (announcement_num != null) 
			boardImgEntity.setBoardimg_num(announcement_num);
		else if (inquiry_num != null)
			boardImgEntity.setInquiry_num(inquiry_num);
		else if(boardimg_num!= null)
			boardImgEntity.setBoardimg_num(boardimg_num);

		// 기존 이미지 데이터를 조회 및 DB에 저장된 이미지 정보 삭제
		List<BoardImgEntity> OriginData = service.customerImgDelete(boardImgEntity);

		// dto변환
		List<BoardImgDTO> OriginDataDTO = OriginData.stream().map(BoardImgDTO::new).collect(Collectors.toList());
		for (BoardImgDTO rs : OriginDataDTO) {

			// String uploadDir = BOARD_UPLOAD_PATH;
			String uploadDir = new File(BOARD_UPLOAD_PATH).getAbsolutePath();
			File file = new File(uploadDir, rs.getBoardimg());
			// String safeFile = uploadDir+"/"+originalFileName;
			if (file.exists()) { // 파일이 존재하면
				file.delete(); // 파일 삭제
			}
		}

		// int rs = service.deleteBoardImg(map);

	}
	
	/*
	 * 
	 * 이미지 삭제 Image Delete End
	 * 
	 */
	

	
	/*
	 * 
	 * 공지사항 글쓰기 announcement Write Start
	 * 
	 */
	
	//공지사항 글쓰기
	@PostMapping("/insertAnnouncement")
	@ResponseBody
	public int announcementPost(@RequestBody AnnouncementDTO announcementDTO) {
		
		AnnouncementEntity entity = AnnouncementDTO.toEntity(announcementDTO);
		
		
		
		entity.setC_type("공지");
		entity.setPostdate(LocalDateTime.now());
		
		int announcement_num =service.insertAnnouncement(entity);
		
		
		return announcement_num;
	}
	
	/*
	 * 
	 * 공지사항 글쓰기 announcement Write END
	 * 
	 */
	
	
	
	/*
	 * 
	 * 공지사항 세부 announcement Detail update Start

	 * 
	 */
	@GetMapping(value ={"/view","/updateAnnouncement"})
	@ResponseBody
	public ResponseEntity<?> announcementView(AnnouncementDTO announcementDTO) {
		
		
		try {
			
			AnnouncementEntity entity = AnnouncementDTO.toEntity(announcementDTO);
			AnnouncementEntity announcementData = service.getAnnouncementOne(entity);
			
			
			List<BoardImgEntity> boardImgList = service.getAnnouncementImg(entity);

			//DTO변환
			List<BoardImgDTO> boardImgDTO = boardImgList.stream()
				    .filter(Objects::nonNull) // "null" 값인 요소들을 제외하고 필터링
				    .map(BoardImgDTO::new)
				    .collect(Collectors.toList());
			
			//이미지 경로 주입
			int num =0;
			String boardImg =null;
			for(BoardImgDTO i : boardImgDTO) {
				boardImg = i.getBoardimg();
				boardImg = "/uploads/"+ boardImg;
				i.setBoardimg(boardImg);
				num++;
			}

			JSONObject jsonData = ChangeJson.ToChangeJson(announcementData);
			
			jsonData.put("boardImg", boardImgDTO);

			ResponseDTO<JSONObject> response = ResponseDTO.<JSONObject>builder().json(jsonData).build();
			
			return ResponseEntity.ok().body(response.getJson());
			
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<JSONObject> response = ResponseDTO.<JSONObject>builder().error(error).build();
			return ResponseEntity.badRequest().body(response.getError());
		}
		
	}
	/*
	 * 

	 * 공지사항 세부 announcement Detail update END
	 * 
	 */

	/*
	 * 
	 * 공지사항 세부 announcement Detail update END
	 * 
	 */
	
	@PutMapping("/updateAnnouncement")
	public void updateBoardpost(@RequestBody AnnouncementDTO dto){

		AnnouncementEntity announcementEntity = AnnouncementDTO.toEntity(dto);
		
		service.updateAnnounment(announcementEntity);
		
	}

	/*
	 * 
	 * 공지사항 세부 announcement Detail update END
	 * 
	 */
	
	/*
	 * 
	 * 공지사항 세부 삭제 announcementDetail Delete Start
	 * 
	 */
	
	
/*
 * 
 * 공지사항 세부 삭제 announcementDetail Delete END
 * 
 */
	
	@DeleteMapping("deleteAnnouncement")
	public String  deleteAnnouncement(AnnouncementDTO dto) {
		
		AnnouncementEntity announcementEntity = AnnouncementDTO.toEntity(dto);
	
		service.deleteAnnouncement(announcementEntity);
		
		return "forward:/deleteBoardImg";
		
	}


	
	@GetMapping("/getfaqList")
	@ResponseBody
	public Page<FaqDTO> getfaqList(FaqDTO dto,int pageNum) {
		
		FaqEntity faqEntity = FaqDTO.toEntity(dto);
		Page<FaqDTO> faqList = service.getFaqList(faqEntity,pageNum);
		
		return faqList;
	}
	

	@PostMapping("/insertInquiry")
	@ResponseBody
	public int announcementInquiryPOST(@RequestBody InquiryDTO dto) {
		
		InquiryEntity entity = InquiryDTO.toEntity(dto);
		int inquiry_num = service.insertInquiry(entity);
				
		return inquiry_num;
	}
	
	@GetMapping("/inquiryList")
	@ResponseBody
	public Page<InquiryDTO> consultationDetailsGET(InquiryDTO dto,String type,String search,int pageNum) {
		
		if(search == null)
			search= "";
		InquiryEntity entity = InquiryDTO.toEntity(dto);
		
		Page<InquiryDTO> InquiryList = service.getInquieyList(entity,pageNum,search,type);
		

		return InquiryList;
	
	}
	
	
	@GetMapping("/inquiryView")
	public ResponseEntity<?>  consultationViewGET(InquiryDTO dto) {
		try {
			InquiryEntity entity = InquiryDTO.toEntity(dto);
			
			List<Optional<InquiryEntity>> inquiryDetail = service.getInquiryDetail(entity);
			
			List<InquiryEntity> inquiryDetailDataOP = inquiryDetail.stream().map((detail) -> detail.get()).collect(Collectors.toList());
			List<InquiryDTO> inquiryDetailData = inquiryDetailDataOP.stream().map(InquiryDTO::new).collect(Collectors.toList());
			
			ResponseDTO<InquiryDTO> response = ResponseDTO.<InquiryDTO>builder().data(inquiryDetailData).build();
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseDTO<String> response = ResponseDTO.<String>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	
	
	@GetMapping("/inquiryImage")
	public ResponseEntity<?> inquiryImage(InquiryDTO dto){
		
		try {
			List<BoardImgEntity> boardImg = service.selectInquiryImg(dto);
			List<BoardImgDTO> boardImgList = boardImg.stream().map(BoardImgDTO::new).collect(Collectors.toList());
			
			for(BoardImgDTO b :boardImgList) {
				 b.setBoardimg("/uploads/"+b.getBoardimg());
				
			}	
			
			ResponseDTO<BoardImgDTO> response  = ResponseDTO.<BoardImgDTO>builder().data(boardImgList).build();
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			String  error = e.getMessage();
			ResponseDTO<BoardImgDTO> response  = ResponseDTO.<BoardImgDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	
	@PostMapping("/inquiryAnswer")
	public void consultationViewPOST(@RequestBody InquiryDTO dto) {
		InquiryEntity entity = InquiryDTO.toEntity(dto);
		
		service.InquieyAnswer(entity);		
		
	}
	
	@DeleteMapping("/deleteInquiry")
	public void  deleteInquiry(InquiryDTO dto) {
		int inquiry_num = dto.getInquiry_num();
		InquiryEntity entity = InquiryDTO.toEntity(dto);
		service.deleteInquiry(entity);
		
	}
	

}
