package org.yumix.mail.receiver;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import org.yumix.mail.session.SessionProvider;
import org.yumix.mail.session.SessionProviderFactory;

public class Popper<E> {
	
	private List<E> decodedMessages = new ArrayList<>();
	
	private MessageDecoder<E> decoder;
	
	public void receive(Class<SessionProvider> provider) {
		SessionProviderFactory factory = SessionProviderFactory.getInstance();
		Session session = factory.getSession(provider, "", "", "");
		
		try {
			Store store = session.getStore("pop3");
			store.connect();
			
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			
			for (Message message : folder.getMessages()) {
				MimeMessage mimeMessages = (MimeMessage) message;
				decodedMessages.add(decoder.decode(mimeMessages));
			}
			
			folder.close(false);
			store.close();
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public List<E> getReceivedMail() {
		return decodedMessages;
	}
}
