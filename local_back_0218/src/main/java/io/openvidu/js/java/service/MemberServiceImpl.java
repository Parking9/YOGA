package io.openvidu.js.java.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openvidu.js.java.dao.MemberDao;
import io.openvidu.js.java.dto.MemberDto;


@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;
		
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberDao.class).login(memberDto);
	}

	@Override
	public String getServerInfo() {
		return "사용자에게 전달하고 싶은 중요정보";
	}

	@Override
	public int signupMember(MemberDto memberDto) {
		  return sqlSession.getMapper(MemberDao.class).signupMember(memberDto);
	}
	
	@Override
	public int updateMember(MemberDto memberDto) {
		  return sqlSession.getMapper(MemberDao.class).updateMember(memberDto);
	}
	@Override
	public int deleteMember(int id) {
		  return sqlSession.getMapper(MemberDao.class).deleteMember(id);
	}

	@Override
	public int overlapCheck(String userid) {
		 return sqlSession.getMapper(MemberDao.class).overlapCheck(userid);
	}
	
	@Override
	public MemberDto findpw(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberDao.class).findpw(memberDto);
	}
	@Override
	public MemberDto finduserid(String email) throws Exception {
		return sqlSession.getMapper(MemberDao.class).finduserid(email);
	}

	@Override
	public int saveImage(Map<String, Object> hmap) {
		return sqlSession.getMapper(MemberDao.class).saveImage(hmap);
		
	}
}
