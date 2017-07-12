package common.controller;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import common.entity.Result;
import common.po.Opinion;
import common.po.Weather;
import common.service.OpinionService;
import common.service.WeatherService;
import common.util.WeatherUtil;
import common.vo.WeatherVo;
import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.po.Topic;
import forum.po.User;
import forum.service.DbUserService;
import forum.service.TopicService;
import forum.service.UserService;
import forum.util.DateUtil;
import forum.util.ImgUtil;
import forum.util.Md5Util;

@Controller
public class HomeAction {
	
	@Resource(name="dbUserServiceImpl")
	private DbUserService dbUserService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	@Resource(name="opinionServiceImpl")
	private OpinionService opinionService;
	
	@Resource(name="topicServiceImpl")
	private TopicService topicService;
	
	@Resource(name="weatherServiceImpl")
	private WeatherService weatherService;
	
	@RequestMapping("/index")
	public String loadIndex(Model model,HttpServletRequest request)throws Exception{
		/*HttpSession session = request.getSession();
		if(session.getAttribute("dbUser") != null){
			DbUser dbUser =(DbUser)session.getAttribute("dbUser");
			//如果是我的管理员账号登陆，那就跳转到后台
			if("myadmin".equals(dbUser.getUsername())){
				return "redirect:/admin";
			}
		}*/
		Weather weather = weatherService.getLocalBeijingWeather();
		if(weather == null){
			weather = weatherService.getRemoteBeijingWeather();
		}
		if(ValidatePcMobile.checkRequest(request)){
			List<Topic> goodTopicList = topicService.findGoodTopicListDesc(7, 0);
			WeatherVo weatherVo = WeatherUtil.weatherToWeatherVo(weather);
			model.addAttribute("weatherVo",weatherVo);
			model.addAttribute("goodTopicList",goodTopicList);
		}else{
			model.addAttribute("weatherIcon",weather.getTodayIconOne());
		}
		model.addAttribute("flag","index.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/index");
	}
	
	@RequestMapping("/history")
	public String loadHistory(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		model.addAttribute("flag","history.html");  //此属性用来给前台确定当前是哪个页面
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		return ValidatePcMobile.checkRequest(request, "/history");
	}
	
	@RequestMapping("/happy")
	public String loadHappy(Model model,HttpServletRequest request)throws Exception{
		model.addAttribute("flag","happy.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/happy");
	}
	
	@RequestMapping("/happy/hahaha")
	public String loadHahaha(Model model,HttpServletRequest request)throws Exception{
		model.addAttribute("flag","happy.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/happys/hahaha");
	}
	
	@RequestMapping("/video")
	public String loadVedio(Model model,HttpServletRequest request)throws Exception{
		model.addAttribute("flag","video.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/video");
	}
	
	@RequestMapping("/regist")
	public String loadRegist(@AuthenticationPrincipal DbUser dbUser,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			return "redirect:/index";
		}
		
		//返回上一页的路径，赋值到页面中
		String prePage = request.getHeader("Referer");
		if(prePage != null && !"".equals(prePage) && !prePage.contains("/do")){
			model.addAttribute("prePage", prePage);
		}else{
			String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
			model.addAttribute("prePage", tmpPage);
		}
		
		model.addAttribute("flag","regist.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/regist");
	}
	
	/**
	 * 异步 校验昵称
	 */
	@RequestMapping("/checkUserName")
	@ResponseBody
	public Result checkUserName(String name) {
		Result result = new Result();
		boolean flag = dbUserService.checkUserName(name);
		if(flag == true){
			result.setStatus(1);
		}else{
			result.setStatus(0);
			result.setMessage("该昵称已经存在，请使用其他昵称");
		}
		return result;
	}
	
	/**
	 * 异步 校验邮箱
	 */
	@RequestMapping("/checkEmail")
	@ResponseBody
	public Result checkEmail(String email) {
		Result result = new Result();
		boolean flag = userService.checkEmail(email);
		if(flag == true){
			result.setStatus(1);
		}else{
			result.setStatus(0);
			result.setMessage("该邮箱已经存在，请使用其他邮箱");
		}
		return result;
	}
	
	@RequestMapping(value="/doRegist")
	public String doRegist(Model model,HttpServletRequest request,
			@RequestParam String nickName,
			@RequestParam(required = false) String trueName,
			@RequestParam String email,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) Integer sex,
			@RequestParam(required = false) CommonsMultipartFile face,
			@RequestParam String newPwd )throws Exception{
		
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("dbUser")!=null){
			return "redirect:/index";
		}
		
		if(!userService.checkEmail(email)){
			if(ValidatePcMobile.checkRequest(request)){
				model.addAttribute("flag","regist.html");  //此属性用来给前台确定当前是哪个页面
				return "/pc/registsuccess";
			}else{
				model.addAttribute("msgInfo","您已经注册了");
				return "/mobile/person";
			}
		}
		
	    DbUser tmpDbUser = new DbUser();
		User tmpUser = new User();

		//保存图片到本地，并且在数据库中赋值为路径
		if (face!=null && face.getSize()!=0) {
			InputStream tmpStream = null;
			try {
				tmpStream = face.getInputStream();
				if(ImageIO.read(tmpStream)==null){throw new Exception("上传文件不是图片！");}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally{
				if(tmpStream != null){
					tmpStream.close();
				}
			}
			String realPath=request.getSession().getServletContext().getRealPath(ImgUtil.FORUM_PATH+ImgUtil.USER_FACE);
			String imgName = DateUtil.getRadomStr();
			String imgPath = "/"+imgName+ ".png";
			File file = new File(realPath+imgPath);
			try {
				face.getFileItem().write(file);
				tmpUser.setFace(ImgUtil.USER_FACE+imgPath);
			} catch (Exception e) {
				e.printStackTrace();
				tmpUser.setFace("");
			}
		}else{
			tmpUser.setFace("");
		}
	        
		tmpDbUser.setUsername(nickName);
		tmpUser.setPassword(newPwd);
		//密码需要md5加密
		newPwd = Md5Util.toMD5(newPwd);
		tmpDbUser.setPassword(newPwd);
		tmpDbUser.setAccess(0);

		if(sex!=null && sex==1){
			tmpUser.setSex("男");
		}else if(sex!=null && sex==2){
			tmpUser.setSex("女");
		}else{
			tmpUser.setSex("未知");
		}
		tmpUser.setNickName(nickName);
		tmpUser.setType(0);
		tmpUser.setMobile(mobile);
		tmpUser.setEmail(email);
		tmpUser.setTrueName(trueName);

		//一对一关联映射的时候需要这么取值
		tmpDbUser.setUser(tmpUser);
		tmpUser.setDbUser(tmpDbUser);

		dbUserService.save(tmpDbUser);
		
//		DbUser dbUser = dbUserService.getByName(nickName);
		DbUser dbUser = tmpDbUser;
		
		
		//注册后直接登陆
        PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(dbUser, dbUser.getPassword(),dbUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		if(session != null){
			session.removeAttribute("dbUser");
			session.setAttribute("dbUser",dbUser);
		} 
		
		//发送邮件
//		SendMailThread ss = new SendMailThread(nickName,email);
//		ss.start();
		
		if(ValidatePcMobile.checkRequest(request)){
			model.addAttribute("flag","regist.html");  //此属性用来给前台确定当前是哪个页面
			model.addAttribute("successFlag", 0);
			return "/pc/registsuccess";
		}else{
			model.addAttribute("msgInfo","感谢您的注册");
			return "/mobile/person";
		}
	}
	
	@RequestMapping(value="/doUpdate")
	public String doUpdate(@AuthenticationPrincipal DbUser dbUser,Model model,HttpServletRequest request,
			@RequestParam(required = false) String trueName,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) Integer sex,
			@RequestParam(required = false) CommonsMultipartFile face)throws Exception{
		
		User tmpUser = dbUser.getUser();
		
		//保存图片到本地，并且在数据库中赋值为路径
		if (face!=null && face.getSize()!=0) {
			InputStream tmpStream = null;
			try {
				tmpStream = face.getInputStream();
				if(ImageIO.read(tmpStream)==null){throw new Exception("上传文件不是图片！");}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally{
				if(tmpStream != null){
					tmpStream.close();
				}
			}
			String realPath=request.getSession().getServletContext().getRealPath(ImgUtil.FORUM_PATH+ImgUtil.USER_FACE);
			String imgName = DateUtil.getRadomStr();
			String imgPath = "/"+imgName+ ".png";
			File file = new File(realPath+imgPath);
			try {
				face.getFileItem().write(file);
				tmpUser.setFace(ImgUtil.USER_FACE+imgPath);
			} catch (Exception e) {
				e.printStackTrace();
				tmpUser.setFace("");
			}
		}
		
		if(sex!=null && sex==1){
			tmpUser.setSex("男");
		}else if(sex!=null && sex==2){
			tmpUser.setSex("女");
		}else{
			tmpUser.setSex("未知");
		}
		tmpUser.setMobile(mobile);
		tmpUser.setTrueName(trueName);
		
		dbUserService.merge(dbUser);
		
		HttpSession session = request.getSession();
		session.setAttribute("dbUser", dbUser);
		
		model.addAttribute("successFlag", 1);
		return ValidatePcMobile.checkRequest(request, "/registsuccess");
	}
	

	@RequestMapping("/changePassword")
	@ResponseBody
	public Result changePassword(HttpServletRequest request,String oldPwd,String newPwd) {
		Result result = new Result();
		HttpSession session = request.getSession(false);
		if(session != null){
			DbUser dbUser = (DbUser)session.getAttribute("dbUser");
			User user = dbUser.getUser();
			if(!oldPwd.equals(user.getPassword())){
				result.setStatus(0);
				result.setMessage("原密码错误！");
				return result;
			}
			user.setPassword(newPwd);
			newPwd = Md5Util.toMD5(newPwd);
			dbUser.setPassword(newPwd);
			dbUserService.merge(dbUser);
			result.setStatus(1);
			result.setMessage("修改密码成功！");
		} else{
			result.setStatus(0);
			result.setMessage("修改密码失败！");
		}
		return result;
	}
	
	@RequestMapping("/lottery")
    @ResponseBody
    public Result loadLottery() {
        Result result = new Result();
        Object[][] prizeArr = new  Object[][]{
				//id,min,max，prize【奖项】,v【中奖率】
				//里面的指针转动（没有）
				//外面的转盘转动
				{1,1,14,"恭喜你获得一等奖",0},
				{2,346,364,"恭喜你获得一等奖",0},
				{3,16,44,"不要灰心",10},
				{4,46,74,"恭喜你中了一毛钱",20},
				{5,76,104,"祝您好运",10},
				{6,106,134,"恭喜你获得二等奖",0},
				{7,136,164,"再接再厉",10},
				{8,166,194,"恭喜你中了一个亿",0},
				{9,196,224,"运气先攒着",10},
				{10,226,254,"恭喜你获得三等奖",20},
				{11,256,284,"要加油哦",10},
				{12,286,314,"恭喜你中了一千块",0},
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
	
	 /**
	  * 跳转到adminpage页面
	  * @return
	  *//*
	 @RequestMapping("/admin")
	 public String getAdminPage(HttpServletRequest request){
		 return ValidatePcMobile.checkRequest(request, "/adminpage");
	 }*/
	
	 /**
	  * 跳转到admin控制台页面
	  * @return
	  */
	 @RequestMapping("/admin")
	 public String getAdminPage(HttpServletRequest request){
		return ValidatePcMobile.checkRequest(request, "/admin/main");
	 }
	 
	 /**
	  * 跳转到意见反馈页面
	  * @return
	  */
	 @RequestMapping("/feedback")
	 public String getFeedback(ModelMap model, HttpServletRequest request){
		//返回上一页的路径，赋值到页面中
		String prePage = request.getHeader("Referer");
		if(prePage != null && !"".equals(prePage) && !prePage.contains("/do")){
			model.addAttribute("prePage", prePage);
		}else{
			String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
			model.addAttribute("prePage", tmpPage);
		}
		 return ValidatePcMobile.checkRequest(request, "/feedback");
	 }
	 
	 /**
	  * 增加意见反馈页面
	  * @return
	  */
	 @RequestMapping("/doFeedback")
	 public String doFeedback(@AuthenticationPrincipal DbUser dbUser,@RequestParam Integer type,
			 @RequestParam String content,
			 @RequestParam(required = false) String email, ModelMap model, HttpServletRequest request){
		 
		 Opinion opinion = new Opinion();
		 opinion.setType(type);
		 opinion.setContent(content);
		 opinion.setEmail(email);
		 opinionService.saveOpinion(opinion);
		 
		 if(ValidatePcMobile.checkRequest(request)){
			model.addAttribute("successFlag", 2);
			return "/pc/registsuccess";
		}else{
			model.addAttribute("msgInfo","感谢您的反馈");
			if(dbUser != null){
				return "/mobile/person";
			}else{
				return "/mobile/beforelogin";
			}
		}
		 
	 }
	 
	 /**
	  * 跳转到基本信息页面
	  */
	 @RequestMapping("/basicinfo")
	 public String basicinfo(HttpServletRequest request){
		 return ValidatePcMobile.checkRequest(request, "/basicinfo");
	 }
	 
	 /**
	  * 跳转到修改基本信息页面
	  */
	 @RequestMapping("/updateinfo")
	 public String updateinfo(@AuthenticationPrincipal DbUser dbUser,HttpServletRequest request,Model model){
		 model.addAttribute("dbUser", dbUser);
		 return ValidatePcMobile.checkRequest(request, "/updateinfo");
	 }
	 
	 /**
	  * 跳转到修改密码页面
	  */
	 @RequestMapping("/updatepassword")
	 public String updatepassword(@AuthenticationPrincipal DbUser dbUser,HttpServletRequest request,Model model){
		 model.addAttribute("dbUser", dbUser);
		 
		//返回上一页的路径，赋值到页面中
		String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
		model.addAttribute("prePage", tmpPage);
		 
		 return ValidatePcMobile.checkRequest(request, "/updatepassword");
	 }
	 
	 @RequestMapping("/registsu")
	 public String registsu(Model model,HttpServletRequest request){
		 model.addAttribute("msgInfo","感谢您的注册");
		 return ValidatePcMobile.checkRequest(request, "/person");
	 }
	 
	 /**
	  * 跳转到简历页面
	  */
	 @RequestMapping("/resume")
	 public String resume(HttpServletRequest request){
		 return ValidatePcMobile.checkRequest(request, "/resume");
	 }
	 
	 /**
	  * 跳转到打赏页面
	  */
	 @RequestMapping("/shang")
	 public String shang(HttpServletRequest request){
		 return ValidatePcMobile.checkRequest(request, "/shang");
	 }
	
}
