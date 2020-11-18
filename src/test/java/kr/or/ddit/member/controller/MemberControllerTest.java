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
	public void listTest() throws Exception {
		mockMvc.perform(get("/member/list")
						.param("page", "1")
						.param("pageSize", "5"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("memberList"))
				.andExpect(model().attributeExists("pages"))
				.andExpect(view().name("jsonView"));
	}
	
	@Test
	public void getMember_GET_Test() throws Exception {
		mockMvc.perform(get("/member/member"))
				.andExpect(status().isOk())
				.andExpect(view().name("member/member"));
	}
	
	@Test
	public void getMember_POST_Test() throws Exception {
		mockMvc.perform(post("/member/member")
						.param("userid", "brown"))
				.andExpect(status().isOk())
				.andExpect(view().name("jsonView"));
	}
	
	@Test
	public void regist_Get_Test() throws Exception {
		mockMvc.perform(get("/member/regist"))
				.andExpect(status().isOk())
				.andExpect(view().name("member/memberRegist"));
	}
	
	
	@Test
	public void regist_POST_SUCCESS_Test() throws Exception {
		
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
				.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void regist_POST_ERROR_Test() throws Exception {
		
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
				.andExpect(view().name("member/memberRegist"));
	}
	
	@Test
	public void regist_POST_FAIL_Test() throws Exception {
		
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
		.andExpect(view().name("member/memberRegist"));
	}
	
	@Test
	public void update_GET_Test() throws Exception {
		mockMvc.perform(get("/member/update")
						.param("userid", "brown"))
				.andExpect(status().isOk())
				.andExpect(view().name("member/memberUpdate"));
	}
	
	@Test
	public void update_POST_SUCCESS_Test() throws Exception {
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/update")
						.file(file)
						.param("userid", "brown")
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
	public void update_POST_ERROR_Test() throws Exception {
		
		InputStream is = getClass().getResourceAsStream("/kr/or/ddit/upload/brown.png");
		MockMultipartFile file = new MockMultipartFile("file", "brown.png", "image/png", is);
		
		mockMvc.perform(fileUpload("/member/update")
						.file(file)
						.param("userid", "brown")
						.param("alias", "test")
						.param("pass", "test")
						.param("addr1", "test")
						.param("addr2", "test")
						.param("zipcode", "test"))
				.andExpect(view().name("member/memberUpdate"));
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
	public void delete_SUCCESS_Test() throws Exception {
		mockMvc.perform(get("/member/delete")
					   .param("userid", "brown"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login/main"));
	}
	
	@Test
	public void delete_FAIL_Test() throws Exception {
		mockMvc.perform(get("/member/delete")
				.param("userid", "test"))
		.andExpect(status().is3xxRedirection());
	}
	
	@Test
	public void search_i_Test() throws Exception{
		mockMvc.perform(get("/member/search")
				   .param("searchType", "i")
				   .param("searchKeyWord", "brown"))
			.andExpect(status().isOk())
			.andExpect(view().name("jsonView"));
	}
	
	@Test
	public void search_n_Test() throws Exception{
		mockMvc.perform(get("/member/search")
				.param("searchType", "n")
				.param("searchKeyWord", "브라운"))
		.andExpect(status().isOk())
		.andExpect(view().name("jsonView"));
	}
	
	@Test
	public void search_a_Test() throws Exception{
		mockMvc.perform(get("/member/search")
				.param("searchType", "a")
				.param("searchKeyWord", "브라운"))
		.andExpect(status().isOk())
		.andExpect(view().name("jsonView"));
	}
	
	
	

}
