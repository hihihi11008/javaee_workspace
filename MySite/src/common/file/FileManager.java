/*
 * 파일과 관련된 
 * */
package common.file;

import java.io.File;

public class FileManager {
	//확장자만 추출하기 
	public static String getExtend(String path) {
		int lastIndex = path.lastIndexOf(".");
		String ext = path.substring(lastIndex+1, path.length());
//		System.out.println(lastIndex);
		return ext;
	}
	
	//파일삭제 : 호출자는 삭제하고 싶은 파일의 경로를 넘긴다 
	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}
	
	//미리 단위 테스트 해보기 위함 
//	public static void main(String[] args) {
//		String filename = "C:\\Photo\\summer\\2010\\지난.여름에.놀러.갔던.사진.jpg";
//		getExtend(filename);
//	}
	
}
