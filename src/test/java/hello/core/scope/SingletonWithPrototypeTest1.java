package hello.core.scope;

import static org.assertj.core.api.Assertions.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest1 {

	@Test
	void prototypeFind() {

		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
				PrototypeBean.class);

		PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
		prototypeBean1.addCount();
		assertThat(prototypeBean1.getCount()).isEqualTo(1);

		PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
		prototypeBean2.addCount();
		assertThat(prototypeBean2.getCount()).isEqualTo(1);
	}

	@Test
	void singletonClientUsePrototype() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
				ClientBean.class, PrototypeBean.class);

		ClientBean clientBean1 = ac.getBean(ClientBean.class);
		int logic1 = clientBean1.logic();
		assertThat(logic1).isEqualTo(1);

		ClientBean clientBean2 = ac.getBean(ClientBean.class);
		int logic2 = clientBean2.logic();
		assertThat(logic2).isEqualTo(2);

		System.out.println("clientBean1 = " + clientBean1);
		System.out.println("clientBean2 = " + clientBean2);
		assertThat(clientBean1).isSameAs(clientBean2);


	}

	@Scope("singleton")
	static class ClientBean {
		private final PrototypeBean prototypeBean;
		// 이 프로토타입은 싱글톤 생성 시점에 주입이 된거야 그래서 프로토타입이지만 싱글톤처럼 계속 같은걸써

		@Autowired
		public ClientBean(PrototypeBean prototypeBean) {
			this.prototypeBean = prototypeBean;
		}

		public int logic() {
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}

	@Scope("prototype")
	static class PrototypeBean {
		private int count = 0;
		public void addCount() {
			count++;
		}

		public int getCount() {
			return count;
		}

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init" + this);
		}

		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}

	}

}
