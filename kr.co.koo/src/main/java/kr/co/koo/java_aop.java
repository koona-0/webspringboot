package kr.co.koo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
OOP : 객체지향언어 
AOP (Aspect-Orient Programming) : 관점 지향 언어 
*/

//AOP를 적용할 클래스 
public class java_aop {
	
	public static void main(String[] args) {
		/*
		인터페이스를 로드 후 해당 인터페이스를 사용한 클래스를 호출하는 방식 
		 
//		List<String> l = new ArrayList<>();	//이거랑 같은 형식임 인터페이스 클래스 
		examinterface ex = new exam();		//앞에는 인터페이스 뒤에는 클래스 
		int result = ex.total();
		System.out.println(result);
		*/
		
		/*
		AOP (Proxy) => newProxyInstance => 기존메소드 + 신규코드 return
		
		Proxy.newProxyInstance : 동적 프록시 ClassLoad 역할 
		Class : 프록시 클래스가 구현할 인터페이스 목록 (배열)
		
		*/
		
		// AOP 사용
		examinterface ex = new exam();
		examinterface aops = (examinterface) Proxy.newProxyInstance(
				examinterface.class.getClassLoader(), // 가져올 클래스
				new Class[] { examinterface.class }, // 여러개의 클래스가있다면 범위
				new InvocationHandler() { // InvocationHandler => 자동으로 생성됨

					/*
					불러온 클래스의 모든 메소드의 리턴값에 100을 다 더해서 직접 안만진 avg의 값도 100이 더해짐 
					 */
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					
						//해당 본코드의 메소드를 실행시킨 결과값을 가져오는 역할 
						Object result = method.invoke(ex, args);	//ex로 본코드 가져옴 (invoke를 사용)

						Object total2 = Integer.parseInt(result.toString()) + 100;		//결과값에 100을 더해서 새로 만듦 

						return total2;
					}
				});
		int result = ex.total();		//본코드 실행 
		System.out.println("본코드에서 실행된 결과 : " + result);
		
		int result_aop = aops.total();	
		System.out.println("AOP에서 실행된 결과값 : " + result_aop);
		
		int result_aop2 = aops.avg();
		System.out.println("AOP에서 실행된 결과값2 : " + result_aop2);
		
		
	}

}
