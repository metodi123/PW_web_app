package pw.web.app.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pw.web.app.model.ImageResource;

public interface ImageResourceDataAccess {
	public void uploadImage(MultipartFile multipartFile) throws Exception;
	
	public List<ImageResource> getAllImages() throws Exception;
	
	public List<ImageResource> getPaginatedImages(int offset, int count) throws Exception;
	
	public ImageResource getImage(String name) throws Exception;
	
	public void deleteImage(String name) throws Exception;
}
