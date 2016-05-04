package com.project.framework.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.base.user.entity.User;
import com.project.framework.filter.WebContext;
import com.project.framework.service.ServiceManager;
import com.project.framework.util.FileNameTool;
import com.project.framework.util.ImageZoom;
import com.project.framework.util.StringUtil;
import com.project.framework.util.SystemConfig;

public class BaseController {

	@Resource
	protected ServiceManager serviceManager;

	/**
	 * 获取当前登录用户
	 * @return
	 * @author gql 2015-9-21 下午3:18:08
	 */
	protected User getCurrentUser(){
		return WebContext.getLoginUser();
	}
	
	/**
	 * 返回成功信息
	 * 
	 * @param msg
	 * @return
	 */
	protected Msg getSuccessMsg(String msg) {
		Msg successMsg = new Msg();
		successMsg.setSuccess(true);
		successMsg.setMessage(msg);
		return successMsg;
	}

	/**
	 * 返回失败信息
	 * 
	 * @param msg
	 * @return
	 */
	protected Msg getErrorMsg(String msg) {
		Msg successMsg = new Msg();
		successMsg.setSuccess(false);
		successMsg.setMessage(msg);
		return successMsg;
	}
	/**
	 * 返回成功信息
	 * 
	 * @param msg
	 * @return
	 */
	protected Msg getSuccessMsg(String msg,Object obj) {
		Msg successMsg = new Msg();
		successMsg.setSuccess(true);
		successMsg.setMessage(msg);
		successMsg.setObj(obj);
		return successMsg;
	}
	
	/**
	 * 返回失败信息
	 * 
	 * @param msg
	 * @return
	 */
	protected Msg getErrorMsg(String msg,Object obj) {
		Msg successMsg = new Msg();
		successMsg.setSuccess(false);
		successMsg.setMessage(msg);
		successMsg.setObj(obj);
		return successMsg;
	}
	/**
	 * 构建Page对象
	 * @param request
	 * @return
	 */
	protected <T> Page<T> buildPage(ServletRequest request){
		Page<T> page = new Page<T>(getPageNo(request), getPageSize(request));
		return page;
	}

	protected int getPageNo(ServletRequest request) {
		String pageNumber = request.getParameter("page");
		if (StringUtils.isNumeric(pageNumber)) {
			return Integer.parseInt(pageNumber);
		} else {
			return 1;
		}
	}
	
	protected int getPageSize(ServletRequest request) {
		String pageSize = request.getParameter("rows");
		if (pageSize != null && StringUtils.isNumeric(pageSize)) {
			return Integer.parseInt(pageSize);
		} else {
			return Page.DEFAULT_PAGE_SIZE;
		}
	}
	/**
	 * 利用response像前台writer消息
	 * 
	 * @param msg
	 * @param response
	 */
	protected void sendResponseMsg(String msg, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			Writer writer = response.getWriter();
			writer.write(msg);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 利用response 输出任何形式的流
	 * 
	 * @param bytes
	 *            字节
	 * @param response
	 */
	protected void sendResponseBytesXls(byte[] bytes,
			HttpServletResponse response,String fileName,String extName) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream");
			String name = fileName + "." + extName;
			response.setHeader("content-disposition", "attachment;filename="
					+ name);
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 如果返回true，则说明锁正常存在，不能继续执行,否则可以,并自动进行锁定
	 * 锁定请求，避免重复请求,锁定依据，同一个session调用同一个controller
	 * @return
	 * @author gql 2014-11-29 下午2:38:50
	 */
	public boolean lock() {
		String caller = getCaller();
		HttpServletRequest request = WebContext.getRequest();
		HttpSession session = null;
		if(null != request){
			session = request.getSession();
		}
		if(null != session){
			Long lockTime = (Long) session.getAttribute(caller);
			if(null != lockTime){
				if(System.currentTimeMillis()-lockTime>5*60*1000){
					session.setAttribute(caller, null);
					session.removeAttribute(caller);
					return false;
				}else{
					return true;
				}
			}else{
				session.setAttribute(caller, System.currentTimeMillis());
			}
		}
		return false;
	}
	/**
	 * 解除锁定
	 * @return
	 * @author gql 2014-11-29 下午3:06:26
	 */
	protected boolean unlock() {
		String caller = getCaller();
		HttpServletRequest request = WebContext.getRequest();
		HttpSession session = null;
		if(null != request){
			session = request.getSession();
		}
		if(null != session){
			session.setAttribute(caller, null);
			session.removeAttribute(caller);
			return true;
		}
		return false;
	}
	/**
	 * 获取调用者信息
	 * @return
	 * @author gql 2014-11-29 下午2:56:32
	 */
	private String getCaller(){
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		String caller = "temp_user_lock";
		if(null != stack && stack.length > 0){
			StackTraceElement st = stack[stack.length-1];
			caller = st.getClassName();
		}
		return caller;
	}
	/**
	 * 显示图片
	 * @param imgUrl 图片路径
	 * @param width  缩放宽度,为0或null为原图显示
	 * @param response
	 * @return
	 * @author zpr 2015-9-25 上午10:41:18
	 */
	@RequestMapping(value = "showFileImg")
	public String showFileImg(String imgUrl, Integer width,HttpServletResponse response) {
		if (width == null)
			width = 0;
		if (!StringUtil.isNullOrEmpty(imgUrl)) {
			
			//路径验证
			if(imgUrl.indexOf(SystemConfig.getValue("sys_path")+File.separator+SystemConfig.getValue("sys_file"))==-1){
				return null;
			}
			
			File f = new File(imgUrl);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/*");
			response.setHeader("content-disposition", "attachment;filename="+ f.getName());
			OutputStream writer = null;
			InputStream in = null;
			try {
				writer = response.getOutputStream();
				in = new FileInputStream(f);
				// 如果不缩放
				if (width == 0) {
					byte[] b = new byte[1024];
					int len = 0;
					while ((len = in.read(b)) != -1) {
						writer.write(b, 0, len);
					}
				} else {
					byte[] filecontent = new byte[(int) f.length()];
					in.read(filecontent);
					String extName = FileNameTool.readExtName(imgUrl);
					ImageZoom iz = new ImageZoom();
					byte[] imageByte = iz.zoomByWidth(filecontent, width,extName);
					writer.write(imageByte);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (writer != null) {
						writer.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
