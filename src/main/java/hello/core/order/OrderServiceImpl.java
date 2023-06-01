package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

	// 외부에서 변경하기 어려워서 테스트하기 힘들다.
	@Autowired private MemberRepository memberRepository;
	@Autowired private DiscountPolicy discountPolicy;

	// 이렇게 필드 주입하면 setter 를 만들게 되는데 이럴 바에는 수정자 주입을 한다는 마인드...
	// 결론 쓰지말자 근데 테스트에 사용할 때도 있긴함
	@Autowired
	public void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	@Autowired
	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
	}

	// 생성자 호출시점에 딱 1번만 호출되는 것을 보장된다. 불변, 필수 의존관계에 사용
	// 원래는 빈등록하고 의존관계주입하는 순서로 이뤄지지만 생성자로 DI를 하면 빈등록할때 생성자를 쓰니까 동시에 하게 되버린다.
//	@Autowired // OrderServiceImpl 생성자가 하나면 생략해도 됨, 요즘에 안쓰는 추세임
//	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//		// Autowired 어노테이션을 뺏을때 로그 찍히는 확인함
//		System.out.println("memberRepository = " + memberRepository);
//		System.out.println("discountPolicy = " + discountPolicy);
//		this.memberRepository = memberRepository;
//		this.discountPolicy = discountPolicy;
//	}


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
