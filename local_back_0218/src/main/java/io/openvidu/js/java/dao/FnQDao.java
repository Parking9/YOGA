package io.openvidu.js.java.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.dto.FnQDto;


@Mapper
public interface FnQDao {
	public List<FnQDto> selectFnQ();
	public FnQDto selectFnQByNo(int no);
	public int insertFnQ(FnQDto FnQ);
	public int updateFnQ(FnQDto FnQ);
	public int deleteFnQ(int no);
}