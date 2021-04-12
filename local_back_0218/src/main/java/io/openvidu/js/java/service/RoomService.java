package io.openvidu.js.java.service;

import java.util.List;

import io.openvidu.js.java.dto.RoomDto;


public interface RoomService {

	public int makeroom(RoomDto roomDto);

	public int overlapCheck(String title);

	public List<RoomDto> showroom();

	public RoomDto findroom(String title);

	public RoomDto selectroom(int session_id);

	public int updateRoom(RoomDto roomdto);
	
	public int deleteroom(int session_id);

}
