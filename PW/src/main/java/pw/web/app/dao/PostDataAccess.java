package pw.web.app.dao;

import java.util.List;

import pw.web.app.model.Post;

public interface PostDataAccess {
	public Post getPost(int id) throws Exception;
	
	public List<Post> getAllPosts() throws Exception;
	
	public List<Post> getPaginatedPosts(int offset, int count) throws Exception;

	public void createPost(Post post) throws Exception;
	
	public void updatePost(Post post) throws Exception;
	
	public void deletePost(int id) throws Exception;
}
