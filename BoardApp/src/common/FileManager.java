/*
 * ���ϰ� ���õ� 
 * */
package common;

public class FileManager {
	//Ȯ���ڸ� �����ϱ� 
	public static String getExtend(String path) {
		int lastIndex = path.lastIndexOf(".");
		String ext = path.substring(lastIndex+1, path.length());
//		System.out.println(lastIndex);
		return ext;
	}
	
	//�̸� ���� �׽�Ʈ �غ��� ���� 
//	public static void main(String[] args) {
//		String filename = "C:\\Photo\\summer\\2010\\����.������.�.����.����.jpg";
//		getExtend(filename);
//	}
	
}
