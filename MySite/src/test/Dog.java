/*강아지 인스턴스를 오직 1개만 만들도록 조치하기 
 * SingleTon Pattern : 객체의 인스턴스를 1개만 생성하는 패턴*/
package test;

public class Dog {
	String name = "가비";
	private static Dog instance;
	//생성자를 막는다 
	private Dog() {
		
	}
	//막아버렸으니, 인스턴스를 제공할 의무가 생긴다
	public static Dog getInstance() { // static이 없으면 클래스를 new 해서 메서드를 가져와야한다(다른 클래스에서 메서드사용시) 
		if(instance==null) {
			instance = new Dog();
		}
		return instance;
	}
}
