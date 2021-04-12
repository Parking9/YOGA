package io.openvidu.js.java.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openvidu.js.java.dao.ImgDao;


@Service
public class ImgServiceImpl implements ImgService {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int saveImage(Map<String, Object> hmap) {
		return sqlSession.getMapper(ImgDao.class).saveImage(hmap);
		
	}

	@Override
	public Map<String, Object> getByteImage(String userid) {
		return sqlSession.getMapper(ImgDao.class).getByteImage(userid);
	}

	@Override
	public int updateImage(Map<String, Object> hmap) {
		return sqlSession.getMapper(ImgDao.class).updateImage(hmap);
	}

	@Override
	public int saveThumbnail(Map<String, Object> hmap) {
		return sqlSession.getMapper(ImgDao.class).saveThumbnail(hmap);
	}


	@Override
	public Map<String, Object> getByteThumbnail(int session_id) {
		return sqlSession.getMapper(ImgDao.class).getByteThumbnail(session_id);
	}
	 


}
