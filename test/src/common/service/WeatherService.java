package common.service;

import common.po.Weather;

public interface WeatherService {
	
	void deleteAllWeather();
	void saveWeather(Weather weather);
	Weather getRemoteBeijingWeather();
	Weather getRemoteWeather(String cityName);
	Weather getLocalBeijingWeather();
	
}
