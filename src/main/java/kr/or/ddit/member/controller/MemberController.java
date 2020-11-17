package kr.or.ddit.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;

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
		return "tiles/member/memberRegist";
	}
	
	
	@RequestMapping(path = "/regist" , method = {RequestMethod.POST})
	public String regist(@Valid MemberVo memberVo, BindingResult br, MultipartFile file) {
		
		// validation
		//new MemberVoValidator().validate(memberVo, br);
		
		// 寃�利앹쓣 �넻怨쇳븯吏� 紐삵뻽�쑝誘�濡� �궗�슜�옄 �벑濡� �솕硫댁쑝濡� �씠�룞
		if(br.hasErrors()) {
			return "tiles/member/memberRegist";
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
		
		// �궗�슜�옄 �젙蹂� �벑濡�
		memberVo.setFilename(filePath);
		memberVo.setRealFilename(realFilename);
		
		int insertCnt = 0;
		try {
			insertCnt = memberService.insertMember(memberVo);

			// 1嫄댁씠 �엯�젰�릺�뿀�쓣 �븣 : �젙�긽 - memberList �럹�씠吏�濡� �씠�룞
			if ( insertCnt == 1 ) {
				return "redirect:/member/list";
			} 
		} catch (Exception e) {
		}
		
		// 1嫄댁씠 �븘�땺�븣 : 鍮꾩젙�긽
		return "tiles/member/memberRegist";
	}
	
	@RequestMapping(path = "/update", method = {RequestMethod.GET} )
	public String update(Model model, String userid) {
		
		MemberVo memberVo = memberService.getMember(userid);
		model.addAttribute("memberVo", memberVo);
		
		return "tiles/member/memberUpdate";
	}
	
	@RequestMapping(path = "/update", method = {RequestMethod.POST})
	public String update(MemberVo memberVo, MultipartFile file, RedirectAttributes ra ) {
		
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
		
		// �궗�슜�옄 �젙蹂� �벑濡�
		int updateCnt = memberService.updateMember(memberVo);
		
		// 1嫄댁씠 �엯�젰�릺�뿀�쓣 �븣 : �젙�긽 - member �럹�씠吏�濡� �씠�룞
		if ( updateCnt == 1 ) {
			return "redirect:/member/member";
		} 
		// 1嫄댁씠 �븘�땺�븣 : 鍮꾩젙�긽
		else {
			return "redirect:/member/update";
		}
	}
	
	
	
	
}
