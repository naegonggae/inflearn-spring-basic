package hello.core.member;
// 인터페이스의 구현체가 하나일 경우 관례상 Impl 라고 뒤에 붙여줌
public class MemberServiceImpl implements MemberService {

	// 아직 의존을 객체 생성해서 받아야한다.
	// Solid 의 원칙중에 DIP 를 잘 지키고 있는가?
	// 실제로 MemberServiceImpl 는 MemberRepository 라는 역할만을 의존하는게 아니라 역할 + MemoryMemberRepository 구현체도 의존하고 있어 위반되고 있다.
	// 즉, 추상화에도 의존하고 구체화에도 의존을 하고 있다.
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
