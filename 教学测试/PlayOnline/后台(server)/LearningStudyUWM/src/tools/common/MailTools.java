package tools.common;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;


/**
 * 邮件激活
 * @author MyPC
 *
 */
public class MailTools {
	
	
	@Test
	public void test(){
		sendMail("commonsstring@yeah.net", "哈哈");
	}
	

	/**
	 * 
	 * @param name
	 * @param content
	 */
	public static void sendMail(String name, String content){
		
		Properties prop = new Properties();
		
//		prop.put("mail.transport.protocol", "SMTP" );
		prop.put("mail.smtp.host", "smtp.163.com");
		prop.put("mail.smtp.auth", "true");
		
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");  
        prop.setProperty("mail.smtp.port", "465");  
        prop.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("m838206069@163.com", "wanmcdmmaa123"); //发送邮件的账号和密码
			}
		});
		
		
		//创建邮件对象
		Message message = new MimeMessage(session);
		
		try {
			//设置发件人
			message.setFrom(new InternetAddress("m838206069@163.com"));
			//设置收件人
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(name));
			//设置主题
			message.setSubject("状元王激活账号： ");
			//设置邮件正文
			message.setContent(content, "text/html;charset=UTF-8");
			
			Transport.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	/**
	 * @param url
	 * @param activeCode
	 * @return
	 */
	public static StringBuffer sendFormat(String url, String activeCode){
		StringBuffer content = new StringBuffer();
		content.append("尊敬的用户，为了保障您的账号能正常使用，请点击链接激活账号:<br />");
		content.append("<a href=\"");
		content.append(url);
		content.append("?method=active&activeCode=");
		content.append(activeCode);
		content.append("\">");
		content.append(CommonUtils.uuid().toLowerCase() + CommonUtils.uuid());
		content.append("</a>");	
		
		return content;
	}
	
	
	
	
}
