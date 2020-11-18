package kr.or.ddit.member.repository;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.member.model.MemberVo;

public interface MemberRepositoryI {
	
	MemberVo getMember(String userId);
	
	List<MemberVo> selectAllMember();
	
	List<MemberVo> selectMemberPageList(PageVo pageVo);
	
	int selectMemberTotalCnt();

	int insertMember(MemberVo memberVo);
	
	int deleteMember(String userid);
	
	int updateMember(MemberVo memberVo);
	
	List<MemberVo> searchMember(Map<String, Object> map);

}
