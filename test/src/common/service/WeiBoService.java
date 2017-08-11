package common.service;

import java.net.URL;
import java.util.List;

import common.po.WeiBo;

public interface WeiBoService {
	
	void saveWeiBo(WeiBo weiBo);
	void deleteAllWeiBo();
	void saveWeiBoList(List<WeiBo> weiBoList);
	List<WeiBo> findWeiBoList(int pageSize,int pageNo);
	List<WeiBo> updateWeiBoList(URL url);

}
