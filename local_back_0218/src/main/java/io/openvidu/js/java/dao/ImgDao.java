package io.openvidu.js.java.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImgDao {

	int saveImage(Map<String, Object> hmap);

	Map<String, Object> getByteImage(String userid);

	int updateImage(Map<String, Object> hmap);

	int saveThumbnail(Map<String, Object> hmap);

	Map<String, Object> getByteThumbnail(int session_id);

}