package org.yumix.mail.session.impl;

import javax.mail.Session;

import org.yumix.mail.session.SessionProvider;
import org.yumix.mail.session.qualifier.Docomo;

@Docomo
public class DocomoProvider implements SessionProvider {

	@Override
	public Session getSession() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Session getSession(String address, String userName, String password) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
