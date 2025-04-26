package kr.co.koo;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface api_mapper {
	int api_insert(Map<String, String> map);

}
