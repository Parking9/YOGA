package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.dto.MemberDto;
import io.openvidu.js.java.service.JwtService;
import io.openvidu.js.java.service.MemberService;
import io.swagger.annotations.ApiOperation;

//http://localhost:8800/vue/swagger-ui.html

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class MemberController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private JavaMailSender javaMailSender;

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@ApiOperation(value = "로그인을 한다.", response = List.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDto memberDto, HttpServletResponse response,
			HttpSession session) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {

			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				// jwt.io에서 확인
				// 로그인 성공했다면 토큰을 생성한다.
				String token = jwtService.create(loginUser);
				logger.trace("로그인 토큰정보 : {}", token);

				// 토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
				// response.setHeader("auth-token", token);
				resultMap.put("auth-token", token);
				resultMap.put("user-id", loginUser.getUserid());
				resultMap.put("user-name", loginUser.getUsername());
				resultMap.put("message", "로그인 성공");
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "토큰의 회원 정보를 반환한다.", response = List.class)
	@PostMapping("/info")
	public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest req) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		// System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")));
		try {
			resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			logger.error("정보조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 가입을 한다.", response = List.class)
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if(memberDto.getProfileImg().contains("blob:http")) {
			memberDto.setProfileImg("");
		}
		try {
			memberService.signupMember(memberDto);
			resultMap.put("message", "가입 성공");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "가입 실패");
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원 정보를 수정하고 토큰을 갱신한다.", response = List.class)
	@PutMapping("/update/{id}")
	public ResponseEntity<Map<String, Object>> update(@RequestBody MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if(memberDto.getProfileImg().contains("blob:http")) {
			memberDto.setProfileImg("");
		}
		try {
			memberService.updateMember(memberDto);
			resultMap.put("message", "수정 성공");
			String token = jwtService.create(memberDto);
			resultMap.put("auth-token", token);
			System.out.println(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "수정 실패");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원 탈퇴를 한다.", response = List.class)
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleteMember(id);
			resultMap.put("message", "삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "삭제 실패");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "중복 체크를 한다.", response = List.class)
	@GetMapping("/overlap/{userid}")
	public ResponseEntity<Map<String, Object>> overlap(@PathVariable String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int cnt = memberService.overlapCheck(userid);
			resultMap.put("cnt", cnt);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "중복 체크 실패 ");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* userid 찾기 */
	@ApiOperation(value = "이메일을 통해 유저 아이디를 찾는다.", response = List.class)
	@GetMapping("/finduserid/{email}")
	public ResponseEntity<Map<String, Object>> finduserid(@PathVariable String email) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto finduseriduser = memberService.finduserid(email);
			if (finduseriduser != null) {
				String msg = finduseriduser.getEmail() + "로 등록한 회원님의 아이디는 " + finduseriduser.getUserid() + "입니다!!";
				resultMap.put("message", msg);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "아이디 찾기 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("찾기 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	/* 비밀번호 찾기 */
	@ApiOperation(value = "비밀번호 찾기.", response = List.class)
	@PostMapping("/findpw")
	public ResponseEntity<Map<String, Object>> findpw(@RequestBody MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			MemberDto findpwuser = memberService.findpw(memberDto);
			if (findpwuser != null) {
				// 임시 비밀번호 생성
				String pw = "";
				for (int i = 0; i < 18; i++) {
					pw += (char) ((Math.random() * 26) + 97);
				}

				findpwuser.setUserpwd(pw);
				findpwuser.setUserpwdconfirm(pw);
				memberService.updateMember(findpwuser);

				String subject = "YOGA 임시 비밀번호 입니다.";
				String msg = "";
				msg += findpwuser.getUserid() + "님의 임시 비밀번호 입니다.\n 비밀번호를 변경하여 사용하세요.\n";
				msg += "임시 비밀번호 : " + findpwuser.getUserpwd() + "\n";

				// 받는 사람 E-Mail 주소
				String mail = findpwuser.getEmail();

				try {
					SimpleMailMessage email = new SimpleMailMessage();
					email.setTo(mail);
					email.setFrom("yodo@yodo.com");
					email.setSubject(subject);
					email.setText(msg);
					javaMailSender.send(email);

				} catch (Exception e) {
					System.out.println("메일발송 실패 : " + e);
				}
				resultMap.put("message", "비밀번호 찾기 성공");
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "비밀번호 찾기 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("찾기 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

		/*
		 * 네이버 이메일을 이용하는 경우 String hostSMTP = "smtp.naver.com"; email.setSmtpPort(587);
		 * 지메일을 이용하는 경우 String hostSMTP = "smtp.gmail.com"; email.setSmtpPort(465); 처음엔
		 * 동일한 587 포트를 사용했으나 오류가 발생하여 구글링끝에 465로 설정하니 정상 작동하였다. 지메일을 이용하는 경우 구글 계정에서 보안
		 * 수준이 낮은 앱의 액세스를 허용해야한다. 보내는 사람과 받는 사람 둘 다 보안 수준이 낮은 앱의 액세스를 허용해야지만 정상 작동한다.
		 */

	}
}
