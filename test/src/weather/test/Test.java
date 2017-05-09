package weather.test;

import weather.service.ArrayOfString;
import weather.service.WeatherWebService;
import weather.service.WeatherWebServiceSoap;


public class Test {

	public static void main(String[] args) {
		WeatherWebService weatherWebService = new WeatherWebService();
        WeatherWebServiceSoap  soap = weatherWebService.getWeatherWebServiceSoap();
        System.out.println("--------weatherInfo--------");
        ArrayOfString weatherInfo = soap.getWeatherbyCityName("北京");
        for(String str : weatherInfo.getString()) {
            System.out.println("weatherInfo: " + str);
        }

	}

}
