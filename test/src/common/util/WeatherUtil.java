package common.util;

import java.util.ArrayList;
import java.util.List;

import common.po.Weather;
import common.vo.WeatherVo;

public class WeatherUtil {
	
	/**
	 * 提取Weather中的部分信息到WeatherVo
	 * @param weather
	 * @return
	 */
	public static WeatherVo weatherToWeatherVo(Weather weather){
		WeatherVo ans = new WeatherVo();
		if(weather == null){
			return ans;
		}
		if("".endsWith(weather.getCity())){
			ans.setWeatherFlag(true);
			return ans;
		}
		
		try {
//			String str = "紫外线指数：强，涂擦SPF大于15、PA+防晒护肤品。感冒指数：少发，无明显降温，感冒机率较低。穿衣指数：热，适合穿T恤、短薄外套等夏季服装。洗车指数：较适宜，无雨且风力较小，易保持清洁度。运动指数：较适宜，风力稍强，推荐您进行室内运动。空气污染指数：良，气象条件有利于空气污染物扩散。";
			String str = weather.getTodayLife();
			str = str.replaceAll("\n", "");

			String x[] = str.split("。");
			for(int i=0;i<x.length; i++){
				String a = x[i].substring(0, x[i].indexOf('：'));
				String b = x[i].substring(x[i].indexOf('：')+1, x[i].indexOf('，'));
				String c = x[i].substring(x[i].indexOf('，')+1);
				if("紫外线指数".equals(a)){
					ans.setZiwaixian1(b);
					ans.setZiwaixian2(c);
				}else if("感冒指数".equals(a)){
					ans.setGanmao1(b);
					ans.setGanmao2(c);
				}else if("穿衣指数".equals(a)){
					ans.setChuanyi1(b);
					ans.setChuanyi2(c);
				}else if("洗车指数".equals(a)){
					ans.setXiche1(b);
					ans.setXiche2(c);
				}else if("运动指数".equals(a)){
					ans.setYundong1(b);
					ans.setYundong2(c);
				}else if("空气污染指数".equals(a)){
					ans.setWuran1(b);
					ans.setWuran2(c);
				}
			}
		} catch (Exception e) {
			//do nothing
		}
		
		try {
//			String str = "今日天气实况：气温：26℃；风向/风力：西风 4级；湿度：18%；紫外线强度：强。空气质量：良。";
			String str = weather.getTodayActual();
			if(str.startsWith("今日天气实况：")){
				str = str.substring(7);
			}
			
			String x[] = str.split("；");
			List<String> y = new ArrayList<String>();
			for(int i=0;i<x.length; i++){
				if(x[i].contains("。")){
					String tmp[] = x[i].split("。");
					for(int j=0; j<tmp.length; j++){
						y.add(tmp[j]);
					}
				}else{
					y.add(x[i]);
				}
			}
			for(String s : y){
				String a = s.substring(0,s.indexOf("："));
				String b = s.substring(s.indexOf("：")+1);
				if("气温".equals(a)){
					ans.setRealTemperature(b);
				}else if("风向/风力".equals(a)){
					ans.setRealWind(b);
				}else if("湿度".equals(a)){
					ans.setRealShidu(b);
				}else if("紫外线强度".equals(a)){
					ans.setRealZiwaixian(b);
				}else if("空气质量".equals(a)){
					ans.setRealAir(b);
				}
			}
		} catch (Exception e) {
			//do nothing
		}
		
		ans.setCity(weather.getCity());
		ans.setLastUpdateDate(weather.getLastUpdateDate());
		ans.setTodayWind(weather.getTodayWind());
		ans.setTodayIconOne(weather.getTodayIconOne());
		ans.setTodayIconTwo(weather.getTodayIconTwo());
		ans.setTodaySituation(weather.getTodaySituation());
		
		return ans;
	}

}
