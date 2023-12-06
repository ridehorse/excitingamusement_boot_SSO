package com.exciting.login.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exciting.login.dto.MemberDTO;
import com.exciting.login.entity.Member;
import com.exciting.login.persistence.LoginRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
//	memberEntity로 member table에 저장
	public Member memberCreate(final Member memberEntity) {
		if(memberEntity == null || memberEntity.getMember_id() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		
		final String member_id = memberEntity.getMember_id();
		if(loginRepository.existsByMember_id(member_id)) {
			log.warn("member_id already exists {}",member_id);
			throw new RuntimeException("member_id already exists");
		}
		
		return loginRepository.save(memberEntity);
	}
	
	
//	memberEntity로 member table에 저장
	public int socialMemberUpdate(final Member memberEntity) {
		if(memberEntity == null || memberEntity.getMember_id() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		
		String member_id = memberEntity.getMember_id();
		String m_gender = memberEntity.getM_gender();
		String m_birth = memberEntity.getM_birth();
		String m_address = memberEntity.getM_address();
		String m_email = memberEntity.getM_email();
		String m_name = memberEntity.getM_name();
		String m_phone = memberEntity.getM_phone();
		String m_pass = passwordEncoder.encode(memberEntity.getM_pass());
		
		
		return loginRepository.socialMemberUpdate(member_id,m_gender,m_birth,m_address,m_email,m_name,m_phone,m_pass);
	}
	

	
	
	
//	로그인 검증(비밀번호가 다르면 null 반환, 같으면 memberEntity 반환)
	public Member getByCredentials(final String member_id,final String password,final PasswordEncoder encoder) {
		
		final Member originalUser = loginRepository.findByMember_id(member_id);
		System.out.println("getBYCredentials/memberEntity : "+originalUser);
		
//		사용자가 요청할때 입력한 password와 DB에 저장되어 있는 password 비교(DB에는 인코딩한 password가 저장되어있다)
		if(originalUser != null && encoder.matches(password, originalUser.getM_pass())) {
			return originalUser;
		}
		
		return null;
	}
	
//	카카오 아이디로 접속 했을 때 로그인 검증
	public Member getByCredentialsKakao(final String member_id,final String password,final PasswordEncoder encoder) {
		
		final Member originalUser = loginRepository.findByMember_id(member_id);
		System.out.println("getBYCredentials/memberEntity : "+originalUser);
		
//		사용자가 요청할때 입력한 password와 DB에 저장되어 있는 password 비교(DB에는 인코딩한 password가 저장되어있다)
		if(originalUser != null) {
			return originalUser;
		}
		
		return null;
	}
	
//	member_id에 일치하는 행 모두 반환(entity로 반환)
	public Member entityByMember_id(final MemberDTO memberDTO) {
		String member_id = memberDTO.getMember_id();
		return loginRepository.findByMember_id(member_id);
	}
	
	
	
//	member_id에 일치하는 행 모두 반환(DTO로 반환)
	public MemberDTO getByMember_id(final MemberDTO memberDTO) {
		System.out.println("getByMember_id / memberDTO : "+memberDTO);
		String member_id = memberDTO.getMember_id();
		System.out.println("getByMember_id / member_id : "+member_id);
		Member member = loginRepository.findByMember_id(member_id);
		System.out.println("getByMember_id / member_id : "+member_id);
		System.out.println("getByMember_id / member : "+member);
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
				.m_image(member.getM_image())
				.build();
		return responseMemberDTO;
	}
	
//	member_id에 일치하는 행 모두 반환(DTO로 반환)
	public MemberDTO getByKakao_id(final MemberDTO memberDTO) {
		String kakao_id = memberDTO.getM_kakao_id();
		System.out.println("getByKakao_id / memberDTO : "+memberDTO);
		Member member = loginRepository.findByKakao_id(kakao_id);
		System.out.println("getByKakao_id / kakao_id : "+kakao_id);
		System.out.println("getByKakao_id / member : "+member);
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
				.m_image(member.getM_image())
				.build();
		return responseMemberDTO;
	}
	
//	MemberDTO 를 받아서 member_id와 일치하는 행이 있는지 검사 후 있으면 true, 없으면 false 반환.
	public Boolean existsByMember_id(final MemberDTO memberDTO) {
		String member_id = memberDTO.getMember_id();
		return loginRepository.existsByMember_id(member_id);
	}
	
//	MemberDTO 를 받아서 m_kakao_id와 일치하는 행이 있는지 검사 후 있으면 true, 없으면 false 반환.
	public Boolean existsByM_kakao_id(final MemberDTO memberDTO) {
		String m_kakao_id = memberDTO.getM_kakao_id();
		return loginRepository.existsByM_kakao_id(m_kakao_id);
	}
	
//	MemberDTO 를 받아서 m_github_id와 일치하는 행이 있는지 검사 후 있으면 true, 없으면 false 반환.
	public Boolean existsByM_github_id(final MemberDTO memberDTO) {
		String m_github_id = memberDTO.getMember_id();
		return loginRepository.existsByM_github_id(m_github_id);
	}
	
//	m_github_id 를 받아서 m_github_id와 일치하는 행이 있는지 검사 후 있으면 true, 없으면 false 반환.
	public int getNameByM_github_id(final String m_github_id) {
		return loginRepository.getNameByM_github_id(m_github_id);
	}
	
//	m_kakao_id 를 받아서 m_kakao_id와 일치하는 행이 있는지 검사 후 있으면 true, 없으면 false 반환.
	public int getNameByM_kakao_id(final String m_kakao_id) {
		return loginRepository.getNameByM_kakao_id(m_kakao_id);
	}
	
//	m_naver_id 를 받아서 m_naver_id와 일치하는 행이 있는지 검사 후 있으면 true, 없으면 false 반환.
	public int getNameByM_naver_id(final String m_naver_id) {
		return loginRepository.getNameByM_naver_id(m_naver_id);
	}
	
//	controller 단마다 실행할 인증
	public void authenticationById(final String id) {
		
		if(id == "anonymousUser") {
			new RuntimeException("인증실패");
		}
	}
	
//	memberDTO를 받아서 사용자로부터 받은 password를 DB에 수정후 결과(수정한 행의 갯수 int로)반환
	public int updateM_pass(final MemberDTO memberDTO) {
		
		String member_id = memberDTO.getMember_id();
		String m_pass = passwordEncoder.encode(memberDTO.getM_pass());
		
		int result = loginRepository.updateM_pass(member_id,m_pass);
		
		return result;
		
	}
	
//	memberEntity를 받아서 member table 업데이트
	public int updateM_image(final Member memberEntity) {
		
		String member_id = memberEntity.getMember_id();
		String m_image = memberEntity.getM_image();
		
		
		int result = loginRepository.updateM_image(member_id,m_image);
		
		return result;
		
	}
	
//	memberEntity를 받아서 member table 업데이트
	public int deleteMember(final String member_id) {
		
		
		int result = loginRepository.deleteMember(member_id);
		
		return result;
		
	}

	
}
