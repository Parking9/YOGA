package io.openvidu.js.java.service;

import java.util.Map;

public interface GameService {

	String liarGame(int id);

	String topicGame(Map<String, Object> hmap);

	String recommend(int id);
}
