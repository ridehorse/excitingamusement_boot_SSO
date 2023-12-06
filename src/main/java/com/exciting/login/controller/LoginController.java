package com.exciting.login.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exciting.dto.ResponseDTO;
import com.exciting.login.dto.BoardDTO;
import com.exciting.login.dto.BoardReplyDTO;
import com.exciting.login.dto.MemberDTO;
import com.exciting.login.entity.Board;
import com.exciting.login.entity.BoardReply;
import com.exciting.login.entity.Member;
import com.exciting.login.persistence.LoginRepository;
import com.exciting.login.security.TokenProvider;
import com.exciting.login.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO){
		System.out.println("/login/signup 진입");
		System.out.println("/login/signup : member_id : "+memberDTO.getMember_id());
		System.out.println("/login/signup : m_pass : "+memberDTO.getM_pass());
		
//		비밀번호 존재 유효성 검사
		if(memberDTO == null || memberDTO.getM_pass() == null) {
			throw new RuntimeException("invalid Password value.");
		}
		
		
		try {
//		요청을 이용해 저장할 유저 만들기
		Member user = Member.builder()
				.member_id(memberDTO.getMember_id())
				.m_pass(passwordEncoder.encode(memberDTO.getM_pass()))
				.m_name(memberDTO.getM_name())
				.m_birth(memberDTO.getM_birth())
				.m_phone(memberDTO.getM_phone())
				.m_address(memberDTO.getM_address())
				.m_email(memberDTO.getM_email())
				.m_gender(memberDTO.getM_gender())
				.m_kakao_id(memberDTO.getM_kakao_id())
				.roles("ROLE_user")
				.build();
		
		System.out.println("/login/signup : user : "+user);
		
		
		
//		git접속 회원가입이라서 github_인 아이디가 이미 존재할때
		if(memberDTO.getMember_id().substring(0, 7).equals("github_") || memberDTO.getMember_id().substring(0, 6).equals("kakao_") || memberDTO.getMember_id().substring(0, 6).equals("naver_")) {
			int result = loginService.socialMemberUpdate(user);
			return ResponseEntity.ok().body(result);
		}
		
//		서비스를 이용해 DB에 유저 저장(저장한값 UserEntity 형태로 반환)
		Member registeredUser = loginService.memberCreate(user);
		
		System.out.println("/login/signup : registeredUser : "+registeredUser);

		
//		반환된 UserEntity에서 값 꺼낸후 UserDTO에 저장
		MemberDTO responseUserDTO = MemberDTO.builder()
				.member_id(registeredUser.getMember_id())
				.m_pass(registeredUser.getM_pass())
				.m_name(registeredUser.getM_name())
				.m_birth(registeredUser.getM_birth())
				.m_phone(registeredUser.getM_phone())
				.m_address(registeredUser.getM_address())
				.m_email(registeredUser.getM_email())
				.m_gender(registeredUser.getM_gender())
				.m_regidate(registeredUser.getM_regidate())
				.build();
		
		return ResponseEntity.ok().body(responseUserDTO);
		}catch (Exception e) {
			ResponseDTO<?> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody MemberDTO memberDTO){
		System.out.println("/login/signin 진입");
		
//		비밀번호가 다르면 null 반환, 같으면 memberEntity 반환
		Member member = loginService.getByCredentials(memberDTO.getMember_id(), memberDTO.getM_pass(), passwordEncoder);
		
		
		
		if(member != null) {
//			create : 토큰 생성
			final String token = tokenProvider.create(member);
			final MemberDTO responseMemberDTO = MemberDTO.builder()
					.member_id(member.getMember_id())
					.m_pass(member.getM_pass())
					.m_name(member.getM_name())
					.m_birth(member.getM_birth())
					.m_phone(member.getM_phone())
					.m_address(member.getM_address())
					.m_email(member.getM_email())
					.m_gender(member.getM_gender())
					.m_regidate(member.getM_regidate())
					.m_kakao_id(member.getM_kakao_id())
					.m_github_id(member.getM_github_id())
					.token(token)
					.roles(member.getRoles())
					.build();
			System.out.println("signin/ roles : "+member.getRoles());
			return ResponseEntity.ok().body(responseMemberDTO);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login failed")
					.build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	// kakaoid가 DB에 저장되어있는지 확인(가입을 아예 안했다면 없어서 false,가입은했는데 KAKAO 연동이 안되있다면 kakao_id 값이 false여서 false)
	@PostMapping("/kakaoLogin")
	public Boolean kakaoLogin(@RequestBody MemberDTO memberDTO) {
		System.out.println("/login/kakaoLogin 진입");
		
//		true - 메인페이지로 이동 .. false - 회원가입페이지로 이동
		Boolean result = loginService.existsByM_kakao_id(memberDTO);
			
		return result;
			

	}
	
	@PostMapping("/kakaoSignin")
	public ResponseEntity<?> kakaoSignin(@RequestBody MemberDTO memberDTO){
		System.out.println("/login/kakaoSignin 진입");
		
//		비밀번호가 다르면 null 반환, 같으면 memberEntity 반환
		Member member = loginService.getByCredentialsKakao(memberDTO.getMember_id(), memberDTO.getM_pass(), passwordEncoder);
		
		
		
		if(member != null) {
//			create : 토큰 생성
			final String token = tokenProvider.create(member);
			System.out.println("/kakaoSignin / token : "+token);
			final MemberDTO responseMemberDTO = MemberDTO.builder()
					.member_id(member.getMember_id())
					.m_pass(member.getM_pass())
					.m_name(member.getM_name())
					.m_birth(member.getM_birth())
					.m_phone(member.getM_phone())
					.m_address(member.getM_address())
					.m_email(member.getM_email())
					.m_gender(member.getM_gender())
					.m_regidate(member.getM_regidate())
					.m_kakao_id(member.getM_kakao_id())
					.token(token)
					.build();
			
			System.out.println("/kakaoSignin / responseMemberDTO : "+responseMemberDTO);
			return ResponseEntity.ok().body(responseMemberDTO);
		}else {
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("Login failed")
					.build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	

	
	
//	회원가입 페이지에서 적은 member_id가 DB에 있는지 없는지 확인
	@PostMapping("/matchId")
	public Boolean matchId(@RequestBody MemberDTO memberDTO) {
		System.out.println("/login/matchId 진입");
		
		Boolean bool = loginService.existsByMember_id(memberDTO);
		
		return bool;
		
	}
	
//	newPw에서 입력한 비밀번호 DB에 업데이트
	@PostMapping("/newPw")
	public int newPw(@RequestBody MemberDTO memberDTO) {
		System.out.println("/login/newPw 진입");
		
		int result = loginService.updateM_pass(memberDTO);
		
		System.out.println("result : " + result);
		
		return result;
		
	}
	

	
}
