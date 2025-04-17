package kr.co.koo;

//Controller, Service의 역할 
public class exam implements examinterface{
/*
	// 기존에는 본코드에서 직접 실행 => AOP를 다 만들면 이렇게 잠구고 AOP에서 실행
	// => 경로를 틀어서 유지보수  
	public static void main(String[] args) {
		int result = new exam().total();
		System.out.println(result);
	}
*/
	//이것이 기존 코드 손하나 까딱하지말고 코드 추가해! => java_aop파일에서 추가해서 어노테이션 사용하기 
	@Override
	public int total() {
		int data[] = {10,20,30};
		int w = 0;
		int sum = 0;
		
		while(w < data.length) {
			sum += data[w];
			w++;
		}
		
		return sum;
	}
	
	@Override
	public int avg() {
		int avgs = 500;
		return avgs;
	}
	
	
	
}
