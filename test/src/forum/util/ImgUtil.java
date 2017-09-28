package forum.util;

public class ImgUtil {
	public static String FORUM_PATH = "/WEB-INF/pc/forum/";
	public static String USER_FACE = "images/userFaceImg";
	public static String SECTION_FACE = "images/sectionImg";
	public static String TMPIMG_PATH = "/WEB-INF/images/tmpImg/";
	public static String REAL_PATH = "/WEB-INF/images/reaImg";
	public static String TOOLS_PATH = "/WEB-INF/pc/onlinetools/";
	public static String TOOLS_TXT = "txts";

	public static String ABSOLUTE_TMPIMG_PATH ="";
	public static String ABSOLUTE_REAL_PATH ="";
	public static String ABSOLUTE_TMPPDF_PATH ="";
	public static String ABSOLUTE_TOOLS_TXT_PATH ="";

	static{
		String inPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		String tmp = inPath.substring(0,inPath.indexOf("classes"));
		ABSOLUTE_TMPIMG_PATH = tmp + "images/tmpImg/";
		ABSOLUTE_REAL_PATH = tmp + "images/reaImg/";
		ABSOLUTE_TMPPDF_PATH = tmp + "images/tmpPdf/";
		ABSOLUTE_TOOLS_TXT_PATH = tmp + "pc/onlinetools/txts/";
	}

}
