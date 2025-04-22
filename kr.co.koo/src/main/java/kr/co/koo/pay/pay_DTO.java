package kr.co.koo.pay;

import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository("pay_DTO")
public class pay_DTO {
	String goodcode, goodname, goodprice, goodea;
	
	//상품코드, 상품명, 상품금액, 상품수량, 결제금액 
	String product_code, product_nm, product_money, product_ea;
	String price;
	
	//고객명, 고객연락처, 고객이메일, 우편번호, 도로명주소, 상세주소, 결제방식 
	String buyername, buyertel, buyeremail, rec_post, rec_way, rec_addr;
	String gopaymethod;

}
