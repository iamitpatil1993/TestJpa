package com.example.ejb.jpa.localinterfaces.accesstypes;

import javax.ejb.Local;

@Local
public interface AccessTypeBeanLocal {

	void create();
	void read(Integer id);
	
}
