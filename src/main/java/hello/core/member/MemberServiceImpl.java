package hello.core.member;
// 인터페이스의 구현체가 하나일 경우 관례상 Impl 라고 뒤에 붙여줌
public class MemberServiceImpl implements MemberService {

	// 아직 의존을 객체 생성해서 받아야한다.
	private final MemberRepository memberRepository = new MemoryMemberRepository();

	@Override
	public void join(Member member) {
		memberRepository.save(member);
		// 다형성에 의해 MemberRepository 의 save 메서드가 아닌 MemoryMemberRepository 의 save 메서드가 호출이 됨

	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
