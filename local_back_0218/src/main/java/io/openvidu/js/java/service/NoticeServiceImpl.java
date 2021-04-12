package io.openvidu.js.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.openvidu.js.java.dao.NoticeDao;
import io.openvidu.js.java.dto.NoticeDto;


@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
	private NoticeDao noticeDao;

    @Override
	public List<NoticeDto> retrieveNotice() {
		return noticeDao.selectNotice();
	}
    
  	@Override
	public boolean writeNotice(NoticeDto notice) {
		return noticeDao.insertNotice(notice) == 1;
	}

	@Override
	public NoticeDto detailNotice(int no) {
		return noticeDao.selectNoticeByNo(no);
	}

	@Override
	@Transactional
	public boolean updateNotice(NoticeDto notice) {
		return noticeDao.updateNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int no) {
		return noticeDao.deleteNotice(no) == 1;
	}
}