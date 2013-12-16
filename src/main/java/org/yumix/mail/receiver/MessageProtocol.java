package org.yumix.mail.receiver;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public enum MessageProtocol {
	/**
	 * 
	 */
	POP3("pop3"), 
	
	/**
	 * 
	 */
	IMAP("imap");
	
	/**
	 * 
	 */
	private final String name;
	
	/**
	 * @param name
	 */
	MessageProtocol(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
}
