package pw.web.app.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pw.web.app.model.FileResource;

public interface FileResourceDataAccess {
	public void uploadFile(MultipartFile multipartFile) throws Exception;
	
	public List<FileResource> getAllFiles() throws Exception;
	
	public List<FileResource> getPaginatedFiles(int offset, int count) throws Exception;
	
	public FileResource getFile(String name) throws Exception;
	
	public void deleteFile(String name) throws Exception;
}
