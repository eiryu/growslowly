package org.yumix.mail.session;

import javax.mail.Session;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public interface SessionProvider {
	/**
	 * @param address
	 * @param userName
	 * @param password
	 * @return
	 */
	Session getSession(final String address, final String userName, final String password);
}
