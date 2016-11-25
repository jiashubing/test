package forum.util;

import java.util.HashMap;
import java.util.Map;

/**
 * �ַ�����
 * @author 
 *
 */
public class StringUtil {

	/**
	 * �ж��Ƿ��ǿ�
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
	 * �ж��Ƿ��ǿ�
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
	 * �õ�URL���ַ����ֻ�ʺ�URL����һ������
	 * @param url
	 * @return
	 */
	public static String getParamFromUrl(String url){
		String afterQuestion=url.substring(url.indexOf("?")+1);
		return afterQuestion.substring(afterQuestion.indexOf("=")+1);
	}
}
