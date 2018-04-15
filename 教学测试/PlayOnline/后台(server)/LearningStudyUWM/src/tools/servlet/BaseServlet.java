package tools.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//处理
@SuppressWarnings("serial")
public class BaseServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//处理编码
		if(request.getMethod().equalsIgnoreCase("get")){
			if(!(request instanceof GetRequest)){
				request = new GetRequest(request); //修改编码，重写方法
 			}
		}else{
			request.setCharacterEncoding("utf-8"); //post请求编码
		}
		
		//获取方法名
		String methodName = request.getParameter("method");
//System.out.println("method : " + methodName);
		Method method = null;
		
			try {
				method = this.getClass().getMethod(methodName, HttpServletRequest.class , HttpServletResponse.class);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			
			//反射调用  方法的返回值
 			try {
				@SuppressWarnings("unused")
				String path = (String)method.invoke(this, request , response);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
	}
}
