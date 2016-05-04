package com.project.framework.init;


import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.base.user.entity.User;
import com.project.framework.service.ServiceManager;
import com.project.framework.util.security.MD5Util;


/**
 * 初始化完成之后执行
 * @author Nice
 */
@Service
public class InitService implements ApplicationListener<ContextRefreshedEvent> {
	
	private Logger logger = LoggerFactory.getLogger(InitService.class);
	@Resource
	private ServiceManager serviceManager;
	/**
	 * 当初始化完成后，将触发事件，调用以下代码。SpringMVC所以与Spring初始化完成后都会调用，所以增加判断
	 */
	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null){
			boolean flag = serviceManager.userService.isHasUser();
			if(!flag){
				User user = new User();
				user.setUsername("admin");
				user.setPassword(MD5Util.MD5Encode("xx1111"));
				serviceManager.userService.save(user);
				logger.info("成功创建了初始用户.账户名为:admin");
			}
		}
	}
}
