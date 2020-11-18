package kr.or.ddit.member.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.WebTestConfig;
import kr.or.ddit.member.service.MemberServiceI;

public class ProfileControllerTest extends WebTestConfig{
	
	@Resource(name = "memberService")
	private MemberServiceI memberService;
	
	@Test
	public void profileImgTest() throws Exception {
		mockMvc.perform(get("/profileImgView")
						.param("userid", "brown"))
				.andExpect(view().name("profileImgView"));
		
	}

}
