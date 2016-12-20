package common.controller;


import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import common.entity.Result;
import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.service.DbUserService;
import forum.util.DateUtil;
import forum.util.FileEcodeUtil;
import forum.util.ImgUtil;

@Controller
public class ToolsAction {
	
	@Resource(name="dbUserServiceImpl")
	private DbUserService dbUserService;
	
	@RequestMapping("/tools")
	public String loadTools(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	@RequestMapping("/tools/one")
	public String loadToolOne(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	/**
	 * 异步 将文件信息打印在前端
	 * @throws Exception 
	 */
	@RequestMapping("/tools/loadFile")
	@ResponseBody
	public Result loadFile(@RequestParam("loadFile") MultipartFile loadFile,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+inName+".txt";
		File cin =  new File(inPath);
		loadFile.transferTo(cin);
		String ans = FileEcodeUtil.readFileByLines(inPath);
		FileEcodeUtil.deleteFile(inPath);
		
		result.setStatus(1);
		result.setBody(ans);
		//必须加这句话
		response.setHeader("X-frame-Options", "SAMEORIGIN");
		return result;
	}
	
	/**
	 * 异步 将文件信息打印在前端
	 * @throws Exception 
	 */
	@RequestMapping("/loadFile")
	@ResponseBody
	public Result loadFile2(@RequestParam("loadFile") MultipartFile loadFile,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+inName+".txt";
		File cin =  new File(inPath);
		loadFile.transferTo(cin);
		String ans = FileEcodeUtil.readFileByLines(inPath);
		FileEcodeUtil.deleteFile(inPath);
		
		result.setStatus(1);
		result.setBody(ans);
		response.setHeader("X-frame-Options", "SAMEORIGIN");
		return result;
	}
	
	@RequestMapping("/tools/customEncryption")
	public String filepage(Model model,HttpServletRequest request
			,@RequestParam(required = false) CommonsMultipartFile loadfile
			,@RequestParam(required = false) Integer customKey
			)throws Exception{
		if(customKey == null){
			System.out.println("请输入密钥！");
			return ValidatePcMobile.checkRequest(request, "/filepage");
		}
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+inName+".txt";
		String outName = DateUtil.getRadomStr();
		String outPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+outName+".txt";
		File cin =  new File(inPath);
		loadfile.transferTo(cin);
		File cout =  new File(outPath);
		boolean flag = FileEcodeUtil.fileEncrypt(cin,cout,customKey);
		//删除临时生成的文件
		FileEcodeUtil.deleteFile(inPath);
		
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	
	@RequestMapping("/tools/two")
	public String loadToolTwo(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 2;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	/**
	 * 每行文本的首尾增加相同字符工具
	 * @param startText
	 * @param startChar
	 * @param endChar
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/tools/insertString")
    @ResponseBody
    public Result insertString(String startText,String startChar,String endChar,
    		HttpServletRequest request) throws Exception {
        Result result = new Result();
        
        String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+inName+".txt";
		File cin =  new File(inPath);
		String ans = "";
		boolean flag= FileEcodeUtil.writeStrToFile(startText, cin);
		
		if(flag){
			ans = FileEcodeUtil.modifyString(cin,startChar,endChar);
		}
		FileEcodeUtil.deleteFile(inPath);
        
        result.setStatus(1);
        result.setBody(ans);

        return result;
    }
	
	
}
