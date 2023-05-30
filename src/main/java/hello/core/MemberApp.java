package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

	// 순수 자바로 테스트한것임 스프링은 사용되지 않았다.
	// 이렇게 테스트하는것은 효율적인 방법이 아니니 테스트 코드를 작성하자
	public static void main(String[] args) {
//		AppConfig appConfig = new AppConfig();
//		MemberService memberService = appConfig.memberService();

		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		// 이렇게 적어주면 AppConfig에 있는 bean들을 다 스프링 컨테이너에 넣어줌
		MemberService memberService = applicationContext.getBean("memberService",
				MemberService.class);

		Member member = new Member(1L, "Member1", Grade.VIP);
		memberService.join(member);

		Member findMember = memberService.findMember(1L);

		System.out.println("new member = " + member.getName());
		System.out.println("find Member = " + findMember.getName());
	}

}
