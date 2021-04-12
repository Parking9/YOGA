package io.openvidu.js.java.service;

import java.util.List;

import io.openvidu.js.java.dto.NoticeDto;

public interface NoticeService {
	public List<NoticeDto> retrieveNotice();
	public NoticeDto detailNotice(int no);
	public boolean writeNotice(NoticeDto notice);
	public boolean updateNotice(NoticeDto notice);
	public boolean deleteNotice(int no);
}
