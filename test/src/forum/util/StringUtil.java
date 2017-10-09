package forum.util;

/**
 * 字符串静态方法类
 *
 * @author jiashubing
 */
public class StringUtil {

    /**
     * 判断是否为空
     */
    static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 判断是否不空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getParamFromUrl(String url) {
        String afterQuestion = url.substring(url.indexOf("?") + 1);
        return afterQuestion.substring(afterQuestion.indexOf("=") + 1);
    }
}
