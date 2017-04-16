package common.service;
import java.net.URL;
import java.util.List;

import com.sun.syndication.io.FeedException;
import common.vo.DingYueVo;


public interface SubscribeService {
	public List<DingYueVo> parseXml(URL url) throws IllegalArgumentException,FeedException;
}
