package kr.or.ddit.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.file.FileUploadUtil;
import kr.or.ddit.member.model.MemberVo;
import kr.or.ddit.member.service.MemberServiceI;

@RequestMapping("/member")
@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Resource(name = "memberService")
	private MemberServiceI memberService;
	
	@RequestMapping(path = "/list", method= {RequestMethod.GET, RequestMethod.POST})
	public String getView(Model model,
						  @RequestParam(name="page", defaultValue = "1", required = false) int page,
						  @RequestParam(name="pageSize", defaultValue = "5", required = false ) int pageSize) {
		
		// pageVo : page, pageSize
		PageVo pageVo = new PageVo(page, pageSize);
		//memberService.selectMemberPageList(page) => List<MemberVo> => Map<String,Object>
		Map<String, Object> map = memberService.selectMemberPageList(pageVo);
		model.addAttribute("memberList", map.get("memberList"));
		model.addAttribute("pages", map.get("pages"));
		model.addAttribute("cpage", page);
		
		return "jsonView"; // list
	}
	
	@RequestMapping(path = "/member", method = {RequestMethod.GET})
	public String getMember() {
		return "member/member";
	}
	
	@RequestMapping(path = "/member", method = {RequestMethod.POST})
	public String getMember(Model model, String userid) {
		MemberVo memberVo = memberService.getMember(userid);
		model.addAttribute("memberVo", memberVo);
		return "jsonView";
	}
	
	@RequestMapping(path = "/regist" , method = {RequestMethod.GET})
	public String regist() {
		return "member/memberRegist";
	}
	
	
	@RequestMapping(path = "/regist" , method = {RequestMethod.POST})
	public String regist(@Valid MemberVo memberVo, BindingResult br,@RequestParam(required = false) MultipartFile file,
						Model model) {
		
		if(br.hasErrors()) {
			return "member/memberRegist";
		}
		
		String filePath = "";
		String realFilename = "";
		if( file.getSize() > 0 ) {
			String filename = UUID.randomUUID().toString();
			realFilename = file.getOriginalFilename();
			String extension = FileUploadUtil.getExtension(realFilename);
			File uploadFile = new File("D:\\profile\\" + filename + "." + extension);
			filePath = "D:\\profile\\" + filename + "." + extension;
			try {
				file.transferTo(uploadFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		memberVo.setFilename(filePath);
		memberVo.setRealFilename(realFilename);
		
		int insertCnt = 0;
		try {
			insertCnt = memberService.insertMember(memberVo);

			if ( insertCnt == 1 ) {
				return "redirect:/member/member?userid=" + memberVo.getUserid();
			} 
		} catch (Exception e) {
		}
		
		model.addAttribute("msg", "fail");
		
		return "member/memberRegist";
	}
	
	@RequestMapping(path = "/update", method = {RequestMethod.GET} )
	public String update(Model model, String userid) {
		MemberVo memberVo = memberService.getMember(userid);
		model.addAttribute("memberVo", memberVo);
		return "member/memberUpdate";
	}
	
	@RequestMapping(path = "/update", method = {RequestMethod.POST})
	public String update(@Valid MemberVo memberVo, BindingResult br,@RequestParam(required = false)MultipartFile file, RedirectAttributes ra ) {
		
		if(br.hasErrors()) {
			return "member/memberUpdate";
		}
		
		String realFilename= "";
		String filePath ="";
		String fileCheck = file.getOriginalFilename();
		if(!fileCheck.equals("")) {
			
			realFilename = file.getOriginalFilename();
			String filename = UUID.randomUUID().toString();
			String extension = FileUploadUtil.getExtension(realFilename);
			
			if( file.getSize() > 0 ) {
				File uploadFile = new File("D:\\profile\\" + filename + "." + extension);
				filePath = "D:\\profile\\" + filename + "." + extension;
				
				try {
					file.transferTo(uploadFile);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			MemberVo dbMember = memberService.getMember(memberVo.getUserid());
			realFilename = dbMember.getRealFilename();
			filePath = dbMember.getFilename();
		}
		
		memberVo.setFilename(filePath);
		memberVo.setRealFilename(realFilename);
		
		logger.debug("orginal file : {}, {}", filePath, realFilename);
		
		ra.addAttribute("userid", memberVo.getUserid());
		
		int updateCnt = memberService.updateMember(memberVo);
		
		if ( updateCnt == 1 ) {
			return "redirect:/member/member";
		} 
		else {
			return "redirect:/member/update";
		}
	}
	
	@RequestMapping("/delete")
	public String delete(String userid) {
		int deleteCnt = memberService.deleteMember(userid);
		
		if(deleteCnt == 1) {
			return "redirect:/login/main";
		} else {
			return "redirect:/member/member?userid=" + userid;
		}
	}
	
	@RequestMapping("/search")
	public String search(String searchType, String searchKeyWord, Model model,
						@RequestParam(name="page", defaultValue = "1", required = false) int page,
						@RequestParam(name="pageSize", defaultValue = "5", required = false ) int pageSize) {
		
		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("page", page);
		map.put("pageSize", pageSize);
		
		if (searchType.equals("i")) {
			map.put("userid", searchKeyWord);
		} else if (searchType.equals("n")) {
			map.put("usernm", searchKeyWord);
		} else if (searchType.equals("a")) {
			map.put("alias", searchKeyWord);
		}
		
		List<MemberVo> memberList = memberService.searchMember(map);
		model.addAttribute("memberList", memberList);
		
		return "jsonView";
	}
	
	
	
	
}
