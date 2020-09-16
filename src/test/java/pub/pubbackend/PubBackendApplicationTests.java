package pub.pubbackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pub.pubbackend.controller.AuthController;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PubBackendApplicationTests {
	@Autowired
	private AuthController authController;

	@Test
	void contextLoads() throws Exception {

		//TEST EMAIL SENDING
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", "doragirl96@gmail.com");
		data.put("message", "easdais gdiasgidugasi");

		authController.sendNotification(data);
		System.out.println(24);
	}

}
