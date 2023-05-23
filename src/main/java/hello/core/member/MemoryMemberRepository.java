package hello.core.member;

import java.util.HashMap;
import java.util.Map;

public class MemoryMemberRepository implements MemberRepository{

	private static Map<Long, Member> store = new HashMap<>();
	// 실무에서는 동시성 문제로 concurrent hashmap 을 써야함

	@Override
	public void save(Member member) {
		store.put(member.getId(), member);

	}

	@Override
	public Member findById(Long memberId) {
		return store.get(memberId);
	}
	// 오류처리같은거는 없이 간단하게 구현함
}
