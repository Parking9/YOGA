package io.openvidu.js.java.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameDao {

	String liarGame(int id);

	String topicGame(Map<String, Object> hmap);

	String recommend(int id);

}