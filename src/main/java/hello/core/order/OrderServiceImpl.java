package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

	private final MemberRepository memberRepository = new MemoryMemberRepository();
//	private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	// 단일책임의 클래스를 구성했다. SRP OK

	// 우리는 역할과 구현을 충실하게 분리했다. LSP ISP OK
	// 다형성도 활용하고, 인터페이스와 구현 객체를 분리했다. LSP ISP OK

	// 잘보면 클라이언트인 OrderServiceImpl 이 DiscountPolicy 인터페이스 뿐만 아니라
	// FixDiscountPolicy 인 구체 클래스도 함께 의존하고 있다. 실제 코드를 보면 의존하고 있다! DIP 위반

	// 지금 코드는 기능을 확장해서 변경하면, 클라이언트 코드(OrderServiceImpl)에 영향을 준다! 따라서 OCP 를 위반한다.
	// 할인정책을 바꿀때마다 new 를 해서 갈아끼워야하니까
	private DiscountPolicy discountPolicy;
	// final 은 무조건 값이 할당 되야하니까 뺀거야
	// 인터페이스에만 의존하도록 변경했다.
	// 테스트를 해봤더니 discountPolicy 의 구현체가 없어서 nullPointException 이 났다.
	// 어떻게 이 문제를 해결 할 수 있을까?

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
}
