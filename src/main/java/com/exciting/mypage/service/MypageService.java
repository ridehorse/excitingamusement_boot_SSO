package com.exciting.mypage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exciting.login.dto.Criteria;
import com.exciting.login.dto.MemberDTO;
import com.exciting.login.dto.MypageDTO;
import com.exciting.mypage.persistence.MypageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MypageService {

	@Autowired
	private MypageRepository mypageRepository;


	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	//	editMember에서 사용자가 입력한 값 DB에 수정
	public int updateMember(final MemberDTO memberDTO) {
		
		String member_id = memberDTO.getMember_id();
		String m_address = memberDTO.getM_address();
		String m_birth = memberDTO.getM_birth();
		String m_email = memberDTO.getM_email();
		String m_phone = memberDTO.getM_phone();

		int result = mypageRepository.updateMember(member_id,m_email,m_birth,m_address,m_phone);

		return result;

	}

	//	총 게시물 수
	public int countByMember_id(Criteria cri) {

		String member_id = cri.getMember_id();

		return mypageRepository.countByMember_id(member_id);
	}

	public List<Object[]> selectDescByMember_id(Criteria cri){

		int limitNum = cri.getLimitNum();
		int amount = cri.getAmount();
		String member_id = cri.getMember_id();

		return mypageRepository.selectDescByMember_id(member_id, limitNum , amount);
	}

	public List<Object[]> selectAscByMember_id(Criteria cri){

		int limitNum = cri.getLimitNum();
		int amount = cri.getAmount();
		String member_id = cri.getMember_id();

		return mypageRepository.selectAscByMember_id(member_id, limitNum , amount);
	}

}
