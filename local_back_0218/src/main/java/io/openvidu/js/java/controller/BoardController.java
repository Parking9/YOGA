package io.openvidu.js.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.js.java.dto.FnQDto;
import io.openvidu.js.java.dto.NoticeDto;
import io.openvidu.js.java.service.FnQService;
import io.openvidu.js.java.service.NoticeService;
import io.swagger.annotations.ApiOperation;


@CrossOrigin("*")
@RestController
@RequestMapping("/board")
public class BoardController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NoticeService noticeService;

	@ApiOperation(value = "모든 공지사항의 정보를 반환한다.", response = List.class)
	@GetMapping("/notice")
	public ResponseEntity<List<NoticeDto>> retrieveNotice() throws Exception {
		return new ResponseEntity<List<NoticeDto>>(noticeService.retrieveNotice(), HttpStatus.OK);
	}

	@ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 반환한다.", response = NoticeDto.class)
	@GetMapping("/notice/{no}")
	public ResponseEntity<NoticeDto> detailNotice(@PathVariable int no) {
		return new ResponseEntity<NoticeDto>(noticeService.detailNotice(no), HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 공지사항 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/notice")
	public ResponseEntity<String> writeNotice(@RequestBody NoticeDto notice) {
		if (noticeService.writeNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/notice/{no}")
	public ResponseEntity<String> updateNotice(@RequestBody NoticeDto notice) {
		if (noticeService.updateNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 공지사항의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/notice/{no}")
	public ResponseEntity<String> deleteNotice(@PathVariable int no) {
		if (noticeService.deleteNotice(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	///////////////
	@Autowired
	private FnQService FnQService;

	@ApiOperation(value = "모든 F&Q의 정보를 반환한다.", response = List.class)
	@GetMapping("/FnQ")
	public ResponseEntity<List<FnQDto>> retrieveFnQ() throws Exception {
		return new ResponseEntity<List<FnQDto>>(FnQService.retrieveFnQ(), HttpStatus.OK);
	}

	@ApiOperation(value = "글번호에 해당하는 F&Q의 정보를 반환한다.", response = FnQDto.class)
	@GetMapping("/FnQ/{no}")
	public ResponseEntity<FnQDto> detailFnQ(@PathVariable int no) {
		return new ResponseEntity<FnQDto>(FnQService.detailFnQ(no), HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 F&Q 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/FnQ")
	public ResponseEntity<String> writeFnQ(@RequestBody FnQDto FnQ) {
		if (FnQService.writeFnQ(FnQ)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 F&Q의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/FnQ/{no}")
	public ResponseEntity<String> updateFnQ(@RequestBody FnQDto FnQ) {
		if (FnQService.updateFnQ(FnQ)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 F&Q의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/FnQ/{no}")
	public ResponseEntity<String> deleteFnQ(@PathVariable int no) {
		if (FnQService.deleteFnQ(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}