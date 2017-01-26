package pw.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pw.web.app.model.LabelColor;

@Repository
public class LabelColorDAO implements LabelColorDataAccess {

	public LabelColor getLabelColor(String value) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LabelColor.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		LabelColor labelColor;
		
		try {		
			session.beginTransaction();

			labelColor = (LabelColor) session.createQuery("from LabelColor where value = :value")
										.setParameter("value", value)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return labelColor;
	}
	
	public LabelColor getLabelColor(int id) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LabelColor.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		LabelColor labelColor;
		
		try {		
			session.beginTransaction();

			labelColor = (LabelColor) session.createQuery("from LabelColor where id = :id")
										.setParameter("id", id)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return labelColor;
	}
	
	public List<LabelColor> getAllLabelColors() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LabelColor.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<LabelColor> labelColors = new ArrayList<LabelColor>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<LabelColor> tQuery = session.createQuery("from LabelColor", LabelColor.class);
			labelColors = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return labelColors;
	}

	public void createLabelColor(LabelColor labelColor) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LabelColor.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(labelColor);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updateLabelColor(LabelColor labelColor) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LabelColor.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("update LabelColor set name = :name where id = :id")
					.setParameter("name", labelColor.getName())
					.setParameter("id", labelColor.getId())
					.executeUpdate();
			session.createQuery("update LabelColor set value = :value where id = :id")
					.setParameter("value", labelColor.getValue())
					.setParameter("id", labelColor.getId())
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deleteLabelColor(int id) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(LabelColor.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from LabelColor where id = :id")
					.setParameter("id", id)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
