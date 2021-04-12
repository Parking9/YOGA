package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.openvidu.js.java.dto.ImgDto;
import io.openvidu.js.java.dto.MemberDto;
import io.openvidu.js.java.service.ImgService;

@CrossOrigin("*")
@RestController
@RequestMapping("/imgs")
public class ImgController {

	@Autowired
	private ImgService imgservice;

	@PostMapping("/saveImage")
	public ResponseEntity<String> saveImage(@RequestPart("upload") MultipartFile profileImg,
			@RequestParam("userid") String userid) {
		try {
			Map<String, Object> hmap = new HashMap<String, Object>();
			hmap.put("profileImg", profileImg.getBytes());
			hmap.put("userid", userid);
			imgservice.saveImage(hmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("/getByteImage/{userid}")
	public ResponseEntity<byte[]> getByteImage(@PathVariable String userid) {
		Map<String, Object> map = imgservice.getByteImage(userid);
		byte[] imageContent = (byte[]) map.get("profileImg");
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

	@PutMapping("/updateImage/{userid}")
	public ResponseEntity<String> updateImage(@PathVariable String userid,
			@RequestPart("upload") MultipartFile profileImg) {
		try {
			Map<String, Object> hmap = new HashMap<String, Object>();
			hmap.put("profileImg", profileImg.getBytes());
			hmap.put("userid", userid);
			imgservice.updateImage(hmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@PostMapping("/saveThumbnail")
	public ResponseEntity<String> saveThumbnail(@RequestPart("upload") MultipartFile thumbnail,
			@RequestParam("session_id") int session_id) {
		try {
			Map<String, Object> hmap = new HashMap<String, Object>();
			hmap.put("thumbnail", thumbnail.getBytes());
			hmap.put("session_id", session_id);
			imgservice.saveThumbnail(hmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	@GetMapping("/getByteThumbnail/{session_id}")
	public ResponseEntity<byte[]> getByteThunmbnail(@PathVariable int session_id) {
		Map<String, Object> map = imgservice.getByteThumbnail(session_id);
		byte[] imageContent = (byte[]) map.get("thumbnail");
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

}