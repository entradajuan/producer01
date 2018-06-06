package com.needine.producer01;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import java.io.Serializable;

@Named
@RequestScoped
public class HelloBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}