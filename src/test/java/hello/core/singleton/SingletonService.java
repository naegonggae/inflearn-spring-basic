package hello.core.singleton;
// 사실 싱글톤 패턴을 설계하는 방법은 여러가지지만 이렇게 JVM 이 동작할때 바로 static 인스턴스를 생성하는것이 안전한 방법이라 할 수 있다.

// 싱글톤에는 여러 문제점이 있는데 이걸 스프링 컨테이너는 싱글톤을 쓰면서 문제점들은 전부 커버해준다.
public class SingletonService {

	// JVM 이 작동할때 static 영역에 new 가 있으면 내부에서 자기자신 객체를 하나 생성해 instance 에 넣어준다.
	private static final SingletonService instance = new SingletonService();

	// 조회할때 사용
	public static SingletonService getInstance() {
		return instance;
	}

	// private 생성자를 만들면 여기 외의 다른 class 에서 인스턴스를 만들지 못한다. 다시말해 new 를 해서 객체를 생성하지 못한다.
	private SingletonService() {
	}

	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");
	}

	// 1. static 영역에 객체 instance 를 미리 하나 생성해서 올려둔다.
	// 2. 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회할 수 있다. 이 메서드를 호출하면 항상 같은 인스턴스를 반환한다.
	// 3. 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private 으로 막아서 혹시라도 외부에서 new 키워드로 객체 인스턴스가 생성되는 것을 막는다.

	// Tmi 좋은 설계는 컴파일 오류로 오류를 잡게 하는것이다.
}
