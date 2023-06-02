package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

	@Test
	void lifeCycleTest() {
		ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(
				LifeCycleConfig.class);
		NetworkClient bean = ac.getBean(NetworkClient.class);
		ac.close();

	}
	// 스프링은 생성자 주입방법 제외하고 객체 생성을 먼저하고 의존관계를 주입한다.

	@Configuration
	static class LifeCycleConfig {

		// 약간 헷갈렸는데 networkClient 란 이름으로 밑에 주소 저장한 로직을 빈에 등록하는거임
		@Bean // (initMethod = "init", destroyMethod = "close")
		// 추론 기능을 사용하기 싫으면 destroyMethod="" 처럼 빈 공백을 지정하면 된다.
		// 디폴트 이면 알아서 close 나 shutdown 이름을 찾아서 호출해준다.
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("https://hello-spring.dev");
			return networkClient;
		}
	}

	// 설정 정보 사용 특징
	// 메서드 이름을 자유롭게 줄 수 있다.
	// 스프링 빈이 스프링 코드에 의존하지 않는다.
	// 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.

}
