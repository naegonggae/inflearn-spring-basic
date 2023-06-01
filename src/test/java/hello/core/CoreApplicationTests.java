package hello.core;

import hello.core.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

	// 여기서는 누가 가져다 쓸 것도 아니고 테스트할때만 사용하니 입맛대로 커스텀해서 사용하라는 느낌
	@Autowired
	OrderService orderService;
	@Test
	void contextLoads() {
//		orderService 테스트로직
	}

}
