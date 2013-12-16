package org.yumix.mail.receiver;

import static org.yumix.mail.receiver.MessageProtocol.IMAP;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

public class MailFolder implements AutoCloseable {
	
	private Folder folder;
	
	private final MessageProtocol messageProtocol;
	
	MailFolder(Store store, String folderName, MessageProtocol messageProtocol) {
		if (store == null || folderName == null || messageProtocol == null) {
			throw new NullPointerException();
		}
		
		this.messageProtocol = messageProtocol;
		
 		try {
			folder = store.getFolder(folderName);
			folder.open(Folder.READ_ONLY);
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	MailFolder(Folder folder, MessageProtocol messageProtocol) {
		if (folder == null || messageProtocol == null) {
			throw new NullPointerException();
		}
		
		this.messageProtocol = messageProtocol;
		
		try {
			this.folder = folder;
			folder.open(Folder.READ_ONLY);
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public String getName() {
		return folder.getName();
	}
	
	public String getFullName() {
		return folder.getFullName();
	}
	
	@Override
	public void close() throws Exception {
		folder.close(false);
	}
	
	public List<MailFolder> listFolders() {
		List<MailFolder> mailFolders = new ArrayList<>();
		
		if (messageProtocol == IMAP) {
			try {
				for (Folder subFolder : folder.list()) {
					mailFolders.add(new MailFolder(subFolder, messageProtocol));
				}
			} catch (MessagingException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
		return mailFolders;
	}
	
	public <E> List<E> listMessages(MessageDecoder<E> messageDecoder) {
		try {
			List<E> receivedMessages = new ArrayList<>();
			for (Message message : folder.getMessages()) {
				MimeMessage mimeMessage = (MimeMessage) message;
				receivedMessages.add(messageDecoder.decode(mimeMessage));
			}
			return receivedMessages;
		} catch (MessagingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
