package kr.or.ddit.member.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.ModelTestConfig;
import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.member.model.MemberVo;

public class MemberServiceTest extends ModelTestConfig{
	
	@Resource(name = "memberService")
	private MemberServiceI memberService;
	
	@Test
	public void selectAllMemberTest() {
		/*** Given ***/

		/*** When ***/
		List<MemberVo> memberList = memberService.selectAllMember();

		/*** Then ***/
		assertTrue(memberList.size() > 15);
	}
	
	@Test
	public void insertMember_SUCCESS_Test() {
		/***Given***/
		MemberVo memberVo = new MemberVo("ddit", "dditpass", "대적인재", "개발원", "", "", "", "", "");

		/***When***/
		int insertCnt = memberService.insertMember(memberVo);
		
		/***Then***/
		assertEquals(1, insertCnt);
	}

	//@Test
	public void insertMember_FAIL_Test() {
		/***Given***/
		MemberVo memberVo = new MemberVo("ddit", "dditpass", "대적인재", "개발원", "", "", "", "", "");
		
		/***When***/
		int insertCnt = memberService.insertMember(memberVo);
		
		/***Then***/
		assertEquals(1, insertCnt);
	}
	
	@Test
	public void getMemberTest() {

		/*** Given ***/
		String userId = "brown";

		MemberVo answerMemberVo = new MemberVo();
		answerMemberVo.setUserid("brown");
		answerMemberVo.setPass("brownPass");

		/*** When ***/
		MemberVo memberVo = memberService.getMember(userId);

		/*** Then ***/
		assertEquals("brown", memberVo.getUserid());
		assertEquals("brownPass", memberVo.getPass());

	}

	@Test
	public void selectMemberPageListTest() {

		/*** Given ***/
		PageVo pageVo = new PageVo(1,5);

		/*** When ***/
		// memberList 확인
		Map<String, Object> map = memberService.selectMemberPageList(pageVo);
		List<MemberVo> memberList = (List<MemberVo>) map.get("memberList");

		// 생성해야할 page 수
		int pages = (int) map.get("pages");

		/*** Then ***/
		assertEquals(pageVo.getPageSize(), memberList.size());
		assertEquals(4, pages);

	}
	
	@Test
	public void insertMemberTest() {
		/***Given***/
		MemberVo memberVo = new MemberVo("JEOLKI","pass1234", "홍정기", "JH", "대전 중구 중앙로 76",
				 "영민빌딩 4층 404호", "34940", "d:\\profile\\브루니.png", "브루니.png");

		/***When***/
		int insertCnt = memberService.insertMember(memberVo);

		/***Then***/
		assertEquals(1, insertCnt);
		
	}
	
	@Test
	public void updateMemberTest() {
		/***Given***/
		MemberVo memberVo = new MemberVo("brown","brownPass", "브라운", "곰", "대전 중구 중앙로 76",
				 "영민빌딩 4층 404호", "34940", "D:\\profile\\brown.png", "brown.png");

		/***When***/
		int updateCnt = memberService.updateMember(memberVo);

		/***Then***/
		assertEquals(1, updateCnt);
	}
	
	@Test
	public void deleteMemberTest() {
		/***Given***/
		String userid = "brown";

		/***When***/
		int deleteCnt = memberService.deleteMember(userid);

		/***Then***/
		assertEquals(1, deleteCnt);
		
	}
}
