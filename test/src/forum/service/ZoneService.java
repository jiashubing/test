package forum.service;

import java.util.List;

import forum.po.Zone;

public interface ZoneService {

	void saveZone(Zone zone);
	
	void deleteZone(Zone zone);
	
	void deleteZoneById(long id);
	
	List<Zone> findZoneList(Zone s_zone,int pageSize,int pageNo);
	
	Long getZoneCount(Zone s_zone);
	
	Zone findZoneById(long zoneId);
}
