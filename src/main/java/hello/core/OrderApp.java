package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

	public static void main(String[] args) {
//		AppConfig appConfig = new AppConfig();
//		MemberService memberService = appConfig.memberService();
//		OrderService orderService = appConfig.orderService();

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MemberService memberService = applicationContext.getBean("memberService",
				MemberService.class);
		OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
		// getBean = AppConfig 에서 스프링컨테이너로 등록했던 빈 가져와라
		// 근데 그 빈 이름은 "orderService"(메서드이름으로 저장)이고 타입은 OrderService.class 야

		Long memberId = 1l;
		Member member = new Member(memberId, "memberA", Grade.VIP);
		memberService.join(member);

		Order order = orderService.createOrder(memberId, "itemA", 10000);

		System.out.println("order = " + order);
		// soutv = 특정형태 출력문으로 만들기
		System.out.println("order.calculatePrice = " + order.calculatePrice());

	}

}
