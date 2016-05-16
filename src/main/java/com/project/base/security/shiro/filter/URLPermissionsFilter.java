package com.project.base.security.shiro.filter;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**  
 * 基于URL的权限判断过滤器
 * @company 新龙科技
 * @author zhangpeiran
 * @version 
 * @date 2016年5月10日 下午2:18:58
 */
public class URLPermissionsFilter extends PermissionsAuthorizationFilter{

	private final Logger log = LoggerFactory.getLogger(URLPermissionsFilter.class);
	
	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
		String[] permissions = buildPermissions(request);
		boolean flag = super.isAccessAllowed(request, response, permissions);
		log.info("要验证的url地址为:"+Arrays.toString(permissions)+",验证结果为:"+flag);
		return flag;
	}
    /** 
     * 根据请求URL产生权限字符串，这里只产生，而比对的事交给Realm 
     * @param request 
     * @return 
     */  
    protected String[] buildPermissions(ServletRequest request) {  
        String[] perms = new String[1];  
        HttpServletRequest req = (HttpServletRequest) request;  
        String path = req.getServletPath();  
        int index = path.indexOf("?");
    	if(index!=-1){
    		path = path.substring(0,path.indexOf("?"));
    	}
        perms[0] = path;//path直接作为权限字符串  
        return perms;  
    }
}
