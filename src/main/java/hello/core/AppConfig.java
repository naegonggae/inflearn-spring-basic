package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 이렇게 Configuration, Bean 어노테이션을 붙이면 스프링 컨테이너에 저장되게 된다.
// Configuration 안붙이면 AppConfig$$SpringCGLIB 대신 AppConfig.class 타입으로 빈에 등록하고 같은 것일지라도
// 호출이 되어있으면 빈에 중복과 상관없이 등록한다. memberRepository 같은경우 3번을 등록함 -> 싱글톤이 깨지게 됨
@Configuration
public class AppConfig {
	// DI 컨테이너 이다.

	// @Bean memberService -> new MemoryMemberRepository()
	// @Bean orderService -> new MemoryMemberRepository()
	// 벌써 빈등록하면서 MemoryMemberRepository 는 두번 호출됐는데 이건 싱글톤방식을 위반한는거 아닌가? 테스트 코드를 짜보자

	// Configuration 안써도 싱글톤이 안깨지게 하는 방법은 있다.
//	@Autowired MemberRepository memberRepository; // 이렇게 빈에 등록된 거를 변수로 가져와서 주입해주면 싱글톤이 유지가 된다.
	@Bean
	public MemberService memberService() {
		System.out.println("Call - AppConfig.memberService");
		// 실행됐는지 로그 찍어보기
		return new MemberServiceImpl(MemberRepository());
		// MemberServiceImpl 의 생성자에 구체 MemoryMemberRepository 를 지정해줌
		// 생성자 주입
	}

	@Bean
	public MemoryMemberRepository MemberRepository() {
		System.out.println("Call - AppConfig.MemberRepository");
		return new MemoryMemberRepository();
	} // 이거는 cmd opt m 해서 바꾸니까 자동으로 넣어줬네?
	// DB가 바뀌면 여기를 수정하면 돼

	@Bean
	public OrderService orderService() {
		System.out.println("Call - AppConfig.orderService");
		return new OrderServiceImpl(MemberRepository(), discountPolicy());
	} // MemberRepository(), discountPolicy() 요런식으로 바꾼이유도 저것들이 중복으로 들어가있기때문에 변수화 해준거임

	@Bean
	public DiscountPolicy discountPolicy() {
//		return new FixDiscountPolicy();
		return new RateDiscountPolicy();
	}
	// 할일 정책이 수정되면 여기를 바꿔주면 돼

}

// 설계 변경으로 OrderServiceImpl 은 FixDiscountPolicy 를 의존하지 않는다! 단지 DiscountPolicy 인터페이스만 의존한다.
// OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
// OrderServiceImpl 의 생성자를 통해서 어떤 구현 객체을 주입할지는 오직 외부( AppConfig )에서 결정한다.
// OrderServiceImpl 은 이제부터 실행에만 집중하면 된다.
// OrderServiceImpl 에는 MemoryMemberRepository , FixDiscountPolicy 객체의 의존관계가 주입된다.

// appConfig 객체는 memoryMemberRepository 객체를 생성하고 그 참조값을 memberServiceImpl 을 생성하면서 생성자로 전달한다.
// 클라이언트인 memberServiceImpl 입장에서 보면 의존관계를 마치 외부에서 주입해주는 것 같다고 해서
// DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라 한다.