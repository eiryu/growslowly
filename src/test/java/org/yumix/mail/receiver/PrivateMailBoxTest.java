package org.yumix.mail.receiver;

import static javax.mail.Message.RecipientType.TO;
import static org.yumix.mail.receiver.MessageProtocol.IMAP;
import static org.yumix.mail.receiver.MessageProtocol.POP3;

import java.io.IOException;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.yumix.mail.session.SessionProviderFactory;
import org.yumix.mail.session.impl.OCNProvider;
import org.yumix.mail.session.impl.OutlookProvider;

public class PrivateMailBoxTest {

	private static class TestMessageDecoder implements MessageDecoder<String> {
		@Override
		public String decode(Message message) {
			try {
				MimeMessage mimeMessage = (MimeMessage) message;
				StringBuilder text = new StringBuilder();
				text.append("From: ").append(mimeMessage.getFrom()[0]).append("\n");
				text.append("To: ").append(mimeMessage.getRecipients(TO)[0]).append("\n");
				text.append("Subject: ").append(mimeMessage.getSubject()).append("\n");
				text.append("\n\n");
				return text.toString();
			} catch (MessagingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
	private SessionProviderFactory factory = SessionProviderFactory.getInstance();
	
	@Test
	public void testReceiveMessageFromOutlook() throws IOException {
		Session session = factory.getSession(OutlookProvider.class, "yumix@outlook.com", "yumix@outlook.com", "Dysnomi@");
		session.setDebug(false);
		try (MailBox mailBox = new MailBox(session, IMAP)) {
			List<String> msgs = mailBox.inbox().listMessages(new TestMessageDecoder());
			for (String msg : msgs) {
				System.out.println(msg);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@Test
	public void testReceiveMessageFromOCN() throws IOException {
		Session session = factory.getSession(OCNProvider.class, "yumix_h@dream.ocn.ne.jp", "yumix_h@dream.ocn.ne.jp", "Dysnom!a");
		session.setDebug(false);
		try (MailBox mailBox = new MailBox(session, POP3)) {
			List<String> msgs = mailBox.inbox().listMessages(new TestMessageDecoder());
			for (String msg : msgs) {
				System.out.println(msg);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@Test
	public void testListingMessageFolderFromOutlook() {
		Session session = factory.getSession(OutlookProvider.class, "yumix@outlook.com", "yumix@outlook.com", "Dysnomi@");
		session.setDebug(false);
		try (MailBox mailBox = new MailBox(session)) {
			System.out.printf("%s (%s)\n", mailBox.inbox().getName(), mailBox.inbox().getFullName());
			for (MailFolder mailFolder : mailBox.inbox().listFolders()) {
				System.out.printf("%s (%s)\n", mailFolder.getName(), mailFolder.getFullName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	@Test
	public void testListingMessageFolderFromOCN() {
		Session session = factory.getSession(OCNProvider.class, "yumix_h@dream.ocn.ne.jp", "yumix_h@dream.ocn.ne.jp", "Dysnom!a");
		session.setDebug(false);
		try (MailBox mailBox = new MailBox(session, POP3)) {
			System.out.printf("%s (%s)\n", mailBox.inbox().getName(), mailBox.inbox().getFullName());
			for (MailFolder mailFolder : mailBox.inbox().listFolders()) {
				System.out.printf("%s (%s)\n", mailFolder.getName(), mailFolder.getFullName());
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
