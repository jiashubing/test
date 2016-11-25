package forum.service;

import java.util.List;

import forum.po.Zone;

public interface ZoneService {

	public void saveZone(Zone zone);
	
	public void deleteZone(Zone zone);
	
	public List<Zone> findZoneList(Zone s_zone,int pageSize,int pageNo);
	
	public Long getZoneCount(Zone s_zone);
	
	public Zone findZoneById(int zoneId);
}
