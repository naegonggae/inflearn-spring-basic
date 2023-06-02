package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {

	private String url;

	public NetworkClient() {
		System.out.println("생성자 호출, url = " + url);
	}
	// 생성자 부분을 보면 url 정보 없이 connect가 호출되는 것을 확인할 수 있다.
	// 너무 당연한 이야기이지만 객체를 생성하는 단계에는 url이 없고,
	// 객체를 생성한 다음에 외부에서 수정자 주입을 통해서 setUrl() 이 호출되어야 url이 존재하게 된다.

	public void setUrl(String url) {
		this.url = url;
	}

	// 서비스 시작시 호출
	public void connect() {
		System.out.println("connect: " + url);
	}

	public void call(String message) {
		System.out.println("call: " + url + "message = " + message);
	}

	// 서비스 종료시 호출
	public void disconnect() {
		System.out.println("close: " + url);
	}


	// 의존관계 설정이 끝나면 호출
	@PostConstruct
	public void init() { // 의존관계 주입이 끝나면 호출해주겠다는 뜻
		System.out.println("NetworkClient.init");
		connect();
		call("초기화 연결 메세지");
	}

	// 종료 될 때 호출
	@PreDestroy
	public void close() {
		System.out.println("NetworkClient.close");
		disconnect();
	}

	// @PostConstruct, @PreDestroy 애노테이션 특징
	//최신 스프링에서 가장 권장하는 방법이다.
	//애노테이션 하나만 붙이면 되므로 매우 편리하다.
	//패키지를 잘 보면 javax.annotation.PostConstruct 이다. 스프링에 종속적인 기술이 아니라 JSR-250 라는 자바 표준이다. 따라서 스프링이 아닌 다른 컨테이너에서도 동작한다.
	//컴포넌트 스캔과 잘 어울린다.
	//유일한 단점은 외부 라이브러리에는 적용하지 못한다는 것이다. 외부 라이브러리를 초기화, 종료 해야 하면 @Bean의 기능을 사용하자.
}
