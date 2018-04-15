package tools.common;

import java.util.Date;

import com.xmlbean.Users;

public class Comments {
	
	private Users user;
	private String content;
	private Date date;
	
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
