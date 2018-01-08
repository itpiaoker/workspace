//package cn.com.linnax.file.test;
//
//import cn.com.linnax.file.dao.FileinfoMapper;
//import cn.com.linnax.file.model.Fileinfo;
//import cn.com.linnax.file.model.FileinfoExample;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/spring-beans.xml","/applicationContext.xml"})
//public class FileDaoTest {
//	@Autowired
//	FileinfoMapper fileinfoMapper;
//	@Test
//	public void test() {
//		FileinfoExample example = new FileinfoExample();
//		example.setLimitStart(0);
//		example.setLimitEnd(1000);
//		example.createCriteria().andNameLike("%u%");
//		List<Fileinfo> list = fileinfoMapper.selectByExample(example);
//		//for (Fileinfo fileinfo : list) {
//			System.out.println(list);
//		//}
//	}
//
//}
