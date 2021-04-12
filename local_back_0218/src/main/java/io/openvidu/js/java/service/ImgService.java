package io.openvidu.js.java.service;

import java.util.Map;

public interface ImgService {
	public int saveImage(Map<String, Object> hmap);

	public Map<String, Object> getByteImage(String userid);

	public int updateImage(Map<String, Object> hmap);

	public int saveThumbnail(Map<String, Object> hmap);

	public Map<String, Object> getByteThumbnail(int session_id);
}
