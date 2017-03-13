package com.example.ejb.jpa;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.example.pojo.App;

@Stateless
public class TestBeanClass implements TestBean {

	private static final Logger LOGGER;
	
	static {
		LOGGER = Logger.getLogger(TestBeanClass.class);
	}
	
	@Override
	public void foo() {
	
		App app = new App("jpa maven test app");
		app.displayAppName();
		
	}

	@Override
	public void foo2(App app) {
		LOGGER.info("inside foo2");
		
		if(null != app)
			app.displayAppName();
		
	}
}
