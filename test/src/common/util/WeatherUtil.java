package common.util;

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
		
		try {
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
		return ans;
	}

}
