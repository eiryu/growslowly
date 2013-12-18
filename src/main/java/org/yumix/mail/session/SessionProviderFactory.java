package org.yumix.mail.session;

import javax.mail.Session;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public class SessionProviderFactory {
	/**
	 * 
	 */
	private static final SessionProviderFactory INSTANCE = new SessionProviderFactory();
	
	/**
	 * @return
	 */
	public static SessionProviderFactory getInstance() {
		return INSTANCE;
	}
	
	/**
	 * @param clazz
	 * @param address
	 * @param userName
	 * @param password
	 * @return
	 */
	public Session getSession(Class<?> clazz, String address, String userName, String password) {
		try {
			SessionProvider sessionProvider = SessionProvider.class.cast(clazz.newInstance());
			return sessionProvider.getSession(address, userName, password);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
}
