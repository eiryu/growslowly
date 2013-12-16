package org.yumix.mail.receiver;

public enum MessageProtocol {
	POP3("pop3"), IMAP("imap");
	
	private final String name;
	
	MessageProtocol(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
