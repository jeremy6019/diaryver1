package com.jeremy94.diary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeremy94.diary.domain.DiaryBoard;
import com.jeremy94.diary.service.DiaryBoardService;

@Controller
public class DiaryBoardController {
	@Autowired
	private DiaryBoardService diaryBoardService;
	
	// board/write 요청이 GET 방식으로 온 경우 처리
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String write(Model model) {
		return "board/write";
	}
   
	// board/write 요청이 POST 방식으로 온 경우 처리
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String write(HttpServletRequest request, RedirectAttributes attr) {
		boolean r = diaryBoardService.write(request);
		if(r == true) {
			return "redirect:list";
		}else {
			attr.addFlashAttribute("msg", "게시글 작성에 실패했습니다.");
			return "redirect:write";
		}
	}
	
	// 목록보기 처리를 위한 메소드
	@RequestMapping(value="/board/list", method=RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		// 서비스의 메소드 호출
		List<DiaryBoard> list = diaryBoardService.list(request);
		// 데이터를 저장
		model.addAttribute("list", list);
		return "board/list";
	}

	
}



