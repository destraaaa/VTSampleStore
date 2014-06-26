package com.destra.vtdummy;

public class DatabaseFieldValue {
	private String field;
	private String value;
	
	DatabaseFieldValue(String f, String v){
		field = f;
		value = v;
	}
	
	String getField(){
		return field;
	}
	
	String getValue(){
		return value;
	}

}
