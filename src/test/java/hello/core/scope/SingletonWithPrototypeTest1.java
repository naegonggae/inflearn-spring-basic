package hello.core.scope;

import static org.assertj.core.api.Assertions.*;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
		assertThat(logic2).isEqualTo(1);

	}

	@Scope("singleton")
	static class ClientBean {

		@Autowired
		private ObjectProvider<PrototypeBean> prototypeBeansProvider;
		// 이정도는 스프링이 알아서 빈 등록해준다함

		public int logic() {
			PrototypeBean prototypeBean = prototypeBeansProvider.getObject();
			// ApplicationContext 를 직접 호출하지 않고 찾아만 주는것임
			// ObjectProvider 의 getObject() 를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL)
			prototypeBean.addCount();
			int count = prototypeBean.getCount();
			return count;
		}
	}

//	@Scope("singleton") // 이렇게하면 프로토타입의 기능을 수행할 수 있다.
//	static class ClientBean {
//		@Autowired private ApplicationContext ac;
//
//		public int logic() {
//			PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//			prototypeBean.addCount();
//			int count = prototypeBean.getCount();
//
//			return count;
//		}
//	}

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
