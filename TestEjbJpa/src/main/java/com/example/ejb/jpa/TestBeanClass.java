package com.example.ejb.jpa;

import javax.ejb.Stateless;
import com.example.pojo.App;

@Stateless
public class TestBeanClass implements TestBean {

	@Override
	public void foo() {
	
		App app = new App("jpa maven test app");
		app.displayAppName();
		
	}

	@Override
	public void foo2(App app) {
		System.out.println("inside foo2");
		
		if(null != app)
			app.displayAppName();
		
	}
}
