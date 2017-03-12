package com.example.ejb.jpa;

import javax.ejb.Local;

import com.example.pojo.App;

@Local
public interface TestBean {
	
	public void foo();
	public void foo2(App app);

}
