package org.yumix.mail.receiver;

import javax.mail.Message;

public interface MessageDecoder<E> {
	E decode(Message message);
}
