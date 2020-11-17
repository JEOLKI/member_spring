package kr.or.ddit.member.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;

import kr.or.ddit.ModelTestConfig;
import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.member.model.MemberVo;

public class MemberRepositoryTest extends ModelTestConfig {

	@Resource(name = "memberRepository")
	private MemberRepositoryI memberRepository;

	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Test
	public void selectAllMemberTest() {
		/*** Given ***/

		/*** When ***/
		List<MemberVo> memberList = memberRepository.selectAllMember();

		/*** Then ***/
		assertTrue(memberList.size() > 15);
	}

	@Test
	public void getMemberTest() {

		/*** Given ***/
		String userId = "brown";

		MemberVo answerMemberVo = new MemberVo();
		answerMemberVo.setUserid("brown");
		answerMemberVo.setPass("brownPass");

		/*** When ***/
		MemberVo memberVo = memberRepository.getMember(userId);

		/*** Then ***/
		assertEquals("brown", memberVo.getUserid());
		assertEquals("brownPass", memberVo.getPass());

	}

	@Test
	public void selectMemberPageListTest() {

		/*** Given ***/
		PageVo pageVo = new PageVo(1, 5);

		/*** When ***/
		List<MemberVo> memberList = memberRepository.selectMemberPageList(pageVo);

		/*** Then ***/
		assertEquals(pageVo.getPageSize(), memberList.size());

	}

	@Test
	public void selectMemberTotalCntTest() {

		/*** Given ***/

		/*** When ***/
		int totalCnt = memberRepository.selectMemberTotalCnt();

		/*** Then ***/
		assertEquals(16, totalCnt);

	}

	@Test
	public void insertMemberTest() {
		/*** Given ***/
		MemberVo memberVo = new MemberVo("JEOLKI", "pass1234", "홍정기", "JH", "대전 중구 중앙로 76", "영민빌딩 4층 404호", "34940",
										"d:\\profile\\브루니.png", "브루니.png");

		/*** When ***/
		int insertCnt = memberRepository.insertMember(memberVo);

		/*** Then ***/
		assertEquals(1, insertCnt);

	}

	@Test
	public void updateMemberTest() {
		/*** Given ***/
		MemberVo memberVo = new MemberVo("brown", "brownPass", "브라운", "곰", "대전 중구 중앙로 76", "영민빌딩 4층 404호", "34940",
										"D:\\profile\\brown.png", "brown.png");

		/*** When ***/
		int updateCnt = memberRepository.updateMember(memberVo);

		/*** Then ***/
		assertEquals(1, updateCnt);
	}
	
	@Test
	public void deleteMemberTest() {
		/***Given***/
		String userid = "brown";

		/***When***/
		int deleteCnt = memberRepository.deleteMember(userid);

		/***Then***/
		assertEquals(1, deleteCnt);
		
	}

}
