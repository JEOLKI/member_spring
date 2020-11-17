package kr.or.ddit.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.member.model.MemberVo;
import kr.or.ddit.member.service.MemberServiceI;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "memberService")
	private MemberServiceI memberService;

	@RequestMapping(path = "/login", method = { RequestMethod.GET })
	public String view() {
		return "login/view";
	}

	@RequestMapping(path = "/login/process")
	public String login(String userid, String pass, HttpSession session, Model model) {
		
		logger.debug("userId : {}, password : {} ", userid, pass);
		MemberVo memberVo = memberService.getMember(userid);
		
		if (memberVo == null || !memberVo.getPass().equals(pass)) {
			model.addAttribute("userid", userid);
			return "login/view";
		}else if (memberVo.getPass().equals(pass)) {
			session.setAttribute("S_MEMBER", memberVo);
			return "member/memberList";
		}
		return "login/view";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login/view";
	}

}
