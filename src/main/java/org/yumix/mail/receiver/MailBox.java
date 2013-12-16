package org.yumix.mail.receiver;

import static org.yumix.mail.receiver.MessageProtocol.IMAP;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public class MailBox implements AutoCloseable {
	
	private final Store store;
	
	private final MessageProtocol messageProtocol;
	
	public MailBox(Session session) {
		this(session, IMAP);
	}
	
	public MailBox(Session session, MessageProtocol messageProtocol) {
		if (session == null || messageProtocol == null) {
			throw new NullPointerException("all the argument is not accept null");
		}
		
		this.messageProtocol = messageProtocol;
		
		try {
			store = session.getStore(messageProtocol.getName());
			store.connect();
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void close() throws Exception {
		store.close();
	}
	
	public MailFolder folder(String folderName) {
		return new MailFolder(store, folderName, messageProtocol);
	}
	
	public MailFolder inbox() {
		return folder("INBOX");
	}
}
