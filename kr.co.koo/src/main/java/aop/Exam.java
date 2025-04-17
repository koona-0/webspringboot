package aop;

//본코드 
public class Exam implements ex_interface{
	
	public static void main(String[] args) {
		float result = new Exam().avg();
		System.out.println(result);
	}
	
	@Override
	public float avg() {
		float result = total() / 4.0f;
		return result;
	}
	
	/*
	주석이 된 코드를 aop로 만들어서 추가하기
	*/
	@Override
	public int total() {
//		long start = System.currentTimeMillis();
		
		int result = 100 + 200 + 300;
		
		try {
			Thread.sleep(1000);
		}catch (Exception e) {

		}
		
		/*
		long end = System.currentTimeMillis();
		String message = (end-start) + "ms 시간 소요됨";
		System.out.println(message);
		*/
		
		return result;
	}
	
}
