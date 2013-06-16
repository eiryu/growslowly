package org.yumix.mail.session;

import javax.mail.Session;

public class SessionProviderFactory {
	private static final SessionProviderFactory INSTANCE = new SessionProviderFactory();
	
	public static SessionProviderFactory getInstance() {
		return INSTANCE;
	}
	
	public Session getSession(Class<?> clazz, String address, String userName, String password) {
		try {
			SessionProvider sessionProvider = SessionProvider.class.cast(clazz.newInstance());
			return sessionProvider.getSession(address, userName, password);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
