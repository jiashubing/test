package common.service;
import java.net.URL;
import java.util.List;

import com.sun.syndication.io.FeedException;
import common.vo.BlogVo;


public interface SubscribeService {
	List<BlogVo> parseXml(URL url) throws IllegalArgumentException,FeedException;
}
