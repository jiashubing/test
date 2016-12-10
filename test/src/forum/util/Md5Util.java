package forum.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class Md5Util {
	
	public static String toMD5(String plainText){
		Md5PasswordEncoder x=new Md5PasswordEncoder();
		return x.encodePassword(plainText, null);
	}

	public static void main(String[] args) {
		System.out.println(toMD5("admin"));
	}

}
