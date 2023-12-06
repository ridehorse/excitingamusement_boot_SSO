package com.exciting.mypage.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exciting.login.dto.Criteria;
import com.exciting.login.dto.MemberDTO;
import com.exciting.login.dto.MypageDTO;
import com.exciting.login.dto.PagingDTO;
import com.exciting.login.entity.Member;
import com.exciting.login.security.TokenProvider;
import com.exciting.login.service.LoginService;
import com.exciting.mypage.service.MypageService;
import com.exciting.utils.ChangeTEXT;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MypageService mypageService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private TokenProvider tokenProvider;
	
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	//	회원수정 페이지에서 사용자가 입력한값 DB에 업데이트
	@PostMapping("/editMember")
	public int editMember(@RequestBody MemberDTO memberDTO){
		
		System.out.println("/editMember / memberDTO : "+ memberDTO);
		

		int result = mypageService.updateMember(memberDTO);

		return result;

	}

	//	member_id와 일치하는 회원정보 모두 DTO로 반환
	@PostMapping("/getMember")
	public ResponseEntity<?> getMember(@AuthenticationPrincipal String member_id){
		System.out.println("getMember 진입 / member_id  : "+ member_id);
		
		MemberDTO memberDTO = MemberDTO.builder().member_id(member_id).build();

		MemberDTO responseDTO = loginService.getByMember_id(memberDTO);

		return ResponseEntity.ok().body(responseDTO);

	}

	//	kakao_id와 일치하는 회원정보 반환
	@PostMapping("/getMemberByKakaoId")
	public ResponseEntity<?> getMemberByKakaoId(@RequestBody MemberDTO memberDTO){
		System.out.println("getMemberByKakaoId 진입 / memberDTO : "+ memberDTO);
		
		MemberDTO responseDTO = loginService.getByKakao_id(memberDTO);

		return ResponseEntity.ok().body(responseDTO);

	}

	// member_id와 일치하는 게시판/댓글 반환
	@PostMapping("/getList")
	public void getList(@AuthenticationPrincipal String member_id){
		System.out.println("getList 진입 / member_id : "+ member_id);
		
		MemberDTO memberDTO = MemberDTO.builder().member_id(member_id).build();
		
		System.out.println("member_id : "+memberDTO);
	}


	@PostMapping("/upload")
	public ResponseEntity<?> write(@RequestParam("file") MultipartFile file,@AuthenticationPrincipal String member_id) throws IOException {


		System.out.println("/upload 진입");
		String projectPath = System.getProperty("user.dir") +"\\src\\img\\profileImg";


		String path = "D:\\develop_ws\\react_WS\\excitingamusement_react\\excitingamusement_final\\src\\login\\img\\profileImg";



		System.out.println("upload / member_id : "+member_id);

		UUID uuid = UUID.randomUUID();

		//file이름 생성(uuid + 원래파일이름)
		String fileName = uuid + "_" + file.getOriginalFilename();

		System.out.println("fileName : "+fileName);

		//비어있는 파일 객체 생성(저장할 경로, 만들 이름)
		File saveFile = new File(path,fileName);

		//	      전송된 file객체(실제파일)를 방금 만든 비어있는 파일객체공간에 넣음
		file.transferTo(saveFile);

		String fullPath = path+"/"+fileName;

		Member memberEntity = Member.builder()
				.member_id(member_id)
				.m_image(fileName).build();

		//업데이트한 행의 갯수 반환
		String result =  Integer.toString(loginService.updateM_image(memberEntity));

		Map<String,String> map = new HashMap<>();
		map.put("fileName", fileName);
		map.put("result", result);

		return ResponseEntity.ok().body(map);

	}


	
	//	내 게시물 보기 링크 누르면 오는곳(react용)
	@GetMapping(value = "/writeList/{id}")
	public ResponseEntity<?> writeListGet(@PathVariable("id") int id,@AuthenticationPrincipal String member_id,@RequestParam("sort") String sort,Criteria cri,HttpServletRequest request) {
		System.out.println("writeList.get() 접근(react)");
		System.out.println("writeList()/request : "+request.getSession() );
		System.out.println("writeList()/criteria : "+cri.getMember_id() );
		System.out.println("writeList()/id : "+id );
		System.out.println("writeList()/member_id : "+member_id );
		System.out.println("writeList()/sort : "+sort );

		int limitNum;

		//		현재페이지(pageNum), 한페이지에 포현하는 개시물수(amount) 설정

		limitNum = (id-1)*10+1;
		cri.setLimitNum(limitNum);
		cri.setPageNum(id);

		cri.setAmount(10);

		//		react에서 전달된 member_id 변수 저장
		cri.setMember_id(member_id);

		//		member_id에 따른 총 게시물수(total) 계산 (모든 table 합해서 계산)
		int total = mypageService.countByMember_id(cri);
		System.out.println("countUnionAll/total : "+total);

		//		페이징에 필요한 값들을 받아온다
		PagingDTO d = new PagingDTO(cri,total);

		//		cri 객체 안에 있는 pageNum값 pagingDTO로 꺼냄(cri객체 안에있으면 리엑트 단에서 호출 못함)
		d.setPageNum(d.getCri().getPageNum());


		System.out.println("/writeList.get()/limitNum : " + cri.getLimitNum());
		//		(null:DESC(처음진입),1:DESC,2:ASC)pageNum, amount 에 따른 한화면에 표시할 게시물들을 가져온다.(모든 table 합해서 가져온다)
		List<Object[]> list;
		if(sort.equals("DESC")) {
			System.out.println("/writeList.get()/sort==DESC 진입");

			list = mypageService.selectDescByMember_id(cri);

		}else if(sort.equals("ASC")) {
			System.out.println("/writeList.get()/sort==ASC 진입");

			list = mypageService.selectAscByMember_id(cri);

		}else {
			System.out.println("/writeList.get()/sort==null 진입");
			list = mypageService.selectDescByMember_id(cri);
		}


		List<MypageDTO> mypageList = new ArrayList<>();

		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] object = iterator.next();

			System.out.println(object.length);
			System.out.println(object[0]); // member_id
			System.out.println(object[1]); // view_id
			System.out.println(object[2]); // b_title
			System.out.println(object[3]); // b_content
			System.out.println(object[4]); // postdate
			System.out.println(object[5]); // category

			MypageDTO mypageDTO = new MypageDTO();
			
			ChangeTEXT changeText = new ChangeTEXT();

			mypageDTO.setMember_id((String)object[0]);
			mypageDTO.setView_id((int)object[1]);
			mypageDTO.setB_title((String)object[2]);
			mypageDTO.setB_content(changeText.ToTextarea((String)object[3]));
			mypageDTO.setPostdate((Date)object[4]);
			mypageDTO.setCategory((String)object[5]);

			mypageList.add(mypageDTO);

		}

		System.out.println("list :"+mypageList);

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("list", mypageList);
		responseMap.put("total", total);
		responseMap.put("pageMaker", d);

		return ResponseEntity.ok().body(responseMap);


	}

	//	writeList.jsp 에서 과거순 목록 클릭시 오는곳(react)	
	@GetMapping(value = "/pastPost/{id}")
	public ResponseEntity<?> pastPost(@PathVariable("id") int id,@AuthenticationPrincipal String member_id,Criteria cri,HttpServletRequest request,HttpServletResponse response){
		System.out.println("/pastPost 진입!!");

		int limitNum;

		//		현재페이지(pageNum), 한페이지에 포현하는 개시물수(amount) 설정
		limitNum = (id-1)*10+1;
		cri.setLimitNum(limitNum);
		cri.setPageNum(id);

		cri.setAmount(10);



		//		member_id에 따른 총 게시물수(total) 계산 (모든 table 합해서 계산)
		int total = mypageService.countByMember_id(cri);
		System.out.println("countUnionAll/total : "+total);

		//		페이징에 필요한 값들을 받아온다
		PagingDTO pageMaker = new PagingDTO(cri,total);
		System.out.println("pageMaker :"+pageMaker);

		System.out.println("/writeList.get()/limitNum : " + cri.getLimitNum());
		//		pageNum, amount 에 따른 한화면에 표시할 게시물들을 가져온다.(모든 table 합해서 가져온다)
		List<Object[]> list = mypageService.selectAscByMember_id(cri);

		List<MypageDTO> mypageList = new ArrayList<>();

		Iterator<Object[]> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] object = iterator.next();

			System.out.println(object.length);
			System.out.println(object[0]); // member_id
			System.out.println(object[1]); // view_id
			System.out.println(object[2]); // b_title
			System.out.println(object[3]); // b_content
			System.out.println(object[4]); // postdate
			System.out.println(object[5]); // category

			MypageDTO mypageDTO = new MypageDTO();

			mypageDTO.setMember_id((String)object[0]);
			mypageDTO.setView_id((int)object[1]);
			mypageDTO.setB_title((String)object[2]);
			mypageDTO.setB_content((String)object[3]);
			mypageDTO.setPostdate((Date)object[4]);
			mypageDTO.setCategory((String)object[5]);

			mypageList.add(mypageDTO);

		}

		System.out.println("list :"+mypageList);


		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("list", mypageList);
		responseMap.put("total", total);
		responseMap.put("pageMaker", pageMaker);

		return ResponseEntity.ok().body(responseMap);

	}
	
	@PostMapping("/byebye")
	public int deleteMember(@AuthenticationPrincipal String member_id) {
		
		return loginService.deleteMember(member_id);
	}



}
