package hello.core.singleton;

import static org.assertj.core.api.Assertions.*;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
