package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 인터페이스의 구현체가 하나일 경우 관례상 Impl 라고 뒤에 붙여줌
@Component
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Autowired // Component 스캔으로 MemberServiceImpl 을 빈에 등록하기는 하는데 의존관계는 주입할 수 없다. 그래서 이거를 씀
	public MemberServiceImpl(MemberRepository memberRepository) {
		// AppConfig 에 할당한 구체 MemoryMemberRepository 가 할당됨
		// 이렇게 하면 MemberServiceImpl 에 MemoryMemberRepository 를 의존하지않고 MemberRepository 인터페이스만 의존하게 된다.
		// 다시말해 추상화에만 의존하고 있고 DIP 를 지킬 수 있게 된다.
		// 구체적인거는 MemberServiceImpl 는 모르고 AppConfig 만 알고 있다.
		this.memberRepository = memberRepository;
	}


	@Override
	public void join(Member member) {
		memberRepository.save(member);
		// 다형성에 의해 MemberRepository 의 save 메서드가 아닌 MemoryMemberRepository 의 save 메서드가 호출이 됨

	}

	@Override
	public Member findMember(Long memberId) {
		return memberRepository.findById(memberId);
	}

	// 테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
