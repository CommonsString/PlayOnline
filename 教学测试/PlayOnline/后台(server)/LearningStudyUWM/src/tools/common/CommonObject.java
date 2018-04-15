package tools.common;

import com.xmlbean.Admin;
import com.xmlbean.FileGroup;

/**
 * 组合类
 */
public class CommonObject {
	
	private FileGroup fileGroup;
	private Admin admin;
	
	public FileGroup getFileGroup() {
		return fileGroup;
	}
	public void setFileGroup(FileGroup fileGroup) {
		this.fileGroup = fileGroup;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
