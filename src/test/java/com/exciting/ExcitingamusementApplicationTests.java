//package com.exciting;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.json.simple.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.exciting.board.repository.BoardFavoriteRepasitory;
//import com.exciting.board.repository.BoardImgRepository;
//import com.exciting.board.repository.BoardReplyRepository;
//import com.exciting.board.repository.BoardRepository;
//import com.exciting.board.service.BoardServiceImpl;
//import com.exciting.customerService.repository.AnnouncementRepository;
//import com.exciting.entity.AnnouncementEntity;
//import com.exciting.entity.BoardImgEntity;
//import com.exciting.utils.ChangeJson;
//import com.exciting.dto.BoardDTO;
//import com.exciting.dto.BoardFavoriteDTO;
//import com.exciting.dto.BoardImgDTO;
//import com.exciting.dto.BoardReplyDTO;
//import com.exciting.entity.AnnouncementEntity;
//import com.exciting.entity.BoardEntity;
//import com.exciting.entity.BoardFavoriteEntity;
//import com.exciting.entity.BoardImgEntity;
//import com.exciting.entity.BoardReplyEntity;
//import com.exciting.utils.ChangeJson;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import lombok.extern.slf4j.Slf4j;
//
//@SpringBootTest
//@Slf4j
//class ExcitingamusementApplicationTests {
//	@Autowired
//	private BoardRepository boardRepository;
//	@Autowired
//	private BoardServiceImpl boardService;
//	@Autowired
//	private BoardReplyRepository boardReplyRepository; 
//	@Autowired
//	private BoardFavoriteRepasitory boardFavoriteRepasitory;
//	@Autowired
//	private AnnouncementRepository announcementRepository;
//	@Autowired
//	private BoardImgRepository boardImgRepository;
//	
////	@Test
////	void contextLoads() {
////		BoardDTO boardDTO = new BoardDTO();
////		boardDTO.setViewCnt(10);
////		List<BoardEntity> boardAll = boardRepository.findAll();
////		System.out.println(boardAll);
////	}
////	
////	@Test
////	void countTEST() {
////		BoardReplyDTO boardReplyDTO = new BoardReplyDTO();
////		//boardReplyDTO.setViewCnt(10);
////		BoardDTO boardDTO = new BoardDTO();
////		BoardEntity boardEntity = new BoardEntity();
////		Optional<BoardEntity> boardReplyCount = boardRepository.findByBoardId(1519);
////		System.out.println(boardReplyCount);
////		
////		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
////		
////		 String jsonStr;
////		try {
////			boardEntity = boardReplyCount.isPresent() ? boardReplyCount.get() : null;
////	        jsonStr = mapper.writeValueAsString(boardEntity);
////	        System.out.println(jsonStr);
////
////	        
////	        JSONParser jsonParser = new JSONParser();
////			try {
////				JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonStr);
////				System.out.println(jsonObj);
////			} catch (ParseException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		} catch (JsonProcessingException e) {
////		    e.printStackTrace();
////		}
////	}
////	@Test
////	void countTEST() {
////		BoardFavoriteDTO dto = new BoardFavoriteDTO();
////		//dto.setBoard_id();
////		BoardFavoriteDTO favorite = boardFavoriteRepasitory.findByBoardId(1512);
////		System.out.println(favorite);
////		
////		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
////		
////		 String jsonStr;
////		try {
////			boardEntity = boardReplyCount.isPresent() ? boardReplyCount.get() : null;
////	        jsonStr = mapper.writeValueAsString(boardEntity);
////	        System.out.println(jsonStr);
////
////	        
////	        JSONParser jsonParser = new JSONParser();
////			try {
////				JSONObject jsonObj = (JSONObject) jsonParser.parse(jsonStr);
////				System.out.println(jsonObj);
////			} catch (ParseException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////		} catch (JsonProcessingException e) {
////		    e.printStackTrace();
////		}
////	}
//	
//	//favoriteTest
//	@Test
//	public void Favorite() {
//		
//		AnnouncementEntity entity = new AnnouncementEntity();
//		entity.setAnnouncement_num(73);
//		List<BoardImgEntity> boardImgList = null;
//		Optional<AnnouncementEntity> data = null;
//		
//		
//		
//		Map<String,Object> BoardImgMap = new HashMap<>();
//		List<Map<String,Object>> returnData = new ArrayList<>();
//		
//		int announcement_num = entity.getAnnouncement_num();
//		try {
//			boardImgList = boardImgRepository.findByAnnouncement(announcement_num);
//			data = announcementRepository.findById(announcement_num);
//			
//			JSONObject dataJson = ChangeJson.ToChangeJson(data.get()); 
//			List<JSONObject> boardImgJson = boardImgList.stream().map(img -> ChangeJson.ToChangeJson(img)).collect(Collectors.toList());
//	
//			BoardImgMap.put("boardImg", boardImgJson);
//			
//			returnData.add(dataJson);
//			returnData.add(BoardImgMap);
//			System.out.println(returnData);
//			System.out.println(returnData.get(1).get("boardImg"));
//			if(data.isPresent()) {
//				
//			}else {
//				throw new RuntimeException("getAnnouncementOne is Empty");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException("getAnnouncementOne Error");
//		}
//		
//	}
//
//}
