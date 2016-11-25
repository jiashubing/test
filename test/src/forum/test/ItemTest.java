//package shubing.study.test;
//import java.util.Date;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import shubing.study.po.Items;
//import shubing.study.service.ItemsService;
//
//public class ItemTest {
//	private static ApplicationContext cxt;
//	private static ItemsService itemService;
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		cxt= new ClassPathXmlApplicationContext("resources/spring.xml");
//		itemService=(ItemsService)cxt.getBean("itemsServiceImpl");
//
//	}
//	@Test
//	public void save(){
//		for(int i=1;i<21;i++)
//		{
//			Items items=new Items();
//			items.setCreatetime(new Date());
//			items.setName("测试数据"+i);
//			items.setPic("图片位置"+i);
//			items.setDetail("测试细节"+i);
//			items.setPrice(30f+i);
//			itemService.save(items);
//		}
//	}
//	@Test
//	public void getById(){
//		Items item=itemService.getById(41);
//		System.out.println(item.getName());
//	}
//
//}
