package common.service.Impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import weather.service.ArrayOfString;
import weather.service.WeatherWebService;
import weather.service.WeatherWebServiceSoap;
import common.po.Weather;
import common.service.WeatherService;

@Service
@Transactional
public class WeatherServiceImpl implements WeatherService{

	@PersistenceContext 
	protected EntityManager em;

	@Override
	public void saveWeather(Weather weather) {
		em.persist(weather);
	}

	@Override
	public Weather getRemoteBeijingWeather() {
        Weather weather = getRemoteWeather("54511");
        deleteAllWeather();
        saveWeather(weather);
        return weather;
	}
	
	@Override
	public Weather getRemoteWeather(String cityName) {
		WeatherWebService weatherWebService = new WeatherWebService();
        WeatherWebServiceSoap  soap = weatherWebService.getWeatherWebServiceSoap();
        ArrayOfString weatherInfo = soap.getWeatherbyCityName(cityName);
        List<String> strs = weatherInfo.getString();
        if(strs == null || strs.size()<23){
        	return new Weather();
        }
        Weather weather = new Weather(strs.get(0),strs.get(1),
        		strs.get(2),strs.get(3),strs.get(4),strs.get(5),strs.get(6),strs.get(7),strs.get(8),strs.get(9),
        		strs.get(10),strs.get(11),strs.get(12),strs.get(13),strs.get(14),strs.get(15),strs.get(16),strs.get(17),
        		strs.get(17),strs.get(19),strs.get(20),strs.get(21),strs.get(22));
        return weather;
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Weather getLocalBeijingWeather() {
		String hql="from Weather";
		Query query = em.createQuery(hql);
		if(query.getResultList() == null || query.getResultList().size() == 0){
			return null;
		}
		@SuppressWarnings("unchecked")
		List<Weather> result = query.getResultList();
		return result==null?null:result.get(0);
	}

	@Override
	public void deleteAllWeather() {
		Query query = em.createQuery("delete from Weather where 1=1");
		query.executeUpdate();
	}	

}
