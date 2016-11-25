package forum.service;

import java.util.List;

import forum.po.Section;

public interface SectionService {

	public void saveSection(Section section);
	
	public void deleteSection(Section section);
	
//	public List<Section> findSectionList(Section s_section,PageBean pageBean);
	public List<Section> findSectionList(Section s_section,int pageSize,int pageNo);
	
	public Long getSectionCount(Section s_section);
	
	public Section findSectionById(int sectionId);
	
}
