package br.com.persistenciafa7.util;

import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateUtil {

	private static SessionFactory sf = null;
	
	private HibernateUtil() {
	}
	
	/**
	 * Garante a existencia de apenas 1 session factory, utilizando Singleton Designer Pattern
	 * com Double-lock
	 * 
	 * @author erinaldo.souza
	 * @since 2016-03-18
	 * @return Session do hibernate
	 */
	public static Session getHibernateSession() {
		if(sf == null) {
			synchronized (SessionFactory.class) {
				if(sf == null) {
					sf = new Configuration().configure().buildSessionFactory();
				}
			}
		}
		
		return  sf.getCurrentSession();
	}
}
