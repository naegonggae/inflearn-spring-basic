package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//		basePackages = "hello.core.member", // 어디서 찾을건지 지정
		// 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
//		basePackageClasses = AutoAppConfig.class, // 지정 클래스가 속한 패키지내에 탐색
		// 디폴트는 이 클래스의 하위패키지를 뒤진다.
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
		// AppConfig 를 등록시키지 않기 위해 설정 Configuration 붙은거는 제외하겠다는 말임
)
public class AutoAppConfig {
	// 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
	// 등록할때 빈 객체 이름은 클래스 네임에서 맨앞글자만 소문자로 바꾼것, 다른 이름으로 설정할 수도 있음
	// @Autowired 의 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.

	// @Component : 컴포넌트 스캔에서 사용
	// @Controlller : 스프링 MVC 컨트롤러에서 사용
	// @Service : 스프링 비즈니스 로직에서 사용
	// @Repository : 스프링 데이터 접근 계층에서 사용 @Configuration : 스프링 설정 정보에서 사용

	// 생성자 주입을 사용할때 내가 AutoAppConfig 에 수동 주입을 시도하는 상황이다.
	// 이때 주입하려는 orderService 의 파라미터를 그냥 필드주입으로 설정해주는 상황임
	// 하지만 굳이 다른 방법있는데 이렇게 하기는 별로다...
	@Autowired MemberRepository memberRepository;
	@Autowired DiscountPolicy discountPolicy;

//	@Bean
//	OrderService orderService(MemberRepository memberRepository, DiscountPolicy discountPolicy) { 요렇게 해도 되는거라
//		return new OrderServiceImpl(memberRepository, discountPolicy);
//	}
	@Bean
	OrderService orderService() {
		return new OrderServiceImpl(memberRepository, discountPolicy);
	}
	@Bean(name = "memoryMemberRepository")
	MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
}
