package kr.or.ddit.member.repository;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.member.model.MemberVo;

@Repository("memberRepository")
public class MemberRepository implements MemberRepositoryI {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public MemberVo getMember(String userId) {
		// 원래는 db에서 데이터를 조회하는 로직이 있어야 하나
		// 우리는 controller 기능에 집중하기 위해 하드코딩을 통해 dao, service는 간략하게 넘어간다.
		// Mock (가짜)
		/*
		 * MemberVo memberVo = new MemberVo(); memberVo.setUserId("brown");
		 * memberVo.setPassword("passBrown");
		 */

		// SqlSession sqlSession = MybatisUtil.getSqlSession();

		// select
		// 한건 : selectOne
		// 여러건 : selectList
		//MemberVo memberVo = sqlSession.selectOne("member.getMember", userId);

		// 사용후 닫아준다.
		// sqlSession.close();

		return sqlSession.selectOne("member.getMember", userId);
	}

	@Override
	public List<MemberVo> selectAllMember() {
		return sqlSession.selectList("member.selectAllmember");
	}

	@Override
	public List<MemberVo> selectMemberPageList(PageVo pageVo) {
		return sqlSession.selectList("member.selectMemberPageList", pageVo);
	}

	@Override
	public int selectMemberTotalCnt() {
		return sqlSession.selectOne("member.selectMemberTotalCnt");
	}

	@Override
	public int insertMember(MemberVo memberVo) {
		return sqlSession.insert("member.insertMember", memberVo);
	}

	@Override
	public int deleteMember(String userid) {
		return sqlSession.delete("member.deleteMember", userid);
	}

	@Override
	public int updateMember(MemberVo memberVo) {

		return sqlSession.update("member.updateMember", memberVo);

	}

}
