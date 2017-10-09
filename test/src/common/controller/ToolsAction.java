package common.controller;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.entity.Result;
import config.ValidatePcMobile;
import forum.util.DateUtil;
import forum.util.FileEcodeUtil;
import forum.util.ImgUtil;

@Controller
public class ToolsAction {
	
	@RequestMapping(value={"/tools","/tools/one"})
	public String loadTools(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	/**
	 * 异步 将文件信息打印在前端
	 */
	@RequestMapping("/tools/loadFile")
	@ResponseBody
	public Result loadFile(@RequestParam("loadFile") MultipartFile loadFile,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+inName+".txt";
		File cin =  new File(inPath);
		loadFile.transferTo(cin);
		String ans = FileEcodeUtil.readFileByLines(inPath);
//		FileEcodeUtil.deleteFile(inPath);
		
		result.setStatus(1);
		result.setBody(ans);
		result.setMessage(inName);
		//必须加这句话
		response.setHeader("X-frame-Options", "SAMEORIGIN");
		return result;
	}
	
	/**
	 * 异步 加密
	 */
	@RequestMapping("/tools/customEncryption")
	@ResponseBody
	public Result customEncryption(@RequestParam(required = false) String inName,@RequestParam(required = false) Integer customKey,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		Result result = new Result();
		
		if(customKey == null){
			result.setStatus(0);
			result.setMessage("加密失败，密匙为空");
			return result;
		}
		if(inName == null){
			result.setStatus(0);
			result.setMessage("加密失败，文件为空");
			return result;
		}
		
		
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+inName+".txt";
		String outName = DateUtil.getRadomStr();
		String outPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+outName+".txt";
		File cin =  new File(inPath);
		File cout =  new File(outPath);
		boolean flag = FileEcodeUtil.fileEncrypt(cin,cout, customKey);
		if(!flag){
			result.setStatus(0);
			result.setMessage("加密失败，文件为空");
		}
		String endPassText = FileEcodeUtil.readFileByLines(outPath);
		//删除临时生成的文件
		FileEcodeUtil.deleteFile(inPath);
		
		result.setStatus(1);
		Map<String,String> map = new HashMap<>();
		map.put("endPassText", endPassText);
		map.put("outName", outName);
		map.put("outPath", outPath);
		result.setBody(map);
		//必须加这句话
		response.setHeader("X-frame-Options", "SAMEORIGIN");
		return result;
	}
	
	
	/**
	 * 异步 删除文件
	 */
	@RequestMapping("/tools/deleteFile")
	@ResponseBody
	public Result deleteFile(@RequestParam(required = false) String outName,HttpServletRequest request) throws Exception {
		Result result = new Result();
		
		if(outName == null || "".equals(outName)){
			result.setStatus(0);
			result.setMessage("不存在待删除文件！");
		}
		
		String outPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+outName+".txt";
		boolean flag = FileEcodeUtil.deleteFile(outPath);
		if(flag){
			result.setStatus(1);
		}
		else{
			result.setStatus(0);
			result.setMessage("删除失败！");
		}
		return result;
	}
	
//	文件不能上传后台两次，所以改了，这个方法保留，毕竟那个CommonsMultipartFile很神奇
 /* @RequestMapping("/tools/customEncryption")
	public String filepage(Model model,HttpServletRequest request
			,@RequestParam(required = false) CommonsMultipartFile loadFile
			,@RequestParam(required = false) Integer customKey,
			@RequestParam(required=false)Integer showId,
			@AuthenticationPrincipal DbUser dbUser
			)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 2;
		}
		model.addAttribute("showId",showId); 
		if(customKey == null){
			return ValidatePcMobile.checkRequest(request, "/tools");
		}
		if(loadFile == null){
			return ValidatePcMobile.checkRequest(request, "/tools");
		}
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+inName+".txt";
		String outName = DateUtil.getRadomStr();
		String outPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/"+outName+".txt";
		File cin =  new File(inPath);
		loadFile.transferTo(cin);
		File cout =  new File(outPath);
		boolean flag = FileEcodeUtil.fileEncrypt(cin,cout,(int)customKey);
		//删除临时生成的文件
		FileEcodeUtil.deleteFile(inPath);
		
		
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
*/	
	
	
	@RequestMapping("/tools/two")
	public String loadToolTwo(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		showId = 2;
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	/**
	 * 每行文本的首尾增加相同字符工具
	 */
	@RequestMapping(value={"/tools/insertString","/insertString"})
    @ResponseBody
    public Result insertString(String startText,String startChar,String endChar,
    		HttpServletRequest request) throws Exception {
        Result result = new Result();
        
        String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+inName+".txt";
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
	
	@RequestMapping(value = "/tools/printContract")
    public void cell(HttpServletResponse response,HttpServletRequest request,String outName) {
		//根据outName获取到保存在服务器上的文件
        String filePath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+outName+".txt";
        try(OutputStream out = response.getOutputStream()) {
        	Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String dateString = formatter.format(currentTime);
            //fileName是输出的文件的名字（不支持中文），包括了后缀
            String fileName = "EncryptFile_" + dateString + ".txt";
	        byte[] bytes = FileEcodeUtil.file2byte(filePath);
	        response.setContentType("application/x-msdownload");
	        response.setHeader("Content-Disposition","attachment;filename=" + fileName);
	        response.setContentLength(bytes.length);
	        out.write(bytes);
	        out.flush();
        } catch (IOException e) {
//        	e.printStackTrace();
        }
    }
	
	@RequestMapping("/tools/three")
	public String loadToolThree(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		showId = 3;
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	@RequestMapping("/tools/four")
	public String loadToolFour(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		showId = 4;
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	@RequestMapping("/tools/five")
	public String loadToolFive(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		showId = 5;
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	/**
	 * 每个段落后增加空白行工具
	 */
	@RequestMapping("/tools/insertLine")
    @ResponseBody
    public Result insertLine(String startText,HttpServletRequest request) throws Exception {
        Result result = new Result();
        
        String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+inName+".txt";
		File cin =  new File(inPath);
		String ans = "";
		boolean flag= FileEcodeUtil.writeStrToFile(startText, cin);
		
		if(flag){
			ans = FileEcodeUtil.addBlankLine(cin);
		}
		FileEcodeUtil.deleteFile(inPath);
        
        result.setStatus(1);
        result.setBody(ans);

        return result;
    }
	
	/**
	 * 删除行首到指定字符工具
	 */
	@RequestMapping("/tools/deleteString")
	@ResponseBody
	public Result deleteString(String startText,String startChar,
			HttpServletRequest request) throws Exception {
		Result result = new Result();
		
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+inName+".txt";
		File cin =  new File(inPath);
		String ans = "";
		boolean flag= FileEcodeUtil.writeStrToFile(startText, cin);
		
		if(flag){
			ans = FileEcodeUtil.deleteString(cin,startChar);
		}
		FileEcodeUtil.deleteFile(inPath);
		
		result.setStatus(1);
		result.setBody(ans);
		
		return result;
	}
	
	/**
	 * 删除所有空白行工具
	 */
	@RequestMapping("/tools/deleteLine")
	@ResponseBody
	public Result deleteLine(String startText,HttpServletRequest request) throws Exception {
		Result result = new Result();
		
		String inName = DateUtil.getRadomStr();
		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+'/'+inName+".txt";
		File cin =  new File(inPath);
		String ans = "";
		boolean flag= FileEcodeUtil.writeStrToFile(startText, cin);
		
		if(flag){
			ans = FileEcodeUtil.deleteBlankLine(cin);
		}
		FileEcodeUtil.deleteFile(inPath);
		
		result.setStatus(1);
		result.setBody(ans);
		
		return result;
	}
	
	
}
