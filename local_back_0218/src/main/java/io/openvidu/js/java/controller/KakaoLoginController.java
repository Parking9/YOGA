package io.openvidu.js.java.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.openvidu.js.java.dto.KakaoProfileDto;
import io.openvidu.js.java.dto.OAuthTokenDto;

@RestController
@RequestMapping("/kakao")
public class KakaoLoginController {

	public static final String CLIENT_ID = "8b6ea4f1be4d3399a2d532ccb267485f";
	public static final String REDIRECT_URI = "http://localhost:8080";

	@PostMapping("/login/request")
	public ResponseEntity<Map<String, Object>> kakaoLoginRequest(@RequestBody String code) throws IOException { // return
																												// object
		System.out.println("[카카오 로그인]");
		// POST방식으로 Key=value 데이터를 요청(카카오쪽으로)
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 -Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		OAuthTokenDto oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthTokenDto.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		///////////////////////////// 카카오에서 회원정보 받기/////////////////////////////////
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		// Http 요청하기 -Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest2, String.class);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfileDto kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfileDto.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// User 오브젝트 : name, password, email
		System.out.println("=======[카카오에서 받은 회원정보]======");
		System.out.println("카카오 이메일:" + kakaoProfile.getKakao_account().getEmail());
		System.out.println("카카오 연령대:" + kakaoProfile.getKakao_account().getAge_range());
		System.out.println("카카오 성별:" + kakaoProfile.getKakao_account().getGender());
		System.out.println("카카오 닉네임(이름):" + kakaoProfile.getProperties().getNickname());
		System.out.println("카카오 프로필사진:" + kakaoProfile.getProperties().getProfile_image());
		System.out.println("===============================");

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;

		resultMap.put("email", kakaoProfile.getKakao_account().getEmail());

		if (kakaoProfile.getKakao_account().getAge_range().equals("20~29")) {
			resultMap.put("ages", "20대");
		}else if(kakaoProfile.getKakao_account().getAge_range().equals("30~39")){
			resultMap.put("ages", "30대");
		}else if(kakaoProfile.getKakao_account().getAge_range().equals("10~19")){
			resultMap.put("ages", "10대");
		}else if(kakaoProfile.getKakao_account().getAge_range().equals("40~49")){
			resultMap.put("ages", "40대");
		}

		if (kakaoProfile.getKakao_account().getGender().equals("male")) {
			resultMap.put("gender", "남자");
		} else {
			resultMap.put("gender", "여자");
		}
		resultMap.put("nickname", kakaoProfile.getProperties().getNickname());
		resultMap.put("profileImage", kakaoProfile.getProperties().getProfile_image());

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

}
