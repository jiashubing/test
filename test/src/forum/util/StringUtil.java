package forum.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 字符串静态方法类
 * @author jiashubing
 *
 */
public class StringUtil {
	
	/**
	 * 判断是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null||"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断是否不空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getParamFromUrl(String url){
		String afterQuestion=url.substring(url.indexOf("?")+1);
		return afterQuestion.substring(afterQuestion.indexOf("=")+1);
	}
}
