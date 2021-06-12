package itc.hoseo.springproject;

import itc.hoseo.springproject.service.InitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InitServiceTests {

	@Autowired
	InitService initService;

	@Test
	void contextLoads() throws Exception{
		initService.initRestaurantsByYogiyo();
	}

}
