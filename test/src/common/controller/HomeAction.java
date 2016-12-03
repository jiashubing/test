package common.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.Result;

import config.ValidatePcMobile;

@Controller
public class HomeAction {
	
	@RequestMapping("/index")
	public String loadIndex(Model model,HttpServletRequest request)throws Exception{
		 model.addAttribute("flag","index.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/index");
	}
	
	@RequestMapping("/history")
	public String loadHistory(Model model,HttpServletRequest request)throws Exception{
		model.addAttribute("flag","history.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/history");
	}
	
	@RequestMapping("/lottery")
    @ResponseBody
    public Result loadLottery() {
        Result result = new Result();
        Object[][] prizeArr = new  Object[][]{
				//id,min,max，prize【奖项】,v【中奖率】
				//里面的指针转动（没有）
				//外面的转盘转动
				{1,1,14,"恭喜你获得一等奖",1},
				{2,346,364,"恭喜你获得一等奖",1},
				{3,16,44,"不要灰心",10},
				{4,46,74,"恭喜你中了一毛钱",10},
				{5,76,104,"祝您好运",10},
				{6,106,134,"恭喜你获得二等奖",2},
				{7,136,164,"再接再厉",10},
				{8,166,194,"恭喜你中了一个亿",10},
				{9,196,224,"运气先攒着",10},
				{10,226,254,"恭喜你获得三等奖",5},
				{11,256,284,"要加油哦",10},
				{12,286,314,"恭喜你中了一千块",10},
				{13,316,344,"谢谢参与",10}
		};
        Object ans[] = award(prizeArr);//抽奖后返回角度和奖品等级
        result.setStatus(1);
        result.setBody(ans[0]);
        result.setMessage((String)ans[2]);

        return result;
    }
	
	//抽奖并返回角度和奖项
		public Object[] award(Object[][] prizeArr){
			//概率数组
			Integer obj[] = new Integer[prizeArr.length];
			for(int i=0;i<prizeArr.length;i++){
				obj[i] = (Integer) prizeArr[i][4];
			}
			Integer prizeId = getRand(obj); //根据概率获取奖项id
			//旋转角度
			int angle = new Random().nextInt((Integer)prizeArr[prizeId][2]-(Integer)prizeArr[prizeId][1])+(Integer)prizeArr[prizeId][1];
			String msg = (String) prizeArr[prizeId][3];//提示信息
			return new Object[]{angle,prizeId,msg};
		}
		//根据概率获取奖项
		public Integer getRand(Integer obj[]){
			Integer result = null;
			try {
				int  sum = 0;//概率数组的总概率精度 
				for(int i=0;i<obj.length;i++){
					sum+=obj[i];
				}
				for(int i=0;i<obj.length;i++){//概率数组循环 
					int randomNum = new Random().nextInt(sum);//随机生成1到sum的整数
					if(randomNum<obj[i]){//中奖
						result = i;
						break;
					}else{
						sum -=obj[i];
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	
}
