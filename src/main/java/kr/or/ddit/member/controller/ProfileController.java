package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.member.model.MemberVo;
import kr.or.ddit.member.service.MemberServiceI;

@Controller
public class ProfileController {

	@Resource(name = "memberService")
	private MemberServiceI memberService;
	
	@RequestMapping("/profileImgView")
	public String profileImg(String userid, Model model) throws IOException {
		MemberVo memberVo = memberService.getMember(userid);
		model.addAttribute("filepath", memberVo.getFilename());
		return "profileImgView";
	}
	
	
}
