package io.openvidu.js.java.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openvidu.js.java.dao.GameDao;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public String liarGame(int id) {
		return sqlSession.getMapper(GameDao.class).liarGame(id);
	}

	@Override
	public String topicGame(Map<String, Object> hmap) {
		return sqlSession.getMapper(GameDao.class).topicGame(hmap);
	}

	@Override
	public String recommend(int id) {
		return sqlSession.getMapper(GameDao.class).recommend(id);
	}

}
