package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {

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
	@Override
	public void afterPropertiesSet() throws Exception { // 의존관계 주입이 끝나면 호출해주겠다는 뜻
		System.out.println("NetworkClient.afterPropertiesSet");
		connect();
		call("초기화 연결 메세지");
	}

	// 종료 될 때 호출
	@Override
	public void destroy() throws Exception {
		System.out.println("NetworkClient.destroy");
		disconnect();
	}

	// 초기화, 소멸 인터페이스 단점
	// 이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
	// 초기화, 소멸 메서드의 이름을 변경할 수 없다.
	// 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
}
