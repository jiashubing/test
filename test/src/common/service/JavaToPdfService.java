package common.service;

import java.util.Map;

public interface JavaToPdfService {
	
	Map<String,Object> htmlToMap(long topicId);
	String mapToPdf(Map<String,Object> map);

}
