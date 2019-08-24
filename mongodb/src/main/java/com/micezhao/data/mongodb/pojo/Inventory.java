package com.micezhao.data.mongodb.pojo;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class Inventory {

	private String id;

	private String sku;
	
	private String description;
	
	private String instock;
	
	private String type;
	
	private ArrayList<String> size;
	
	private ArrayList<String> tags;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstock() {
		return instock;
	}

	public void setInstock(String instock) {
		this.instock = instock;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<String> getSize() {
		return size;
	}

	public void setSize(ArrayList<String> size) {
		this.size = size;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public Inventory(String id, String sku, String description, String instock, String type, ArrayList<String> size,
			ArrayList<String> tags) {
		super();
		this.id = id;
		this.sku = sku;
		this.description = description;
		this.instock = instock;
		this.type = type;
		this.size = size;
		this.tags = tags;
	}
	
	public Inventory( String sku, String description, String instock, String type, ArrayList<String> size,
			ArrayList<String> tags) {
		super();
		this.sku = sku;
		this.description = description;
		this.instock = instock;
		this.type = type;
		this.size = size;
		this.tags = tags;
	}

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", sku=" + sku + ", description=" + description + ", instock=" + instock
				+ ", type=" + type + ", size=" + size + ", tags=" + tags + "]";
	}
	
}
