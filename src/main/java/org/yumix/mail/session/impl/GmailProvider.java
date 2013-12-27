package org.yumix.mail.session.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.yumix.mail.session.SessionProvider;
import org.yumix.mail.session.UserSetting;
import org.yumix.mail.session.qualifier.Gmail;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
@Gmail
public class GmailProvider implements SessionProvider {
	
	/**
	 * The sender address used in "mail.from" properties of JavaMail.
	 */
	private String address;
	
	/**
	 * The user name used logged in a mail server.
	 */
	private String userName;
	
	/**
	 * The password used logged in a mail server.
	 */
	private String password;
	
	@Inject
	public GmailProvider(UserSetting userSetting) {
		address = userSetting.getFromAddress();
		userName = userSetting.getUserName();
		password = userSetting.getPassword();
	}
	
	/* (non Javadoc)
	 * @see org.yumix.mail.session.SessionProvider#getSession()
	 */
	@Override
	public Session getSession() {
		if (address == null || userName == null || password == null) {
			throw new IllegalStateException("This provider is not initialized yet");
		}
		return getSession(address, userName, password);
	}
	
	/* (non Javadoc)
	 * @see org.yumix.mail.session.SessionProvider#getSession(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Session getSession(final String address, final String userName, final String password) {
		Properties props = new Properties();
		try {
			props.load(new InputStreamReader(getClass().getResourceAsStream("/gmail.properties")));
			props.setProperty("mail.from", address);
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
