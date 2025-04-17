package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//AOP를 사용하는 형태는 기본 작동되고 있는 메소드에서 추가 코드 및 옵션이 발생했을 때 사용하는 기준
//절대 하지 말아야할 것 : 인터페이스에 추가, 실제 클래스에 코드 및 변수를 추가하지 않기
//=> 본코드 절대 건들지 않기 

//본 코드에 추가하기
public class aop_exam {

	public static void main(String[] args) {
		
		ex_interface exam = new Exam();
		
		ex_interface proxy = (ex_interface)Proxy.newProxyInstance(
				ex_interface.class.getClassLoader(),	//클래스 모두 로드 
				new Class[] {ex_interface.class}, 		//기존 클래스에 내가 덮어쓸게 
				new InvocationHandler() {				//이제 추가코드 쓸게 
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						
						long start = System.currentTimeMillis();
						
						//본코드에서 작동되고 있는 사항을 실행 
						//본코드에서 사용하는 변수와 동일한 변수를 사용하더라도 다른 변수로 인식을 하게 됨 
						Object result = method.invoke(exam, args);	//해당 클래스의 모든 메소드르르 호출 
						//invoke(클래스명, 결과값을 가지고있는 변수) : 해당 클래스에서 메소드를 실행 
//						Object result = method.invoke(exam.total(), args);	//해당 클래스의 특정 메소드만 호출  
						
						
						long end = System.currentTimeMillis();
						String message = (end-start) + "ms 시간 소요됨";
						System.out.println(message);
						
						return result;
					}
				});
		
		System.out.println("total : " + proxy.total());
		System.out.println("total : " + proxy.avg());
		
		
		
	}

}
