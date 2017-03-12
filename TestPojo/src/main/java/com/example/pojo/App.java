package com.example.pojo;


public class App 
{
	String appName;
	
    
    public App (String appName) {
    	this.appName = appName;
    }
    
    public App() {
    	
    }

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
    
    public void displayAppName() {
    	System.out.println("App name is : " + appName);
    }
}
