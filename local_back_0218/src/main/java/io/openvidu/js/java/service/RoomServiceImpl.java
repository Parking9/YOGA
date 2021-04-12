package io.openvidu.js.java.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openvidu.js.java.dao.MemberDao;
import io.openvidu.js.java.dao.RoomDao;
import io.openvidu.js.java.dto.RoomDto;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int makeroom(RoomDto roomDto) {
		return sqlSession.getMapper(RoomDao.class).makeroom(roomDto);
	}

	@Override
	public int overlapCheck(String title) {
		return sqlSession.getMapper(RoomDao.class).overlapCheck(title);
	}

	@Override
	public List<RoomDto> showroom() {
		return sqlSession.getMapper(RoomDao.class).showroom();
	}

	@Override
	public RoomDto findroom(String title) {
		return sqlSession.getMapper(RoomDao.class).findroom(title);
	}

	@Override
	public RoomDto selectroom(int session_id) {
		return sqlSession.getMapper(RoomDao.class).selectroom(session_id);
		}

	@Override
	public int updateRoom(RoomDto roomdto) {
		return sqlSession.getMapper(RoomDao.class).updateRoom(roomdto);
	}

	@Override
	public int deleteroom(int session_id) {
		return sqlSession.getMapper(RoomDao.class).deleteroom(session_id);
	}

}
