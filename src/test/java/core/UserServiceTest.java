package core;

import org.junit.Test;
import com.project.base.user.entity.User;

/**  
 * 用户业务层测试类
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月5日 下午2:22:26
 */
public class UserServiceTest extends BaseTest{
	 @Test
     public void saveUser(){
		 User user = new User();
		 user.setUsername("我是测试用户");
		 user.setPassword("ceshineirong");
    	 serviceManager.userService.save(user);
     }
}
