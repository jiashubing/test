package common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sudoku.business.GenerationAlgorithm;
import config.ValidatePcMobile;

@Controller
public class GamesAction {
	
	@RequestMapping("/games")
	public String loadTools(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(showId == null){
			showId = 1;
		}
		GenerationAlgorithm generationAlgorithm = new GenerationAlgorithm();
		model.addAttribute("puzzle", generationAlgorithm.getPuzzle());
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","games.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/games");
	}
	
	@RequestMapping("/games/sudoku")
	public String loadToolOne(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","games.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/games");
	}
	
	
}
