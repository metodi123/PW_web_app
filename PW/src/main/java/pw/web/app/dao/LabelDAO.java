package pw.web.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import pw.web.app.model.Label;

@Repository
public class LabelDAO implements LabelDataAccess {
	
	public Label getLabel(int id) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		Label label;
		
		try {
			session.beginTransaction();

			label = (Label) session.createQuery("from Label where id = :id")
										.setParameter("id", id)
										.getSingleResult();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return label;
	}
	
	public List<Label> getAllLabels() {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		List<Label> labels = new ArrayList<Label>();
		
		try {	
			session.beginTransaction();
			
			TypedQuery<Label> tQuery = session.createQuery("from Label label ORDER BY label.text ASC", Label.class);
			labels = tQuery.getResultList();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
		
		return labels;
	}

	public void createLabel(Label label) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			session.beginTransaction();
				
			session.save(label);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void updateLabel(Label label) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.update(label);
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
	public void deleteLabel(int id) {
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Label.class).buildSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.createQuery("delete from Label where id = :id")
					.setParameter("id", id)
					.executeUpdate();
			
			session.getTransaction().commit();
		}
		finally {
			factory.close();
		}
	}
	
}
