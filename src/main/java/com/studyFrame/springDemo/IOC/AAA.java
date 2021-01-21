package com.studyFrame.springDemo.IOC;

import org.springframework.beans.factory.annotation.Autowired;

public class AAA {
	@Autowired
	private BBB bbb;

	public BBB getBbb() {
		return bbb;
	}

	public void setBbb(BBB bbb) {
		this.bbb = bbb;
	}
}
