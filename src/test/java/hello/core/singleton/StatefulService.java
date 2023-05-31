package hello.core.singleton;

public class StatefulService {
// 진짜 공유필드는 조심해야 한다! 스프링 빈은 항상 무상태(stateless)로 설계하자.
// 실무에서 실수 많이나오고 복구하기도 힘들다.
// 어떻게 해야하냐 그러면 공유되는 필드를 쓰지 않으면 된다.
//	private int price; // 상태를 유지하는 필드

	public int order(String name, int price) {
		System.out.println("name = " + name + " price = " + price);
//		this.price = price; // 여기서 문제!!!
		return price;
	}

//	public int getPrice() {
//		return price;
//	}

}
