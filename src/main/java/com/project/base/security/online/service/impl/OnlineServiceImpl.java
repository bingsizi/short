package com.project.base.security.online.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.stereotype.Service;
import com.project.base.security.online.entity.Online;
import com.project.base.security.online.service.OnlineService;
import com.project.base.security.user.entity.User;
import com.project.framework.common.Constants;

/**
 * 在线业务实现类
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月16日 上午8:40:43
 */
@Service("onlineService")
public class OnlineServiceImpl implements OnlineService{
	@Resource
	private SessionDAO sessionDAO;
	
	/**
	 * 返回在线用户信息
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午9:22:57
	 */
	public List<Online> findAllSession(){
		List<Online> onlineList = new ArrayList<>();
		Collection<Session> sessionList = sessionDAO.getActiveSessions();
		Iterator<Session> it = sessionList.iterator();
		while(it.hasNext()){
			Session session = it.next();
			User user = (User)session.getAttribute(Constants.USER_IN_SESSION);
			if(user!=null){
				Online online = new Online();
				online.setSessionId(session.getId());
				online.setUserId(user.getId());
				online.setUsername(user.getUsername());
				online.setRealName(user.getRealName());
				online.setStartTimestamp(session.getStartTimestamp());
				online.setLastAccessTime(session.getLastAccessTime());
				onlineList.add(online);
			}
		}
		return onlineList;
	}
	/**
	 * 返回在线session数
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午9:23:39
	 */
	public int countSession(){
		return  sessionDAO.getActiveSessions().size();
	}
}
