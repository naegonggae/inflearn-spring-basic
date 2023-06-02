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

		@Bean // 약간 헷갈렸는데 networkClient 란 이름으로 밑에 주소 저장한 로직을 빈에 등록하는거임
		public NetworkClient networkClient() {
			NetworkClient networkClient = new NetworkClient();
			networkClient.setUrl("https://hello-spring.dev");
			return networkClient;
		}
	}

}
