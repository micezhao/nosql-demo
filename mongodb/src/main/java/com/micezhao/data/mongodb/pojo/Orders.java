package com.micezhao.data.mongodb.pojo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders  {
	
	private String id;
	
	private String item;
	
	private double price;
	
	private double quantity;
	
	private ArrayList<String> specs;
		
	
}
