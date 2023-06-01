package hello.core.scan;

import static org.assertj.core.api.Assertions.*;

import hello.core.AutoAppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

	@Test
	void basicScan() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
				AutoAppConfig.class);

		MemberService memberService = ac.getBean(MemberService.class);
		assertThat(memberService).isInstanceOf(MemberService.class);

		// 필드 주입으로 빈 등록이 잘 됐는지 확인
		OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
		MemberRepository memberRepository = bean.getMemberRepository();
		System.out.println("memberRepository = " + memberRepository);
	}

}
