package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory SESSIONFACTORY;
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
		SESSIONFACTORY = configuration.buildSessionFactory(builder.build());
     
      } catch (Exception e) {
        throw new ExceptionInInitializerError(e);
      }
    }

    private HibernateUtil(){
    	
    }
    public static SessionFactory getSessionFactory() {
      return SESSIONFACTORY;
    }
    
    
	
	public static Session openCurrentSession() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		return currentSession;
	}
	
	public static void closeCurrentSession() {
			currentSession.close();
	}
	
	public static void beginTransaction() {
		currentTransaction = currentSession.beginTransaction();
	}
	
	public static void commitTransaction() {
		currentTransaction.commit();
	}
	
	public static void rollbackTransaction() {
		currentTransaction.rollback();
	}
	
	
}
