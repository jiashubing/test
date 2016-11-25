//package shubing.study.controller;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.ui.Model;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import config.ValidatePcMobile;
//import shubing.study.po.Items;
//import shubing.study.service.ItemsService;
//
//@Controller
//public class ItermController {
//	@Resource(name="itemsServiceImpl")
//	private ItemsService itemsService;
//	
//	@RequestMapping("/item/queryItems")
//	public String queryItems(@RequestParam(required = false)Integer pageSize,@RequestParam(required = false)Integer pageNo,Model model
//			,HttpServletRequest request)throws Exception{
//		List<Items> itemsList = new ArrayList<Items>();
//		//向list中填充静态数据
//		if(pageSize == null || pageSize < 0){
//			pageSize = 10;
//		}
//		if(pageNo == null || pageNo < 0){
//			pageNo = 1;
//		}
//		
//		itemsList = itemsService.getAllItems(pageSize, pageNo);
//		
//		//相当 于request的setAttribut，在jsp页面中通过itemsList取数据
//		model.addAttribute("itemsList",itemsList);
//		model.addAttribute("pageSize",pageSize);
//		model.addAttribute("pageNo",pageNo);
//		
//		//指定视图
//		//下边的路径，如果在视图解析器中配置html路径的前缀和html路径的后缀，实际上相当于返回了"/WEB-INF/html//items/itemsList.html"
//        return ValidatePcMobile.checkRequest(request, "/items/itemsList");
//		
//	}
//	
//	@RequestMapping("/item/loadEditItem")
//	public String loadEditItem(@RequestParam(required = false)Integer id,Model model,HttpServletRequest request)throws Exception{
//		System.out.println("id = "+id);
//		Items ans = itemsService.getById(id);
//		model.addAttribute("item", ans);
//		return ValidatePcMobile.checkRequest(request, "/items/itemEdit");
//	}
//	
//	@RequestMapping("/item/editItem")
//	public String editItem(Integer id,Model model,HttpServletRequest request
//			,@RequestParam(required = false)String name
//			,@RequestParam(required = false)Float price
//			,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createtime
//			,@RequestParam(required = false)String detail)throws Exception{
//		Items ans = itemsService.getById(id);
//		if(name != null){
//			ans.setName(name);
//		}
//		if(price != null){
//			ans.setPrice(price);
//		}
//		if(createtime != null){
//			ans.setCreatetime(createtime);
//		}
//		if(detail != null){
//			ans.setDetail(detail);
//		}
//		itemsService.merge(ans);
//		model.addAttribute("item", ans);
//		return ValidatePcMobile.checkRequest(request, "/items/itemEdit");
//	}
//
//}
