package bigdata.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

@Deprecated
public class PasswordUtil {
	
	 static String generateRandomSalt(){
		int index=((Double)Math.floor(Math.random()*(63))).intValue();
		int saltLen=10;
		StringBuilder salt=new StringBuilder();
			while(saltLen>0){
				saltLen--;
				
				if(index<9) salt.append(index);
				else if(index>36) salt.append(Character.toChars(index));
				else salt.append(Character.toChars(index));
			
			}
			
			return salt.toString();
	}
	
	
	public static String encodePassword(String password){
		Md5PasswordEncoder encoder=new Md5PasswordEncoder();
		String salt=generateRandomSalt();
		return encoder.encodePassword(password, salt);
	}
}
