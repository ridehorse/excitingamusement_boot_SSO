package com.exciting.login.controller;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
import com.exciting.login.dto.MemberDTO;
import com.exciting.login.entity.Member;
import com.exciting.login.persistence.LoginRepository;
import com.exciting.login.security.TokenProvider;
import com.exciting.login.service.LoginService;
import com.exciting.utils.Gmail;
import com.exciting.utils.Sha256;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@PostMapping
	public ResponseEntity<?> emailSendActionPost(@RequestBody MemberDTO memberDTO) {
		System.out.println("/Email.post() 접근");
		
//		유저가 입력한 이메일주소
		String userEmail = memberDTO.getM_email();
		
//		MemberDTO를 전달하면 member_id에 맞는 entity 반환(
		Member memberEntity = loginService.entityByMember_id(memberDTO);
		
		System.out.println("memberEntity : "+memberEntity);
		
		int confirmNumber;
		
//		memberEntity가 비어있지 않다는건 사용자가 입력한 아이디가 존재한다는 뜻이다.
		if(memberEntity != null) {
			
			System.out.println("유저가 입력한 아이디에 등록한 email : "+memberEntity.getM_email());
			System.out.println("유저가 입력한 email : "+userEmail);

			//		사용자가 searchId 에서 입력한 전화번호와 member table의 등록된 이름이 속한 행에 있는 email이 일치하는지 확인
			if(memberEntity.getM_email().equals(userEmail)) {
			
				//			6자리 랜덤 숫자
				java.util.Random generator = new java.util.Random();
				generator.setSeed(System.currentTimeMillis());
				confirmNumber = generator.nextInt(1000000) % 1000000;

				//			이메일 전송 설정
				String host = "localhost:8080";
				String from = "momumu8282@gmail.com";
				String to = memberEntity.getM_email();
				String subject = "EXCITING AMUSEMENT 아이디 찾기 인증메일";
				String sha256 = new Sha256().getSHA256(to);
				String content = "EXCITING AMUSEMENT 웹페이지에서 다음 인증코드 6자리를 입력하세요 >> "+confirmNumber;
				//		"<a href='" + host + "/sendEmail?m_sha256=" + sha256 + "'>이메일 인증하기</a>";

				Properties prop = new Properties(); 

				prop.put("mail.smtp.user", from);
				prop.put("mail.smtp.host", "smtp.googlemail.com");
				prop.put("mail.smtp.port", "465");
				prop.put("mail.smtp.starttls.enable", "true");
				prop.put("mail.smtp.auth", "true");
				prop.put("mail.smtp.debug", "true");
				prop.put("mail.smtp.socketFactory.port", "465");
				prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				prop.put("mail.smtp.socketFactory.fallback", "false");
				prop.put("mail.smtp.ssl.enable", "true"); // SSL 활성화
				prop.put("mail.smtp.ssl.protocols", "TLSv1.2");


				try {
					Authenticator auth = new Gmail();
					Session ses = Session.getInstance(prop, auth);
					ses.setDebug(true);
					MimeMessage msg = new MimeMessage(ses);
					msg.setSubject(subject);
					Address fromAddr = new InternetAddress(from);
					msg.setFrom(fromAddr);
					Address toAddr = new InternetAddress(to);
					msg.addRecipient(Message.RecipientType.TO,toAddr);
					msg.setContent(content, "text/html;charset=UTF8");
					Transport.send(msg);
				}catch(Exception e) {
					e.printStackTrace();
				}
				System.out.println("/Email 종료");

//				ok()는 200 반환이다.
				return ResponseEntity.ok().body(confirmNumber);
			
			}else {
				confirmNumber = 1;
				return ResponseEntity.ok().body(confirmNumber);
			}
//		사용자가 입력한 아이디가 존재하지않으면 오류 보냄
		}else{
//			badRequest는 400 반환이다.
			ResponseDTO responseDTO = ResponseDTO.builder()
					.error("일치하는 아이디가 없음")
					.build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
}
