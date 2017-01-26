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

import pw.web.app.model.ImageResource;

@Repository
public class ImageResourceDAO implements ImageResourceDataAccess {

	public void uploadImage(MultipartFile multipartFile) throws IOException {
	
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ImageResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
	
	    session.beginTransaction();
	
	    InputStream inputStream = null;
        
        if (multipartFile != null) {
            inputStream = multipartFile.getInputStream();
        }
        
        ImageResource imageResource = new ImageResource();
        imageResource.setData(IOUtils.toByteArray(inputStream));
        imageResource.setName(multipartFile.getOriginalFilename());
	
	    session.save(imageResource);
	
	    session.getTransaction().commit();
	}
	
	public List<ImageResource> getAllImages() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ImageResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<ImageResource> images = new ArrayList<ImageResource>();
		
		try {
			session.beginTransaction();
			
			TypedQuery<ImageResource> tQuery = session.createQuery("from ImageResource", ImageResource.class);
			images = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return images;
	}
	
	public List<ImageResource> getPaginatedImages(int offset, int count) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ImageResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<ImageResource> images = new ArrayList<ImageResource>();
		
		try {
			session.beginTransaction();
			
			TypedQuery<ImageResource> tQuery = session.createQuery("from ImageResource image ORDER BY image.id DESC", ImageResource.class)
														.setFirstResult(offset)
														.setMaxResults(count);
			images = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return images;
	}
	
	public ImageResource getImage(String name) {
		
	    SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ImageResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		ImageResource imageResource;
		
		try {		
			session.beginTransaction();

			imageResource = (ImageResource) session.createQuery("from ImageResource where name = :name")
													.setParameter("name", name)
													.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return imageResource;
	}
	
	public void deleteImage(String name) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(ImageResource.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from ImageResource where name = :name")
					.setParameter("name", name)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
