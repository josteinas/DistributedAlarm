package util;

public class DAUtils {
	public static String randString(int length){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < length; i++){
			int asciinum = (int) (((Math.random()* (122-65-6))) + 65);
			builder.append((char)asciinum);
		}
		return builder.toString();
	}
}
