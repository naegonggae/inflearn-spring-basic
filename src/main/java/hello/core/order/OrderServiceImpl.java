package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import java.beans.ConstructorProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor()
public class OrderServiceImpl implements OrderService {

	// 생성자 주입을 쓰면 좋은점
	// 파라미터에 연결정보가 들어나 가독성좋다. final 을 사용해서 불변하게 생성한다. 누락하면 오류로 알려준다.
	// 컴파일 오류가 세상에서 가장 빠르고 좋은 오류다.
	// 항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지 않는게 좋다.
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = rateDiscountPolicy; // 자동 주입을 어떤걸 할지 정하지 못하게된다.
	}
	// @Autowired 매칭 정리
	// 1. 타입 매칭
	// 2. 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭
	// 그래서 생성자 주입할때 파라미터 이름이 빈등록한 2개중에 있으면 그걸로 연결해준다.

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice);
		// 단일 책임 원칙이 잘 지켜짐
		// orderService 는 할인에 대해 모름 할인에 관한거는 discountPrice 가 알아서 해라
		// 그래서 할인에 관한 수정이 있을 경우에 discountPrice 만 뜯어 고치면 된다.
		// 만약 orderService 에도 할인에 관한 로직이 있다면 할인에 관한 수정이 있을 때 다 둘다 뜯어 고쳐야 되는 상황이 나온다.

		return new Order(memberId, itemName, itemPrice, discountPrice);
	}

	// 테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}
}
