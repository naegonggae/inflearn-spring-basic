package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
// 애플리케이션의 실제 동작에 필요한 구현 클래스를 생성한다.
// MemberServiceImpl 라는 로미오가 MemoryMemberRepository 라는 줄리엣 누가 뽑을지 신경쓰는게 아니고 AppConfig 라는 기획자가 캐스팅을 하고있다.
// MemberServiceImpl 라는 로미오는 줄리엣이라는 역할만 알고 연습하면되고 줄리엣이 정확히 누군지는 모른다.
public class AppConfig {

	public MemberService memberService() {
		return new MemberServiceImpl(new MemoryMemberRepository());
		// MemberServiceImpl 의 생성자에 구체 MemoryMemberRepository 를 지정해줌
		// 생성자 주입
	}

	public OrderService orderService() {
		return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
	}

}

// 설계 변경으로 OrderServiceImpl 은 FixDiscountPolicy 를 의존하지 않는다! 단지 DiscountPolicy 인터페이스만 의존한다.
// OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
// OrderServiceImpl 의 생성자를 통해서 어떤 구현 객체을 주입할지는 오직 외부( AppConfig )에서 결정한다.
// OrderServiceImpl 은 이제부터 실행에만 집중하면 된다.
// OrderServiceImpl 에는 MemoryMemberRepository , FixDiscountPolicy 객체의 의존관계가 주입된다.

// appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl 을 생성하면서 생성자로 전달한다.
// 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
// DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.