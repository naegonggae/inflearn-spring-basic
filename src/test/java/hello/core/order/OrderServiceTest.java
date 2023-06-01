package hello.core.order;

import hello.core.AppConfig;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

	MemberService memberService;
	OrderService orderService;

	@BeforeEach
	public void beforeEach() {
		AppConfig appConfig = new AppConfig();
		memberService = appConfig.memberService();
		orderService = appConfig.orderService();
	}

	@Test
	void createOrder() {
		Long memberId = 1L;
		Member member = new Member(memberId, "memberA", Grade.VIP);
		memberService.join(member);

		Order order = orderService.createOrder(memberId, "itemA", 10000);
		Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
		Assertions.assertThat(order.calculatePrice()).isEqualTo(9000);
	}

//	@Test
//	void fieldInjectionTest() {
//		Long memberId = 1L;
//		Member member = new Member(memberId, "memberA", Grade.VIP);
//		memberService.join(member);
//
//		OrderServiceImpl orderService = new OrderServiceImpl();
//
//		orderService.setMemberRepository(new MemoryMemberRepository());
//		orderService.setDiscountPolicy(new FixDiscountPolicy());
//
//		orderService.createOrder(memberId, "itemA", 10000);
//		// 실패하게 됨 왜냐면 createOrder 내부의 memberRepository 랑 discountPolicy 다 null 뜨기 때문
//		// 그러면 setter 를 만들게 됨
//
//		// 이렇게 귀찮게 테스트를 하게 됨
//	}

}
