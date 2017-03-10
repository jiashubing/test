package forum.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.Result;
import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.po.Reply;
import forum.po.Section;
import forum.po.Topic;
import forum.po.User;
import forum.service.ReplyService;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.UserService;
import forum.service.ZoneService;
import forum.util.FileEcodeUtil;
import forum.util.HtmlUtil;
import forum.util.PageUtil;

@Controller
public class TopicAction {
	
	public static int PageSize = 10;
	public static int MaxPageSize = 100;

	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;

	@Resource(name="topicServiceImpl")
	private TopicService topicService;

	@Resource(name="sectionServiceImpl")
	private SectionService sectionService;
	
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;

	@RequestMapping("/forum/topicList")
	public String loadTopicList(Integer sectionId,@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		
		pageNo = PageUtil.initPageNo(pageNo);
		
		Section section=sectionService.findSectionById(sectionId);
		List<Topic> zdTopicList=topicService.findZdTopicListBySectionId(sectionId,PageSize,0);
		List<Topic> ptTopicList=topicService.findPtTopicListBySectionId(sectionId,PageSize,pageNo);
		
		Map<Topic, Reply> topicLastReply=new HashMap<Topic, Reply>(0);
		Map<Topic, Long> topicReplyCount=new HashMap<Topic, Long>(0);
		for(int i=0;i<zdTopicList.size();i++){
			Topic topic = zdTopicList.get(i);
			Reply reply=replyService.findLastReplyByTopicId(topic.getId());
			Long replyCount=replyService.getReplyCountByTopicId(topic.getId());
			if (reply!=null) {
				topicLastReply.put(topic, reply);
			}
			topicReplyCount.put(topic, replyCount);
		}
		
		for(int i=0;i<ptTopicList.size();i++){
			Topic topic = ptTopicList.get(i);
			Reply reply=replyService.findLastReplyByTopicId(topic.getId());
			Long replyCount=replyService.getReplyCountByTopicId(topic.getId());
			if (reply!=null) {
				topicLastReply.put(topic, reply);
			}
			topicReplyCount.put(topic, replyCount);
		}
		
		long totalNum=topicService.getPtTopicCountBySectionId(sectionId);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("section",section); 
		model.addAttribute("zdTopicList",zdTopicList); 
		model.addAttribute("ptTopicList",ptTopicList); 
		model.addAttribute("topicLastReply",topicLastReply); 
		model.addAttribute("topicReplyCount",topicReplyCount); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		model.addAttribute("progressFlag",2); //导航条显示标志
		
		if(!ValidatePcMobile.checkRequest(request)){
			//返回上一页的路径，赋值到页面中
			String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
			model.addAttribute("prePage", tmpPage);
		}
				
		return ValidatePcMobile.checkRequest(request, "/forum/topicList");
	}
	
	/**
	 * 最新的帖子
	 */
	@RequestMapping("/forum/newsTopicList")
	public String loadNewsTopicList(@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		pageNo = PageUtil.initPageNo(pageNo);
		List<Topic> newTopicList = topicService.findNewTopicListDesc(PageSize, pageNo);
		long totalNum=topicService.getAllTopicCount();
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("newTopicList",newTopicList); 
		
		if(!ValidatePcMobile.checkRequest(request)){
			//手机端 返回上一页的路径，赋值到页面中
			String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
			model.addAttribute("prePage", tmpPage);
		}else{
			Map<Topic, Reply> topicLastReply=new HashMap<Topic, Reply>(0);
			Map<Topic, Long> topicReplyCount=new HashMap<Topic, Long>(0);
			for(int i=0;i<newTopicList.size();i++){
				Topic topic = newTopicList.get(i);
				Reply reply=replyService.findLastReplyByTopicId(topic.getId());
				Long replyCount=replyService.getReplyCountByTopicId(topic.getId());
				if (reply!=null) {
					topicLastReply.put(topic, reply);
				}
				topicReplyCount.put(topic, replyCount);
			}
			
			for(int i=0;i<newTopicList.size();i++){
				Topic topic = newTopicList.get(i);
				Reply reply=replyService.findLastReplyByTopicId(topic.getId());
				Long replyCount=replyService.getReplyCountByTopicId(topic.getId());
				if (reply!=null) {
					topicLastReply.put(topic, reply);
				}
				topicReplyCount.put(topic, replyCount);
			}
			model.addAttribute("topicLastReply",topicLastReply); 
			model.addAttribute("topicReplyCount",topicReplyCount); 
			model.addAttribute("progressFlag",3); //导航条显示标志
		}
		
		return ValidatePcMobile.checkRequest(request, "/forum/newsTopicList");
	}
	
	/**
	 * 后台加载帖子列表
	 */
	@RequestMapping("/admin/topicList")
	public String loadAdminTopicList(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) String s_title,
			@RequestParam(required = false) String s_userNickName,
			@RequestParam(required = false) Integer s_sectionId,
			@RequestParam(required = false) Integer s_top,
			@RequestParam(required = false) Integer s_good,
			Model model,HttpServletRequest request)throws Exception{
		
		pageNo = PageUtil.initPageNo(pageNo);
		
		Topic s_topic = new Topic();
		if(s_title != null){
			s_topic.setTitle(s_title);
		}
		if(s_userNickName != null){
			User tmp = userService.getUserByNickName(s_userNickName);
			s_topic.setUser(tmp);
		}
		if(s_sectionId != null){
			Section tmp = sectionService.findSectionById(s_sectionId);
			s_topic.setSection(tmp);
		}
		if(s_top != null){
			s_topic.setTop(s_top);
		}else{
			//必填项，设成2不查询
			s_topic.setTop(2);
		}
		if(s_good != null){
			s_topic.setGood(s_good);
		}else{
			//必填项，设成2不查询
			s_topic.setGood(2);
		}
		
		List<Topic> topicList = topicService.findTopicList(s_topic,PageSize,pageNo);
		Long totalNum = topicService.getTopicCount(s_topic);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		List<Section> sectionList = sectionService.findSectionList(null,MaxPageSize,pageNo);
		
		String mainPage="topic.html";
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("mainPage",mainPage); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("s_topic",s_topic); 
		model.addAttribute("topicList",topicList); 
		model.addAttribute("sectionList",sectionList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/admin/main");
	}
	
	/**
	 * 管理员页面 异步 删除topic
	 */
	@RequestMapping("/admin/topicDelete")
    @ResponseBody
    public Result deleteTopicAdmin(Integer topicId,HttpServletRequest request) {
        Result result = new Result();
        
        //更新小版块对应的数目
        Topic tmp = topicService.findTopicById(topicId);
        Section section = tmp.getSection();
        
        //先删除和帖子一起的回复的对应的图片
        List<Reply> list = replyService.findReplyListByTopicId(topicId, MaxPageSize, 0);
        for(int k =0; k<list.size(); k++){
        	Reply reply = list.get(k);
        	//删除之前数据之前，也要删除图片
    		//提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
    		String[] url = reply.getContent().split("/");
            String imgName;
            for (int i = 0 ; i <url.length ; i++ ) {
                if(url[i].contains(".png")){
                    imgName = url[i].substring(0,40);
                    String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
                    String outName=realOutPath + '/' + imgName;
                    //删除图片
                    FileEcodeUtil.deleteFile(outName);
                }
            }
        }
        
        //删除数据之前，也要删除图片
        //提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
        String[] url = tmp.getContent().split("/");
        String imgName;
        for (int i = 0 ; i <url.length ; i++ ) {
        	if(url[i].contains(".png")){
        		imgName = url[i].substring(0,40);
        		String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
        		String outName=realOutPath + '/' + imgName;
        		//删除图片
        		FileEcodeUtil.deleteFile(outName);
        	}
        }
        
        topicService.deleteTopicById(topicId);
        
		Long totalCount=topicService.getTotalTopicCount(section.getId());			
		Long goodCount=topicService.getGoodTopicCount(section.getId());			
		Long noReplyCount=topicService.getNoReplyTopicCount(section.getId());	
		
		section.setTotalCount(totalCount);
		section.setGoodCount(goodCount);
		section.setNoReplyCount(noReplyCount);
		sectionService.saveSection(section);
		
		
        result.setStatus(1);
        return result;
    }
	
	/**
	 * 论坛页面 异步 删除topic
	 */
	@RequestMapping("/forum/topicDelete")
	@ResponseBody
	public Result deleteTopicForum(Integer topicId,HttpServletRequest request) {
		Result result = new Result();
		
		//更新小版块对应的数目
        Topic tmp = topicService.findTopicById(topicId);
        Section section = tmp.getSection();
        
        //先删除和帖子一起的回复的对应的图片
        List<Reply> list = replyService.findReplyListByTopicId(topicId, MaxPageSize, 0);
        for(int k =0; k<list.size(); k++){
        	Reply reply = list.get(k);
        	//删除之前数据之前，也要删除图片
    		//提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
    		String[] url = reply.getContent().split("/");
            String imgName;
            for (int i = 0 ; i <url.length ; i++ ) {
                if(url[i].contains(".png")){
                    imgName = url[i].substring(0,40);
                    String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
                    String outName=realOutPath + '/' + imgName;
                    //删除图片
                    FileEcodeUtil.deleteFile(outName);
                }
            }
        }
        
        //删除数据之前，也要删除图片
        //提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
        String[] url = tmp.getContent().split("/");
        String imgName;
        for (int i = 0 ; i <url.length ; i++ ) {
        	if(url[i].contains(".png")){
        		imgName = url[i].substring(0,40);
        		String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
        		String outName=realOutPath + '/' + imgName;
        		//删除图片
        		FileEcodeUtil.deleteFile(outName);
        	}
        }
        
        topicService.deleteTopicById(topicId);
        
		Long totalCount=topicService.getTotalTopicCount(section.getId());			
		Long goodCount=topicService.getGoodTopicCount(section.getId());			
		Long noReplyCount=topicService.getNoReplyTopicCount(section.getId());			
		section.setTotalCount(totalCount);
		section.setGoodCount(goodCount);
		section.setNoReplyCount(noReplyCount);
		sectionService.saveSection(section);
		
		result.setStatus(1);
		return result;
	}
	
	@RequestMapping("/forum/details")
	public String loadDetails(Integer id,Model model,@RequestParam(required = false) Integer pageNo,@RequestParam(required = false) boolean errorFlag,
			@RequestParam(required = false) Integer addTopicFlag,HttpServletRequest request)throws Exception{
		
		Topic topic = topicService.findTopicById(id);
		pageNo = PageUtil.initPageNo(pageNo);
		List<Reply> replyList=replyService.findReplyListByTopicId(id, PageSize,pageNo);
		Long totalNum =replyService.getReplyCountByTopicId(id);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("topic",topic); 
		model.addAttribute("replyList",replyList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		model.addAttribute("progressFlag",5); //导航条显示标志
		
		//手机端发表帖子后返回到帖子详情，帖子详情的“返回上一页”按钮不能返回到发帖页面
		model.addAttribute("addTopicFlag",addTopicFlag);
		
		if(!ValidatePcMobile.checkRequest(request)){
			//返回上一页的路径，赋值到页面中
			String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
			tmpPage += "/topicList?sectionId="+topic.getSection().getId();
			model.addAttribute("prePage", tmpPage);
		}
		
		if(errorFlag){
			//手机端抛出异常
			model.addAttribute("errorFlag",errorFlag); 
		}
		return ValidatePcMobile.checkRequest(request, "/forum/topicDetail");
	}
	
	@RequestMapping("/forum/preSave")
	public String preSave(Model model,HttpServletRequest request)throws Exception{
		//这个路径不使用安全登陆框架了，手动添加跳转
		HttpSession session = request.getSession(false);
		DbUser tmpUser = null;
		if(session!=null){
			tmpUser = (DbUser)session.getAttribute("dbUser");
			if(tmpUser == null){
				session.setAttribute("addTopicFlag", true);
				return "redirect:/login";
			}
		}
		
		List<Section> sectionList = sectionService.findSectionList(null, MaxPageSize, 0);
		if(tmpUser!=null && tmpUser.getAccess()!=1){
			sectionList.remove(0);
		}
		
		model.addAttribute("sectionList",sectionList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		model.addAttribute("progressFlag",4); //导航条显示标志
		
		if(!ValidatePcMobile.checkRequest(request)){
			//返回上一页的路径，赋值到页面中
			String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
			model.addAttribute("prePage", tmpPage);
		}
		
		return ValidatePcMobile.checkRequest(request, "/forum/topicAdd");
	}
	
	@RequestMapping("/forum/topicSave")
	public String saveTopic(
			@RequestParam(required = false) String topicTitle,
			@RequestParam(required = false) String topicContent,
			@RequestParam(required = false) Integer topicSectionId,
			Model model,HttpServletRequest request)throws Exception{
		Topic topic = new Topic();
		DbUser dbUser = null;
		HttpSession session = request.getSession();
		if(session.getAttribute("dbUser")!=null){
			dbUser = (DbUser)session.getAttribute("dbUser");
			topic.setUser(dbUser.getUser());
		}else{
			return "redirect:/login";
		}
		
		try{
		//System.out.println("before : "+topicContent);		//首先检测上传内容中的图片
		//提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
		String[] url = topicContent.split("/");
        String imgName;
        StringBuffer buf = new StringBuffer();
        boolean tmpFlag = true; //判断是否有一张图片，只取第一张图片
        int i = 0;
        for (i = 0 ; i <url.length ; i++ ) {
            if(url[i].contains(".png")){
                imgName = url[i].substring(0,40);
                //System.out.println("imgName = "+imgName);
                if(tmpFlag){
                	topic.setFirstimg(imgName);
                	tmpFlag = false;
                }
                String realInPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/tmpImg");
                String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
                String inName=realInPath + '/' + imgName;
                String outName=realOutPath + '/' + imgName;
                boolean flag = FileEcodeUtil.fileRemove(inName, outName);
                //如果移动成功了，应该把内容中的路径修改，替换最后一个'tmpImg'为'reaImg'
                if(flag){
                	url[i-1]="reaImg";
                }
            }
            if(i>0){
            	buf.append(url[i-1]).append('/');
            }
        }
        if(i>0){
        	buf.append(url[i-1]);
        }
        //System.out.println("after : "+buf.toString());	//这就是实际应当报错到数据库里的内容
		
		topic.setTitle(topicTitle);
		topic.setContent(buf.toString());
		
		//保存摘要
		String remark = HtmlUtil.getTextFromHtml(topicContent);
		int tmp = remark.length();
		remark = remark.substring(0, tmp<200?tmp:200);
		topic.setRemark(remark);
		
		Section section = sectionService.findSectionById(topicSectionId);
		if(section == null){
			throw new Exception("该帖子没有对应的小版块，这表示了一个错误。");
		}
		topic.setSection(section);
		
		topic.setPublishTime(new Date());
		topic.setModifyTime(new Date());
		
		topic = topicService.saveTopic2(topic);
		
		//更新小版块对应的数目
		Long totalCount=topicService.getTotalTopicCount(section.getId());			
		Long noReplyCount=topicService.getNoReplyTopicCount(section.getId());			
		section.setTotalCount(totalCount);
		section.setNoReplyCount(noReplyCount);
		sectionService.saveSection(section);
		
		return "redirect:/forum/details?id="+topic.getId()+"&addTopicFlag=1";
		}catch(Exception e){
			//TODO
			List<Section> sectionList = sectionService.findSectionList(null, MaxPageSize, 0);
			if(dbUser!=null && dbUser.getAccess()!=1){
				sectionList.remove(0);
			}
			model.addAttribute("sectionList",sectionList); 
			model.addAttribute("errorFlag",1);
			model.addAttribute("topicTitle",topicTitle);
			model.addAttribute("topicContent",topicContent);
			model.addAttribute("topicSectionId",topicSectionId);
			return ValidatePcMobile.checkRequest(request, "/forum/topicAdd");
		}
	}
	
	@RequestMapping("/forum/topicUpdate")
	public String updateToipc(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer topicId,
			@RequestParam(required = false) Integer topicGood,
			@RequestParam(required = false) Integer topicTop,
			@RequestParam(required = false) Integer sectionId,
			@RequestParam(required = false) Integer newTopicFlag
			)throws Exception{
		
		Topic topic = new Topic();
		if(topicId!=null){
			topic = topicService.findTopicById(topicId);
		}
		if(topicGood != null){
			topic.setGood(topicGood);
		}
		if(topicTop != null){
			topic.setTop(topicTop);
		}
		topicService.saveTopic(topic);
		
		//更新小版块对应的数目
		Section section = topic.getSection();
		Long goodCount=topicService.getGoodTopicCount(section.getId());			
		Long noReplyCount=topicService.getNoReplyTopicCount(section.getId());			
		section.setGoodCount(goodCount);
		section.setNoReplyCount(noReplyCount);
		sectionService.saveSection(section);
		
		//重定向时传递参数
		if(newTopicFlag != null){
			return "redirect:/forum/newsTopicList?pageNo="+pageNo;
		}
		if(sectionId != null){
			return "redirect:/forum/topicList?sectionId="+sectionId+"&pageNo="+pageNo;
		}
		return "redirect:/forum/topicList?pageNo="+pageNo;
		
	}
	
	
	
	
}
