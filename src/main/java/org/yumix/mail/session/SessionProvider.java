package org.yumix.mail.session;

import javax.mail.Session;

public interface SessionProvider {
	Session getSession(final String address, final String userName, final String password);
}
