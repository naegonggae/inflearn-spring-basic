package hello.core.xml;

import static org.assertj.core.api.Assertions.*;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aot.generate.GenerationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class XmlAppContext {

	@Test
	void xmlAppContext() {
		// xml 로 설정해보기
		ApplicationContext ac = new GenericXmlApplicationContext(
				"appConfig.xml");
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		assertThat(memberService).isInstanceOf(MemberService.class);
	}

}
