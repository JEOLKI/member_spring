package kr.or.ddit.login;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.WebTestConfig;
import kr.or.ddit.member.service.MemberServiceI;

public class LoginControllerTest extends WebTestConfig{
	
	@Resource(name = "memberService")
	private MemberServiceI memberService;
	
	@Test
	public void viewTest() throws Exception {
		mockMvc.perform(get("/login/view"))
		.andExpect(status().isOk())
		.andExpect(view().name("login/view"));
	}
	
	@Test
	public void loginTest() throws Exception {
		mockMvc.perform(post("/login/process")
						.param("userid", "brown")
						.param("pass", "brownPass"))
		.andExpect(status().isOk())
		.andExpect(view().name("member/memberList"));
	}
	
	@Test
	public void mainTest() throws Exception {
		mockMvc.perform(get("/login/main"))
		.andExpect(status().isOk())
		.andExpect(view().name("member/memberList"));
	}
	
	
}
