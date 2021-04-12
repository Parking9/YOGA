package io.openvidu.js.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.openvidu.js.java.dao.FnQDao;
import io.openvidu.js.java.dto.FnQDto;


@Service
public class FnQServiceImpl implements FnQService {
    @Autowired
	private FnQDao FnQDao;

    @Override
	public List<FnQDto> retrieveFnQ() {
		return FnQDao.selectFnQ();
	}
    
  	@Override
	public boolean writeFnQ(FnQDto FnQ) {
		return FnQDao.insertFnQ(FnQ) == 1;
	}

	@Override
	public FnQDto detailFnQ(int no) {
		return FnQDao.selectFnQByNo(no);
	}

	@Override
	@Transactional
	public boolean updateFnQ(FnQDto FnQ) {
		return FnQDao.updateFnQ(FnQ) == 1;
	}

	@Override
	@Transactional
	public boolean deleteFnQ(int no) {
		return FnQDao.deleteFnQ(no) == 1;
	}
}