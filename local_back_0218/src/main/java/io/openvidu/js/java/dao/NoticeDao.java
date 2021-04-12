package io.openvidu.js.java.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.dto.NoticeDto;


@Mapper
public interface NoticeDao {
	public List<NoticeDto> selectNotice();
	public NoticeDto selectNoticeByNo(int no);
	public int insertNotice(NoticeDto notice);
	public int updateNotice(NoticeDto notice);
	public int deleteNotice(int no);
}