package cn.vpclub.mockmvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeMethod;


@SpringBootTest
public class UserDemoControllerTests {

//	private UserService userService;

	@BeforeMethod
	public void setUp() {
//		userService = PowerMockito.mock(UserService.class);
//
//		RestAssuredMockMvc.standaloneSetup(new UserController(userService));
	}

}
