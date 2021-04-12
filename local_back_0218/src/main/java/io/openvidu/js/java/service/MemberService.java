package io.openvidu.js.java.service;

import java.util.Map;

import io.openvidu.js.java.dto.MemberDto;


public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public String getServerInfo() throws Exception;
	public int signupMember(MemberDto memberDto) throws Exception;
	public int updateMember(MemberDto memberDto) throws Exception;
	public int deleteMember(int id) throws Exception;
	public int overlapCheck(String userid) throws Exception;
	public MemberDto findpw(MemberDto memberDto) throws Exception;
	public MemberDto finduserid(String email) throws Exception;
	public int saveImage(Map<String, Object> hmap);
	
}
