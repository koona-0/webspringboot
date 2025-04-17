package kr.co.koo.aop;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("user_dto")
public class user_dto {
	String mid, mname, memail;
}
