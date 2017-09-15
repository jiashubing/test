package common.service;

import java.util.Map;

public interface JavaToPdfService {
	
	Map<String,Object> htmlToMap(long topicId);
	void mapToPdf(Map<String,Object> map);

}
