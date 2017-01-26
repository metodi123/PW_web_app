package pw.web.app.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import pw.web.app.model.FileResource;

@Repository
public class FileResourceDAO implements FileResourceDataAccess {

	public void uploadFile(MultipartFile multipartFile) throws IOException {
		
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FileResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
	
	    session.beginTransaction();
	
	    InputStream inputStream = null;
        
        if (multipartFile != null) {
            inputStream = multipartFile.getInputStream();
        }
        
        FileResource fileResource = new FileResource();
        fileResource.setData(IOUtils.toByteArray(inputStream));
        fileResource.setName(multipartFile.getOriginalFilename());
	
	    session.save(fileResource);
	
	    session.getTransaction().commit();
	}
	
	public List<FileResource> getAllFiles() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FileResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<FileResource> files = new ArrayList<FileResource>();
		
		try {
			session.beginTransaction();
			
			TypedQuery<FileResource> tQuery = session.createQuery("from FileResource", FileResource.class);
			files = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return files;
	}
	
	public List<FileResource> getPaginatedFiles(int offset, int count) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FileResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<FileResource> files = new ArrayList<FileResource>();
		
		try {
			session.beginTransaction();
			
			TypedQuery<FileResource> tQuery = session.createQuery("from FileResource file ORDER BY file.id DESC", FileResource.class)
														.setFirstResult(offset)
														.setMaxResults(count);
			files = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return files;
	}
	
	public FileResource getFile(String name) {
		
	    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FileResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		FileResource fileResource;
		
		try {		
			session.beginTransaction();

			fileResource = (FileResource) session.createQuery("from FileResource where name = :name")
													.setParameter("name", name)
													.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return fileResource;
	}
	
	public void deleteFile(String name) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(FileResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from FileResource where name = :name")
					.setParameter("name", name)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
