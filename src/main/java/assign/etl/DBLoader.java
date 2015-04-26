package assign.etl;

import assign.domain.Meeting;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DBLoader {
	private SessionFactory sessionFactory;
	private ServiceRegistry serviceRegistry;
	
	private Logger logger;
	
	public DBLoader() {
		// configures settings from hibernate.cfg.xml
		Configuration configuration = new Configuration();
		configuration.configure();


		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();

		// A SessionFactory is set up once for an application
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		logger = Logger.getLogger("EavesdropReader");
	}
	
	public void loadData(Map<String, List<String>> data) {
		logger.info("Inside loadData.");
	}
	
	public int addMeeting(String teamMeetingName, String year, String meetingName, String link) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int assignmentId = 0;
		try {
			tx = session.beginTransaction();
			Meeting newMeeting = new Meeting(teamMeetingName, year, meetingName, link);
			session.saveOrUpdate(newMeeting);
		    assignmentId = newMeeting.getId();
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();
		}
		return assignmentId;
	}
	
	public Meeting getMeeting(String title) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Meeting.class).
        		add(Restrictions.eq("teamMeetingName", title));
		
		List<Meeting> meetings = criteria.list();
		
		return meetings.get(0);
	}
	
	public Meeting getMeeting(int assignmentId) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Meeting.class).
        		add(Restrictions.eq("id", assignmentId));
		
		List<Meeting> meetings = criteria.list();
		
		return meetings.get(0);
	}
}
