package kr.co.koo.thymeleaf;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import lombok.Data;

//Database와 관계없이 AOP와 Controller에서 서로 상호작용을 하기 위한 DTO
@Data
@Repository("all_DTO")
public class all_DTO {
	ArrayList<String> menus;
	String mid,mpass,memail;

}
