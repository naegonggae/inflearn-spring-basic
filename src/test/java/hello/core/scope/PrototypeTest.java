package hello.core.scope;

import hello.core.scope.SingletonTest.singletonBean;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

	@Test
	void prototypeBeanTest() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
				PrototypeBean.class);

		System.out.println("prototypeBean1");
		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		System.out.println("prototypeBean2");
		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

		System.out.println("prototypeBean1 = " + prototypeBean1);
		System.out.println("prototypeBean2 = " + prototypeBean2);

		Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

		prototypeBean1.destroy();
		prototypeBean2.destroy();
		ac.close(); // 디스트로이가 출력이 안됨 필요하다면 직접 호출해야돼

	}

	// 프로토타입 빈의 특징 정리
	//스프링 컨테이너에 요청할 때 마다 새로 생성된다.
	//스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입 그리고 초기화까지만 관여한다.
	//종료 메서드가 호출되지 않는다.
	//그래서 프로토타입 빈은 프로토타입 빈을 조회한 클라이언트가 관리해야 한다. 종료 메서드에 대한 호출도
	//클라이언트가 직접 해야한다.

	@Scope("prototype")
	// 여기 component 안했는데 어떻게 new AnnotationConfigApplicationContext() 에 넣을 수 있나?
	// new AnnotationConfigApplicationContext() 여기 안에 들어가는 순간 component 의 대상이 된다 생각해
	static class PrototypeBean {

		@PostConstruct
		public void init() {
			System.out.println("prototype.init");
		}

		@PreDestroy
		public void destroy() {
			System.out.println("prototype.destroy");
		}
	}
}
