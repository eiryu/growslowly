package org.yumix.mail.receiver;

import javax.mail.Message;

/**
 * @author Yumi Hiraoka - yumix at outlook.com
 *
 */
public interface MessageDecoder<E> {
	/**
	 * @param message
	 * @return
	 */
	E decode(Message message);
}
