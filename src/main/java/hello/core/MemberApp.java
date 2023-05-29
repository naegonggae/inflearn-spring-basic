package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

	// 순수 자바로 테스트한것임 스프링은 사용되지 않았다.
	// 이렇게 테스트하는것은 효율적인 방법이 아니니 테스트 코드를 작성하자
	public static void main(String[] args) {
		AppConfig appConfig = new AppConfig();
		MemberService memberService = appConfig.memberService();

//		MemberService memberService = new MemberServiceImpl();
		Member member = new Member(1L, "Member1", Grade.VIP);
		memberService.join(member);

		Member findMember = memberService.findMember(1L);

		System.out.println("new member = " + member.getName());
		System.out.println("find Member = " + findMember.getName());
	}

}
