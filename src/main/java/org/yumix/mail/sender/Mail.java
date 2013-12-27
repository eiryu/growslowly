package org.yumix.mail.sender;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * This class performs to create and send a mail.
 * 
 * @author Yumi Hiraoka - yumix at outlook.xom
 */
public class Mail {
	
	public static class Attachment {
		private String type;
		private Object content;
		private String fileName;
		
		public Attachment() {
			this.type = "application/octet-stream";
		}
		
		public Attachment(String type, Object content, String fileName) {
			this.type = type;
			this.content = content;
			this.fileName = fileName;
		}
		
		public Attachment(String type, Object content) {
			this(type, content, null);
		}
		
		public Attachment(Object content, String fileName) {
			this("application/octet-stream", content, fileName);
		}
		
		public Attachment(Object content) {
			this("application/octet-stream", content, null);
		}
		
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Object getContent() {
			return content;
		}
		public void setContent(Object content) {
			this.content = content;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	}
	
	/**
	 * Builder class for create a mail.
	 */
	public class Builder {
		Builder() {
			if (Locale.getDefault().getLanguage().equals(Locale.JAPANESE.getLanguage())) {
				cs = Charset.forName("ISO-2022-JP");
			} else {
				cs = Charset.forName("US-ASCII");
			}
		}
		
		/**
		 * Thar character set of this mail.
		 */
		private Charset cs;
		
		/**
		 * The sender's address.
		 */
		private String sendFrom;
		
		/**
		 * The recipients' addresses set to "To" field.
		 */
		private Set<String> rcptTo = new HashSet<>();
		
		/**
		 * The recipients' addresses set to "Cc" field.
		 */
		private Set<String> rcptCc = new HashSet<>();
		
		/**
		 * The recipients' addresses set to "Bcc" field.
		 */
		private Set<String> rcptBcc = new HashSet<>();
		
		/**
		 * TODO 
		 */
		private String subject;
		
		/**
		 * TODO
		 */
		private String message;
		
		/**
		 * TODO
		 */
		private List<Attachment> attachments = new ArrayList<>();
		
		/**
		 * Set character encoding.
		 * <p>
		 * If it is already set, this overwrites the exist setting.
		 * 
		 * @param cs character set
		 * @return the reference to itself
		 */
		public Builder charset(Charset cs) {
			this.cs = cs;
			return this;
		}
		
		/**
		 * Set senders address as "From" one.
		 * <p>
		 * If it is already set, this overwrites it the new one 
		 * @param address
		 * @return
		 */
		public Builder from(String address) {
			sendFrom = address;
			return this;
		}
		
		/**
		 * Add addresses as "To" recipients.
		 * <p>
		 * If some of them is already added, this overwrites them the new addresses.
		 * {@code addresses} is nothing, this add no recipients.
		 * 
		 * @param addresses recipients' addresses set to "To" field
		 * @return the reference to itself
		 */
		public Builder to(String...addresses) {
			for (String address : addresses) {
				if (!(address == null || address.trim().isEmpty())) {
					rcptTo.addAll(Arrays.asList(addresses));
				}
			}
			return this;
		}
		
		/**
		 * Add addresses as "Cc" recipients.
		 * <p>
		 * If some of them is already added, this overwrites them the new addresses.
		 * {@code addresses} is nothing, this add no recipients.
		 * 
		 * @param addresses recipients' addresses set to "Cc" field
		 * @return the reference to itself
		 */
		public Builder cc(String...addresses) {
			for (String address : addresses) {
				if (!(address == null || address.trim().isEmpty())) {
					rcptCc.addAll(Arrays.asList(addresses));
				}
			}
			return this;
		}
		
		/**
		 * Add addresses as "Bcc" recipients.
		 * <p>
		 * If some of them is already added, this overwrites them the new addresses.
		 * {@code addresses} is nothing, this add no recipients.
		 * 
		 * @param addresses recipients' addresses set to "Bcc" field
		 * @return the reference to itself
		 */
		public Builder bcc(String...addresses) {
			for (String address : addresses) {
				if (!(address == null || address.trim().isEmpty())) {
					rcptBcc.addAll(Arrays.asList(addresses));
				}
			}
			return this;
		}
		
		public Builder subject(String subject) {
			this.subject = subject;
			return this;
		}
		
		public Builder message(String message) {
			this.message = message;
			return this;
		}
		
		public Builder attachemnt(Attachment attachment) {
			attachments.add(attachment);
			return this;
		}
		
		/**
		 * @return
		 */
		public String getFrom() {
			return sendFrom;
		}

		/**
		 * Get recipients' addresses set to "To" field.
		 * <p>
		 * The return value is {@link Set} and unmodifiable.
		 * 
		 * @return recipients' addresses set to "To" field
		 */
		public Set<String> getTo() {
			return Collections.unmodifiableSet(rcptTo);
		}

		/**
		 * Get recipients' addresses set to "Cc" field.
		 * <p>
		 * The return value is {@link Set} and unmodifiable.
		 * 
		 * @return recipients' addresses set to "Cc" field
		 */
		public Set<String> getCc() {
			return Collections.unmodifiableSet(rcptCc);
		}

		/**
		 * Get recipients' addresses set to "Bcc" field.
		 * <p>
		 * The return value is {@link Set} and unmodifiable.
		 * 
		 * @return recipients' addresses set to "Bcc" field
		 */
		public Set<String> getBcc() {
			return Collections.unmodifiableSet(rcptBcc);
		}

		/**
		 * Get the subject.
		 * 
		 * @return subject
		 */
		public String getSubject() {
			return subject;
		}

		/**
		 * Get the body message of the mail.
		 * 
		 * @return the body message
		 */
		public String getMessage() {
			return message;
		}
		
		/**
		 * TODO
		 * 
		 * @return
		 */
		public List<Attachment> getAttachments() {
			return Collections.unmodifiableList(attachments);
		}
		
		/**
		 * Send a mail by the given JavaMail session.
		 * 
		 * @param session JavaMail session
		 */
		public void send(Session session) {
			if (sendFrom == null || sendFrom.isEmpty()) {
				throw new IllegalArgumentException("Sender address is required");
			}
			if (rcptTo.isEmpty() && rcptCc.isEmpty() && rcptBcc.isEmpty()) {
				throw new IllegalArgumentException("Recipient address(es) is required");
			}
			
			MimeMessage msg = new MimeMessage(session);
			try {
				msg.setFrom(toInternetAddress(sendFrom));
				if (!rcptTo.isEmpty()) {
					msg.addRecipients(TO, toInternetAddress(rcptTo));
				}
				if (!rcptCc.isEmpty()) {
					msg.addRecipients(CC, toInternetAddress(rcptCc));
				}
				if (!rcptBcc.isEmpty()) {
					msg.addRecipients(BCC, toInternetAddress(rcptBcc));
				}
				if (!(subject == null || subject.isEmpty())) {
					msg.setSubject(subject, cs.name());
				}
				
				if (attachments.isEmpty()) {
					if (!(message == null || message.isEmpty())) {
						msg.setText(message, cs.name());
					}
				} else {
					MimeMultipart mp = new MimeMultipart();
					if (!(message == null || message.isEmpty())) {
						MimeBodyPart part = new MimeBodyPart();
						part.setText(message, cs.name());
						mp.addBodyPart(part);
					}
					for (Attachment attachment : attachments) {
						MimeBodyPart part = new MimeBodyPart();
						part.setContent(attachment.getContent(), attachment.getType());
						part.setFileName(attachment.getFileName());
						mp.addBodyPart(part);
					}
					msg.setContent(mp);
				}
				
				Transport.send(msg);
			} catch (MessagingException e) {
				throw new MailSenderException(e);
			}
		}
		
		/**
		 * Send a mail by the default session.
		 */
		public void send() {
			send(Session.getDefaultInstance(null, null));
		}
		
		private final Pattern pattern = Pattern.compile("(?:(.*)\\s<)([a-zA-Z_0-9-.]+@[a-zA-Z_0-9-.]+)(?:>)");
		
		private InternetAddress toInternetAddress(String address) {
			Matcher matcher = pattern.matcher(address);
			try {
				return new InternetAddress(matcher.replaceFirst("$2"), matcher.replaceFirst("$1"), cs.name());
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgumentException(e.getMessage(), e);
			}
		}
		
		private InternetAddress[] toInternetAddress(Collection<String> addresses) {
			Matcher matcher = pattern.matcher("");
			List<InternetAddress> internetAddresses = new ArrayList<>();
			for (String address : addresses) {
				matcher = matcher.reset(address);
				try {
					internetAddresses.add(new InternetAddress(matcher.replaceFirst("$2"), matcher.replaceFirst("$1"), cs.name()));
				} catch (UnsupportedEncodingException e) {
					throw new IllegalArgumentException(e.getMessage(), e);
				}
			}
			return internetAddresses.toArray(new InternetAddress[addresses.size()]);
		}
	}
	
	public static Builder create() {
		return new Mail().new Builder();
	}
	
	public static Builder from(String address) {
		return create().from(address);
	}
}
