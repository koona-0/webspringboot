package kr.co.koo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

//맵퍼 사용 두가지 방법
//

//1. mapper.xml => mapper(interface) => service(interface) => dao
//	Controller => Service(interface) => dao

//2. mapper.xml => mapper(interface) => dao
//	Controller => dao
@Mapper
public interface mapper {
	//@Query("select * from MEMBERSHIP")	이런거로 바로때리기도됨 
	public List<membership_dto> alldata();
	public List<membership_dto> onedata(String MID);

}
