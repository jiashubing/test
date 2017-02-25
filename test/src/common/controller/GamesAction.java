package common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.Result;
import sudoku.business.GenerationAlgorithm;
import sudoku.business.SolutionAlgorithm;
import sudoku.model.Square;
import config.ValidatePcMobile;

@Controller
public class GamesAction {
	
	@RequestMapping("/games")
	public String loadGames(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(showId == null){
			showId = 1;
		}
		if(showId==1){
			GenerationAlgorithm generationAlgorithm = new GenerationAlgorithm();
			model.addAttribute("puzzle", generationAlgorithm.getPuzzle());
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","games.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/games");
	}
	
	
	@RequestMapping("/mobilegames/checkSudoku")
	@ResponseBody
	public Result checkMobilegamesSudoku(String sudokuList) {
		SolutionAlgorithm solution = new SolutionAlgorithm();
		List<Square> ansList = solution.convertStringToSquareList(sudokuList);
		boolean flag = solution.isValid(ansList);
		Result result = new Result();
		if(flag){
			result.setStatus(1);
		}else{
			result.setStatus(0);
		}
		return result;
	}
	
	@RequestMapping("/checkSudoku")
	@ResponseBody
	public Result ceckSudoku(String sudokuList) {
		SolutionAlgorithm solution = new SolutionAlgorithm();
		List<Square> ansList = solution.convertStringToSquareList(sudokuList);
		boolean flag = solution.isValid(ansList);
		Result result = new Result();
		if(flag){
			result.setStatus(1);
		}else{
			result.setStatus(0);
		}
		return result;
	}
	
	@RequestMapping("/mobilegames")
	public String loadMobileGames(Model model,HttpServletRequest request)throws Exception{
		return ValidatePcMobile.checkRequest(request, "/games");
	}
	
	@RequestMapping("/mobilegames/sudoku")
	public String loadMobileSudokuGame(Model model,HttpServletRequest request)throws Exception{
		GenerationAlgorithm generationAlgorithm = new GenerationAlgorithm();
		model.addAttribute("puzzle", generationAlgorithm.getPuzzle());
		return ValidatePcMobile.checkRequest(request, "/games/sudoku");
	}
	
	@RequestMapping("/mobilegames/flappy")
	public String loadMobileFlappyGame(HttpServletRequest request)throws Exception{
		return ValidatePcMobile.checkRequest(request, "/games/flappy");
	}
	
}
