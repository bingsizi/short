package com.project.base.log.service;

import javax.annotation.Resource;
import com.project.base.log.entity.Log;
import com.project.framework.dao.BaseDao;
import com.project.framework.service.BaseServiceImpl;

/**
 * 
 * @company 新龙科技
 * @author zhangpeiran
 * @version
 * @date 2016年5月5日 下午1:26:11
 */
public class LogService extends BaseServiceImpl<Log, Long> {
	
	@Resource(name = "logDao")
	public void setBaseDao(BaseDao<Log, Long> baseDao) {
		this.baseDao = baseDao;
	}

}
