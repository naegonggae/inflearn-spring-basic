package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
		excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
		// AppConfig 를 등록시키지 않기 위해 설정 Configuration 붙은거는 제외하겠다는 말임
)
public class AutoAppConfig {
	// 컴포넌트 스캔은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
	// 등록할때 빈 객체 이름은 클래스 네임에서 맨앞글자만 소문자로 바꾼것, 다른 이름으로 설정할 수도 있음
	// @Autowired 의 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.

}
