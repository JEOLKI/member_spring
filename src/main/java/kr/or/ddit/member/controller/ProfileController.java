package kr.or.ddit.member.controller;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

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
		// 응답으로 생성하려고 하는 것 : 이미지 파일을 읽어서 output stream 객체에 쓰는 것

		MemberVo memberVo = memberService.getMember(userid);
		model.addAttribute("filepath", memberVo.getFilename());
		
		return "profileImgView";
	}
	
	
	@RequestMapping("/profileImg")
	public void profileImg(String userid, HttpServletResponse response) throws IOException {
		
		//response content-type
		response.setContentType("image/png");
		
		// db에서 사용자 filename 확인하고
		MemberVo memberVo = memberService.getMember(userid);
		
		// 경로 확인 후 파일 입출력을 통해 응답생성
		// 파일을 읽고
		// 응답 생성
		// memberVo.getFilename(); // 파일경로

		FileInputStream fis = new FileInputStream(memberVo.getFilename());
		ServletOutputStream sos = response.getOutputStream();

		byte[] buffer = new byte[512];

		while (fis.read(buffer) != -1) {
			sos.write(buffer);
		}
	
		fis.close();
		sos.flush();
		sos.close();
	}
	
	@RequestMapping("/profileDownload")
	public String profileDownload(String userid, Model model){

		// db에서 사용자 filename 확인하고
		MemberVo memberVo = memberService.getMember(userid);
		
		model.addAttribute("filepath", memberVo.getFilename());
		model.addAttribute("filename", memberVo.getRealFilename());
		
		return "DownloadView";
	}
	
}
