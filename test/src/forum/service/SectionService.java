package forum.service;

import java.util.List;

import forum.po.Section;

public interface SectionService {

	void saveSection(Section section);
	
	void deleteSection(Section section);
	void deleteSectionById(long sectionId);
	
//	List<Section> findSectionList(Section s_section,PageBean pageBean);
	List<Section> findSectionList(Section s_section,int pageSize,int pageNo);
	
	List<Section> findSectionListByZoneId(long zoneId,int pageSize,int pageNo);
	
	Long getSectionCount(Section s_section);
	
	Section findSectionById(long sectionId);
	
}
