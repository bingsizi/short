package core;
import javax.annotation.Resource;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.project.framework.service.ServiceManager;

/**  
 * 测试类基类
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月5日 下午2:09:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@ActiveProfiles("production")
@Transactional
@Rollback(true)
public class BaseTest{
	@Resource
	public ServiceManager serviceManager;
}
