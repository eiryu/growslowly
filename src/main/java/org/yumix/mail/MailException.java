package org.yumix.mail;

import javax.mail.MessagingException;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public class MailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -699568464431394535L;

	/**
	 * 
	 */
	public MailException() {
		super();
	}

	/**
	 * @param cause
	 */
	public MailException(MessagingException cause) {
		super(cause.getMessage(), cause);
	}
}
