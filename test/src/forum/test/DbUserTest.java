package forum.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import forum.po.DbUser;
import forum.service.DbUserService;


public class DbUserTest {
	private static ApplicationContext cxt;
	private static DbUserService dbUserService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cxt= new ClassPathXmlApplicationContext("resources/spring.xml");
		dbUserService=(DbUserService)cxt.getBean("dbUserServiceImpl");

	}
	
	@Test
	public void save(){
		DbUser user = new DbUser(); 
		user.setAccess(0);
		user.setUsername("bob");
		// "admin"经过MD5加密后  
		user.setPassword("21232f297a57a5a743894a0e4a801fc3");  
        user.setAccess(1);  
        dbUserService.save(user);
  
        DbUser user2 = new DbUser(); 
		user2.setAccess(0);
		user2.setUsername("user");
		// "user"经过MD5加密后  
		user2.setPassword("ee11cbb19052e40b07aac0ca060c23ee");  
        user2.setAccess(0);  
        dbUserService.save(user2);

	}
	
//	@Test
//	public void getById(){
//		DbUser user=dbUserService.getById(1);
//		System.out.println(user.getUsername());
//	}
	
//	@Test
//	public void getByName(){
//		DbUser user=dbUserService.getByName("bob");
//		System.out.println(user.getUsername());
//		System.out.println(user.getPassword());
//	}

}
