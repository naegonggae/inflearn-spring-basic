package hello.core.singleton;

import static org.assertj.core.api.Assertions.*;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import java.lang.reflect.AnnotatedArrayType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

	@Test
	void configurationTest() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
		MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
		// 빈등록할때 MemberRepository 맨앞에 m을 대문자로 저장했더라 원래는 소문자로하는게 통상적임

		MemberRepository memberRepository1 = memberService.getMemberRepository();
		MemberRepository memberRepository2 = orderService.getMemberRepository();

		System.out.println("memberRepository1 = " + memberRepository1);
		System.out.println("memberRepository2 = " + memberRepository2);
		System.out.println("memberRepository = " + memberRepository);

		// 스프링이 어떤방법을 써서라도 싱글톤을 보장하는구나 ~~~
		assertThat(memberRepository1).isSameAs(memberRepository);
		assertThat(memberRepository2).isSameAs(memberRepository);
	}

	@Test
	void configurationDeep() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		AppConfig bean = ac.getBean(AppConfig.class);
		System.out.println("bean = " + bean.getClass());
		// 어떻게 스프링 컨테이너는 싱글톤을 보장할까
		// 빈에 등록된 AppConfig 의 클래스정보를 보면 AppConfig$$SpringCGLIB 라고 뜬다.
		// 이말은 그냥 AppConfig 가 저장된것이 아니고 스프링이 CGLIB 라는 바이트코드 조작 라이브러리를 사용해서
		// AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!
		// 이때 @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고,
		// 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.

	}

}
