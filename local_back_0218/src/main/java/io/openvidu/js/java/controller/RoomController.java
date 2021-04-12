package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.dto.MemberDto;
import io.openvidu.js.java.dto.RoomDto;
import io.openvidu.js.java.service.RoomService;
import io.swagger.annotations.ApiOperation;

//http://localhost:8800/vue/swagger-ui.html

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	private RoomService roomService;

	public static final Logger logger = LoggerFactory.getLogger(RoomController.class);

	@ApiOperation(value = "방 만들기", response = List.class)
	@PostMapping("/makeroom")
	public ResponseEntity<Map<String, Object>> makeroom(@RequestBody RoomDto roomDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if(roomDto.getThumbnail().contains("blob:http")) {
			roomDto.setThumbnail("있음");
		}else {
			roomDto.setThumbnail("없음");
		}
		try {
			roomService.makeroom(roomDto); 
			RoomDto froom = roomService.findroom(roomDto.getTitle());
			System.out.println("froom "+froom);
			resultMap.put("message", "방 만들기 성공");
			resultMap.put("session_id", froom.getSession_id());
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "방 만들기 실패");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	
	@ApiOperation(value = "방 제목 중복 체크를 한다.", response = List.class)
	@GetMapping("/overlap/{title}")
	public ResponseEntity<Map<String, Object>> overlap(@PathVariable String title) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			int cnt = roomService.overlapCheck(title);
			resultMap.put("cnt", cnt);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "중복 체크 실패 ");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "방을 보여준다.", response = List.class)
	@PostMapping("/showroom")
	public ResponseEntity<List<RoomDto>> showroom() {
		HttpStatus status = HttpStatus.ACCEPTED;
		List<RoomDto> rooms = roomService.showroom();
		return new ResponseEntity<List<RoomDto>>(rooms,status);
	}
	
	@ApiOperation(value = "방의 정보를 가지고 온다.", response = List.class)
	@GetMapping("/selectroom/{session_id}")
	public ResponseEntity<Map<String, Object>> selectroom(@PathVariable int session_id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			RoomDto sroom = roomService.selectroom(session_id);
			if(sroom==null) {
				resultMap.put("message", "FAIL");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}
			resultMap.put("room", sroom);
			resultMap.put("message", "수정 성공");
//			System.out.println(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "수정 실패");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "방의 정보를 업데이트해준다.", response = List.class)
	@PutMapping("/updateRoom/{session_id}")
	public ResponseEntity<Map<String, Object>> updateRoom(@RequestBody RoomDto roomdto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		System.out.println("!!!!!!!" + roomdto);
		try {
			roomService.updateRoom(roomdto);
			resultMap.put("message", "수정 성공");
			System.out.println(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "수정 실패");
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
