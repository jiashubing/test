package forum.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import common.entity.Result;
import config.ValidatePcMobile;
import forum.po.Section;
import forum.po.User;
import forum.po.Zone;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.UserService;
import forum.service.ZoneService;
import forum.util.DateUtil;
import forum.util.ImgUtil;
import forum.util.PageUtil;

/**
 * 小版块管理控制器
 * @author jiashubing
 *
 */
@Controller
public class SectionAction {
	
	public static int PageSize = 6;
	public static int MaxPageSize = 100;

	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;

	@Resource(name="topicServiceImpl")
	private TopicService topicService;

	@Resource(name="sectionServiceImpl")
	private SectionService sectionService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;
	
	@RequestMapping("/admin/sectionList")
	public String loadZoneList(@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		
		pageNo = PageUtil.initPageNo(pageNo);
		User master=new User();
		master.setType(2);
		List<User> masterList=userService.findUserList(master, MaxPageSize,0);
		List<Section> sectionList = sectionService.findSectionList(null, PageSize, pageNo);
		
		List<Zone> zoneList = zoneService.findZoneList(null, MaxPageSize,0);
		String mainPage="section.html";
		
		long totalNum = sectionService.getSectionCount(null);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("mainPage",mainPage); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("masterList",masterList); 
		model.addAttribute("sectionList",sectionList); 
		model.addAttribute("zoneList",zoneList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/admin/main");
	}
	
	@RequestMapping("/admin/sectionSave")
	public String saveSection(@RequestParam(required = false) Integer pageNo,
			@RequestParam Integer secId,
			@RequestParam Integer secMasterId,
			@RequestParam Integer secZoneId,
			@RequestParam String secName,
			@RequestParam(required = false) CommonsMultipartFile logo,
			Model model,HttpServletRequest request)throws Exception{
		Section section =new Section();
		if(secId != null){
			section.setId(secId);
		}
		User user =  userService.getUserById(secMasterId);
		section.setMaster(user);
		Zone zone = zoneService.findZoneById(secZoneId);
		section.setZone(zone);
		
		section.setName(secName);
		
		//保存图片到本地，并且在数据库中赋值为路径
		if (logo!=null) {
			if(ImageIO.read(logo.getInputStream())==null){throw new Exception("上传文件不是图片！");}
			if(logo.getSize()==0){throw new Exception("文件为空！");}
			String realPath=request.getSession().getServletContext().getRealPath(ImgUtil.FORUM_PATH+ImgUtil.SECTION_FACE);
			String imgName = DateUtil.getRadomStr();
			String imgPath = "/"+imgName+ ".png";
			File file = new File(realPath+imgPath);
			try {
				logo.getFileItem().write(file);
				section.setLogo(ImgUtil.SECTION_FACE+imgPath);
			} catch (Exception e) {
				e.printStackTrace();
				section.setLogo("");
			}
		}else{
			section.setLogo("");
		}
		sectionService.saveSection(section);
		return "redirect:/admin/sectionList";
	}
//	
//	@RequestMapping("/admin/sectionDelete")
//    @ResponseBody
//    public Result loadLottery(Integer id) {
//        Result result = new Result();
//        zoneService.deleteZoneById(id);
//        result.setStatus(1);
//        return result;
//    }
	
	/**
	 * 异步 通过昵称获取用户
	 * @param nickName
	 * @return
	 */
	@RequestMapping("/admin/getUserByNickName")
    @ResponseBody
    public Result getUserByNickName(String nickName) {
        Result result = new Result();
        System.out.println(nickName);
        User user = userService.getUserByNickName(nickName);
        if(user != null){
        	result.setStatus(1);
        	result.setBody(user.getId());
        	result.setMessage("该用户的id是: "+user.getId()+"   昵称是:"+user.getNickName());
        }
        else{
        	result.setStatus(1);
        	result.setMessage("没有该用户");
        }
        return result;
    }
	
}
