/*������ �ν��Ͻ��� ���� 1���� ���鵵�� ��ġ�ϱ� 
 * SingleTon Pattern : ��ü�� �ν��Ͻ��� 1���� �����ϴ� ����*/
package test;

public class Dog {
	String name = "����";
	private static Dog instance;
	//�����ڸ� ���´� 
	private Dog() {
		
	}
	//���ƹ�������, �ν��Ͻ��� ������ �ǹ��� �����
	public static Dog getInstance() { // static�� ������ Ŭ������ new �ؼ� �޼��带 �����;��Ѵ�(�ٸ� Ŭ�������� �޼������) 
		if(instance==null) {
			instance = new Dog();
		}
		return instance;
	}
}
