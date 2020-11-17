package kr.or.ddit.member.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.InputStream;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import kr.or.ddit.WebTestConfig;
import kr.or.ddit.member.repository.MemberRepositoryI;

public class MemberControllerTest extends WebTestConfig{

	@Resource(name="memberRepository")
	private MemberRepositoryI memberRepository;
	
	@Test
	public void getViewTest() throws Exception {
		mockMvc.perform(get("/member/list"))
				.andExpect(status().isOk())
				.andExpect(view().name("tiles/member/memberListContent"));
	}
	
	@Test
	public void getMemberTest() throws Exception {
		mockMvc.perform(get("/member/member")
						.param("userid", "brown"))
				.andExpect(status().isOk())
				.andExpect(view().name("tiles/member/member"));
	}
	
	@Test
	public void registGetTest() throws Exception {
		mockMvc.perform(get("/member/regist"))
				.andExpect(status().isOk())
				.andExpect(view().name("tiles/member/memberRegist"));
	}
	
	
	@Test
	public void registPost_SUCCESS_Test() throws Exception {
		
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/regist")
						.file(file)
						.param("userid", "rgtest")
						.param("usernm", "test")
						.param("alias", "test")
						.param("pass", "test")
						.param("addr1", "test")
						.param("addr2", "test")
						.param("zipcode", "test"))
				.andDo(print())
				.andExpect(view().name("redirect:/member/list"));
	}
	
	@Test
	public void registPost_ERROR_Test() throws Exception {
		
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/regist")
						.file(file)
						.param("userid", "rgtest")
						.param("alias", "test")
						.param("pass", "test")
						.param("addr1", "test")
						.param("addr2", "test")
						.param("zipcode", "test"))
				.andExpect(view().name("tiles/member/memberRegist"));
	}
	
	@Test
	public void registPost_FAIL_Test() throws Exception {
		
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/regist")
				.file(file)
				.param("userid", "brown")
				.param("usernm", "brown")
				.param("alias", "test")
				.param("pass", "test")
				.param("addr1", "test")
				.param("addr2", "test")
				.param("zipcode", "test"))
		.andExpect(view().name("tiles/member/memberRegist"));
	}
	
	@Test
	public void updateGetTest() throws Exception {
		mockMvc.perform(get("/member/update")
						.param("userid", "brown"))
				.andExpect(status().isOk())
				.andExpect(view().name("tiles/member/memberUpdate"));
	}
	
	@Test
	public void updatePost_SUCCESS_Test() throws Exception {
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/update")
						.file(file)
						.param("userid", "test")
						.param("usernm", "updatetest")
						.param("alias", "test")
						.param("pass", "test")
						.param("addr1", "test")
						.param("addr2", "test")
						.param("zipcode", "test"))
				.andExpect(status().is(302))
				.andExpect(view().name("redirect:/member/member"));
	}
	
	@Test
	public void updatePost_FAIL_Test() throws Exception {
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/update")
				.file(file)
				.param("userid", "test1")
				.param("usernm", "updatetest")
				.param("alias", "test")
				.param("pass", "test")
				.param("addr1", "test")
				.param("addr2", "test")
				.param("zipcode", "test"))
		.andExpect(status().is(302))
		.andExpect(view().name("redirect:/member/update"));
	}
	
	
	
	@Test
	public void listAjaxPageTest() throws Exception {
		mockMvc.perform(get("/member/listAjaxPage"))
				.andExpect(status().isOk())
				.andExpect(view().name("tiles/member/listAjaxPage"));
	}
	
	@Test
	public void listAjaxTest() throws Exception {
		mockMvc.perform(get("/member/listAjax")
						.param("page", "1")
						.param("pageSize", "5"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("memberList"))
		.andExpect(model().attributeExists("pages"))
		.andExpect(view().name("jsonView"));
	}
	
	@Test
	public void listAjaxHTMLTest() throws Exception {
		mockMvc.perform(get("/member/listAjaxHTML")
						.param("page", "1")
						.param("pageSize", "5"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("memberList"))
		.andExpect(model().attributeExists("pages"))
		.andExpect(view().name("member/listAjaxHTML"));
	}
	
	@Test
	public void getMemberAjaxPageTest() throws Exception {
		mockMvc.perform(get("/member/memberAjaxPage"))
		.andExpect(status().isOk())
		.andExpect(view().name("tiles/member/memberAjaxPage"));
	}
	
	@Test
	public void getMemberAjaxTest() throws Exception {
		mockMvc.perform(get("/member/memberAjax")
						.param("userid", "brown"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("memberVo"))
		.andExpect(view().name("jsonView"));
	}
	

}
