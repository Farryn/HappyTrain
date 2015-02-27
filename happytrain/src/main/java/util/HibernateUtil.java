package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	private static Session currentSession;
	private static Transaction currentTransaction;
	
	public static Session getCurrentSession() {
		return currentSession;
	}



	public static void setCurrentSession(Session currentSession) {
		HibernateUtil.currentSession = currentSession;
	}



	public static Transaction getCurrentTransaction() {
		return currentTransaction;
	}



	public static void setCurrentTransaction(Transaction currentTransaction) {
		HibernateUtil.currentTransaction = currentTransaction;
	}

	
	
    static {
      try {
        Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		sessionFactory = configuration.buildSessionFactory(builder.build());
     
      } catch (Throwable ex) {
        System.err.println("Initial SessionFactory creation failed." + ex);
        throw new ExceptionInInitializerError(ex);
      }
    }

    public static SessionFactory getSessionFactory() {
      return sessionFactory;
    }
    
    
	
	public static Session openCurrentSession() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		return currentSession;
	}

	public static Session openCurrentSessionwithTransaction() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public static void closeCurrentSession() {
		currentSession.close();
	}
	
	public static void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
}
