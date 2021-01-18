package com.studyFrame.springDemo;

import org.springframework.beans.factory.FactoryBean;

public class FactoryBeanDemo implements FactoryBean<Person> {
	@Override
	public Person getObject() throws Exception {
		return new Person("kobo");
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
