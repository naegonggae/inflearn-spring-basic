package hello.core.beanfind;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextBasicFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

	@Test
	@DisplayName("빈 이름으로 조회하기")
	void findBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}

	@Test
	@DisplayName("이름없이 타입으로만 조회하기")
	void findBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}

	@Test
	@DisplayName("구체 타입으로 조회하기")
	void findBeanByName2() {
		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	} // 빈등록할때 memberService 로 지정되어있지만 인스턴스는 MemberServiceImpl 이 형태를 가진다.
	  // 그래서 위의 테스트가 통과로 나오긴하지만 옳바른 방법이라고는 볼 수 없다. 왜냐면 역할에 접근해야지 구체에 접근하면 안되기때문이다.
	  // 구체타입으로 조회하면 유연성이 떨어진다.

	@Test
	@DisplayName("빈 이름으로 조회X")
	void findBeanByNameX() {
		// 실패 테스트 만들때 팁 그냥 실행시켜보고 어떤 오류 나는지 볼 수 있다.
//		MemberService xxxx = ac.getBean("xxxx", MemberService.class);
		assertThrows(NoSuchBeanDefinitionException.class,
				() -> ac.getBean("xxxx", MemberService.class));
	}
}
