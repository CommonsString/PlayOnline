package tools.listenerAndFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class FilterSetEncode implements Filter{
	
	private String enCoding;
//	private Map<String , String> map = new HashMap<String , String>();
	
	@Override
	public void destroy() {
		enCoding = null;
		System.gc();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		if(enCoding.equals("") || enCoding != null){
			response.setCharacterEncoding(enCoding);
		}else{
			response.setCharacterEncoding("utf-8");
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		//获取参数
System.out.println("拦截参数  ：" + config.getInitParameter("enCoding"));
		this.enCoding = config.getInitParameter("enCoding");
	}

}
