package common.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import forum.po.Section;
import forum.po.Zone;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.ZoneService;
import forum.util.FileEcodeUtil;
import forum.util.ImgUtil;

@Service  
@Lazy(false) 
public class QuartzService {

	
	 @Value("#{configProperties['http.windows']}")  
	 private String windowsUrl;   
	 
	 @Value("#{configProperties['http.linux']}")  
	 private String linuxUrl;   
	 
	 @Resource(name="zoneServiceImpl")
	 private ZoneService zoneService;
		
	 @Resource(name="topicServiceImpl")
	 private TopicService topicService;
	
	 @Resource(name="sectionServiceImpl")
	 private SectionService sectionService;
	
	@Scheduled(cron = "0 */60 * * * *")//每六十分钟执行一次  
	public void reportCurrentTime() {  
		List<Zone> zoneList=zoneService.findZoneList(null, 10,0);
		
		//定时任务，计算帖子总数
		for (Zone zone : zoneList) {
			for (Section section : zone.getSectionList()) {
				
				Long totalCount=topicService.getTotalTopicCount(section.getId());			
				Long goodCount=topicService.getGoodTopicCount(section.getId());			
				Long noReplyCount=topicService.getNoReplyTopicCount(section.getId());	
				
				section.setTotalCount(totalCount);
				section.setGoodCount(goodCount);
				section.setNoReplyCount(noReplyCount);
				
				sectionService.saveSection(section);
			}
		}
		
	}  
	
	/**
	 * 定时删除在线工具生成的临时txt
	 */
	@Scheduled(cron = "0 10 2 * * ?")//每天2：10触发
	public void deleteTxtFiles() {  
		String inPath = linuxUrl+ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT+'/';
		FileEcodeUtil.deleteDirectoryChildren(inPath);
	}  
	
	/**
	 * 定时删除论坛生成的临时png
	 */
	@Scheduled(cron = "0 20 2 * * ?")//每天2：20触发
	public void deleteImgFiles() {  
		String inPath = linuxUrl+ImgUtil.TMPIMG_PATH;
		FileEcodeUtil.deleteDirectoryChildren(inPath);
	}  

}
