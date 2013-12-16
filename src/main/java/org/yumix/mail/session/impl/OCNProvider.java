package org.yumix.mail.session.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.yumix.mail.session.SessionProvider;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public class OCNProvider implements SessionProvider {
	/* (非 Javadoc)
	 * @see org.yumix.mail.session.SessionProvider#getSession(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Session getSession(final String address, final String userName, final String password) {
//		String domain = address.substring(address.indexOf("@") + 1);
		Properties props = new Properties();
		try {
			props.load(new InputStreamReader(getClass().getResourceAsStream("/ocn.properties")));
			props.setProperty("mail.from", address);
//			props.setProperty("mail.smtp.host", "smtp.vc" + domain);
//			props.setProperty("mail.pop3.host", domain);
		} catch (IOException e) {
			return null;
		}
		return Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
	}

}
