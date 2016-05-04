package com.project.framework.context;

import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import com.project.framework.filter.WebContext;



/**   
 * TODO spring容器启动执行
 * @date 2013-8-21 上午11:35:13
 * 
 */
public class ApplicationContextLoaderListener extends ContextLoaderListener {

	private final Logger log = Logger.getLogger(ApplicationContextLoaderListener.class);
	/* (non-Javadoc)
	 * @see org.springframework.web.context.ContextLoader#initWebApplicationContext(javax.servlet.ServletContext)
	 */
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		try {
			WebContext.setServletContext(servletContext);
			// TODO Auto-generated catch block
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			System.exit(1);
		}
		return super.initWebApplicationContext(servletContext);
	}
}
