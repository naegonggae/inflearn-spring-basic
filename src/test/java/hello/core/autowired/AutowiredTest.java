package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiredTest {

	@Test
	void AutowiredOption() {

		ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
	}

	static class TestBean {

		// 호출 안됨
		@Autowired(required = false) // 자동 주입할 대상이 없으면 호출을 안함 Member 는 빈 등록이 안되어있으니까
		public void setNoBean1(Member member) {
			System.out.println("setNoBean1 = " + member);
		}
		//null 호출
		@Autowired
		public void setNoBean2(@Nullable Member member) {
			System.out.println("setNoBean2 = " + member);
		}
		//Optional.empty 호출
		@Autowired(required = false)
		public void setNoBean3(Optional<Member> member) {
			System.out.println("setNoBean3 = " + member);
		}

		// 참고: @Nullable, Optional은 스프링 전반에 걸쳐서 지원된다.
		// 예를 들어서 생성자 자동 주입에서 특정 필드에만 사용해도 된다.


	}

}
