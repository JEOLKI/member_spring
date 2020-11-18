package kr.or.ddit.member.repository;

import java.util.List;
import java.util.Map;

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

	@Override
	public List<MemberVo> searchMember(Map<String, Object> map) {
		return sqlSession.selectList("member.searchMember", map);
	}

}
