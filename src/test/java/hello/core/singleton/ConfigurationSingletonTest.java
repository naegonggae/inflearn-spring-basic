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
		MemberRepository memberRepository = ac.getBean("MemberRepository", MemberRepository.class);
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

}
