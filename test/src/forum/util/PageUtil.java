package forum.util;

/**
 * 
 * @author jiashubing
 *
 */
public class PageUtil {

	public static int getTotalPages(long totalNum,int pageSize){
		int ans=(int) (totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1);
		return ans;
	}
	
	public static int initPageNo(Integer pageNo){
		if (pageNo == null || pageNo < 0) {
            pageNo = 0;
        }
		return pageNo;
	}
}
