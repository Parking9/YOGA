package io.openvidu.js.java.service;

import java.util.List;

import io.openvidu.js.java.dto.FnQDto;


public interface FnQService {
	public List<FnQDto> retrieveFnQ();
	public FnQDto detailFnQ(int no);
	public boolean writeFnQ(FnQDto FnQ);
	public boolean updateFnQ(FnQDto FnQ);
	public boolean deleteFnQ(int no);
}
