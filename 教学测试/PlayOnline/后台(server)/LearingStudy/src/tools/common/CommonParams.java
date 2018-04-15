package tools.common;

public interface CommonParams {
	
	static final long serialVersionUID = 1L; //序列化ID
	static final String UPLOAD_DIRECTORY = "upload"; //上传文件存储目录
	static final String UPLOAD_PICTURE = "images";
	static final String UPLOAD_VIDEO = "videos";
	
	static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; //3MB
	static final int MAX_FILE_SIZE = 1024 * 1024 * 1000; //1GMB
	static final int MAX_REQUEST_SIZE = 1024 * 1024 * 1000; //1GB
	
	static final String[] TYPE_VIDEO = {"mp4"};
	static final String[] TYPE_PICTURE = {"jpg" , "jpeg" , "png"};
	
}
