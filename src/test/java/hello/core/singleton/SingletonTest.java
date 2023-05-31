package hello.core.singleton;

import static org.assertj.core.api.Assertions.*;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

	@Test
	@DisplayName("스프링 없는 순수한 DI 컨테이너")
	void pureContainer() {
		AppConfig appConfig = new AppConfig();
		// 조회 1 : 호출 할때마다 객체를 생성
		MemberService memberService1 = appConfig.memberService();

		// 조회 2
		MemberService memberService2 = appConfig.memberService();

		// 참조값이 다른것을 확인
		System.out.println("memberService1 = " + memberService1);
		System.out.println("memberService2 = " + memberService2);
		// 요청을 할때마다 새로운 객체가 생성됨 (사실 memberServiceImpl 에 memberRepo 까지 꼬리를 물고 객체가 생성됨)
		// 근데 웹어플리케이션은 고객요청이 엄청 많음...

		// memberService1 은 memberService2 다르다
		assertThat(memberService1).isNotSameAs(memberService2);

		// 우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig 는 요청을 할 때 마다 객체를 새로 생성한다.
		// 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.
		// 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. -> 싱글톤 패턴
	}

	@Test
	@DisplayName("싱글톤 객체를 적용한 객체 사용")
	void SingletonServiceTest() {
		// new SingletonService();
		// java: SingletonService() has private access in hello.core.singleton.SingletonService

		SingletonService singletonService1 = SingletonService.getInstance();
		SingletonService singletonService2 = SingletonService.getInstance();

		System.out.println("singletonService1 = " + singletonService1);
		System.out.println("singletonService2 = " + singletonService2);
		// 같은 인스턴스 객체를 생성함
		// singletonService1 = hello.core.singleton.SingletonService@1dd92fe2
		// singletonService2 = hello.core.singleton.SingletonService@1dd92fe2

		assertThat(singletonService1).isSameAs(singletonService2);
		// 그럼 이제 AppConfig 를 static 에 객체하나 생성하게하고 조회하는 메서드를 만들어서 싱글톤 패턴처럼 리펙토링해야하나?
		// 그럴 필요없다. 스프링 컨테이너를 쓰면 알아서 해준다..

		// isSameAs : 메모리상 같은 객체를 가리키는지 비교 (주소 비교)
		// isEqualTo : 객체가 같은 값을 가지고 있는지 비교한다.
	}

	@Test
	@DisplayName("스프링 컨테이너와 싱글톤")
	void springContainer() {
//		AppConfig appConfig = new AppConfig();
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		MemberService memberService1 = ac.getBean("memberService", MemberService.class);
		MemberService memberService2 = ac.getBean("memberService", MemberService.class);

		// 같은게 나오는게 신기하다.
		System.out.println("memberService1 = " + memberService1);
		System.out.println("memberService2 = " + memberService2);

		assertThat(memberService1).isSameAs(memberService2);
	}

}
