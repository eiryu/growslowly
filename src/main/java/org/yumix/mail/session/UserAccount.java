package org.yumix.mail.session;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public interface UserAccount {
	/**
	 * Get the sender address used in "mail.from" properties of JavaMail.
	 * 
	 * @return the sender address
	 */
	String getFromAddress();
	
	/**
	 * Get the user name used logged in a mail server.
	 * 
	 * @return the user name
	 */
	String getUserName();
	
	/**
	 * Ghe password used logged in a mail server.
	 * 
	 * @return the login password
	 */
	String getPassword();
}
