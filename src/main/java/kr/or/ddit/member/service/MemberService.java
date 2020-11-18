package kr.or.ddit.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.member.model.MemberVo;
import kr.or.ddit.member.repository.MemberRepositoryI;

@Transactional
@Service("memberService")
public class MemberService implements MemberServiceI {

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Resource(name = "memberRepository")
	private MemberRepositoryI memberRepository;
	
	public MemberService() {
		// new 연산자가 아닌 빈을 주입하는 형태로 사용하기 위해서 지운다
		// new MemberRepository();
	}

	@Override
	public MemberVo getMember(String userId) {
		return memberRepository.getMember(userId);
	}

	@Override
	public List<MemberVo> selectAllMember() {
		return memberRepository.selectAllMember();
	}

	@Override
	public Map<String, Object> selectMemberPageList(PageVo pageVo) {

		// 같은 서비스의 메소드 안에 여러개의 Dao를 실행하려면 같은 sqlSession을 사용해야 한다 같은트랜잭션이기때문에 -> 서비스 객체에서
		// 만들어야한다.
		//SqlSession sqlSession = MybatisUtil.getSqlSession();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberList", memberRepository.selectMemberPageList(pageVo));

		// 15건, 페이지 사이즈를 7로 가정했을때 3개의 페이지가 나와야한다
		// 15/7 =2.14.. 올림을 하여 3개의 페이지가 필요

		int totalCnt = memberRepository.selectMemberTotalCnt();
		int pageSize = pageVo.getPageSize();
		int pages = (int) Math.ceil((double) totalCnt / pageSize);
		map.put("pages", pages);

		//sqlSession.close();

		return map;
	}

	@Override
	public int insertMember(MemberVo memberVo) {
		return memberRepository.insertMember(memberVo);
	}

	@Override
	public int updateMember(MemberVo memberVo) {
		return memberRepository.updateMember(memberVo);
	}
	
	@Override
	public int deleteMember(String userid) {
		return memberRepository.deleteMember(userid);
	}

	@Override
	public List<MemberVo> searchMember(Map<String, Object> map) {
		return memberRepository.searchMember(map);
	}

}
