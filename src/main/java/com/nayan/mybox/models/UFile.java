package com.nayan.mybox.models;

import java.io.InputStream;

public class UFile {

	private String id;
	private String name;
	private Long size;
	private String contenType;
	private InputStream data;
	
	
	
	public UFile() {}
	public UFile(String id, String name, Long size) {
		this.id = id;
		this.name = name;
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContenType() {
		return contenType;
	}
	public void setContenType(String contenType) {
		this.contenType = contenType;
	}
	public InputStream getData() {
		return data;
	}
	public void setData(InputStream data) {
		this.data = data;
	}
	
	
}
