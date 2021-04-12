package io.openvidu.js.java.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.dto.MemberDto;


@Mapper
public interface MemberDao {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public int signupMember(MemberDto memberDto);
	public int updateMember(MemberDto memberDto);
	public int deleteMember(int id);
	public int overlapCheck(String userid);
	public MemberDto findpw(MemberDto memberDto);
	public MemberDto finduserid(String email);
	public int saveImage(Map<String, Object> hmap);
}
