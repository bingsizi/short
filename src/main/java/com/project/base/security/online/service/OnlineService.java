package com.project.base.security.online.service;

import java.util.List;
import com.project.base.security.online.entity.Online;

/**
 * 在线业务接口
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月16日 上午8:40:43
 */
public interface OnlineService {
	
	/**
	 * 返回在线用户信息
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午9:22:57
	 */
	public List<Online> findAllSession();
	/**
	 * 返回在线session数
	 * @return
	 * @author zhangpeiran 2016年5月16日 上午9:23:39
	 */
	public int countSession();
}
