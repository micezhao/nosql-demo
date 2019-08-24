package com.micezhao.data.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.micezhao.data.mongodb.pojo.Orders;

@Component
public class OrdersDao extends BaseDao<Orders>{

	public Object getMaxPrice() {
		List<AggregationOperation> operations = new ArrayList<>();
		operations.add(Aggregation.match(new Criteria("item").exists(true)));
		operations.add(Aggregation.group("id").max("price").as("max_price"));
		Aggregation aggregation = Aggregation.newAggregation(operations);
		AggregationResults<? extends String> result =mongoTemplate.aggregate(aggregation, "orders", new String().getClass());
		return result.getMappedResults();
	}
	
	@SuppressWarnings("unchecked")
	public List<Orders> getOrderByConditions(Orders order) {
		Query query = new Query();
		if(!StringUtils.isEmpty(order.getId())) {
			query.addCriteria(Criteria.where("id").is(order.getId()));
		}
		if(!StringUtils.isEmpty(order.getItem())) {
			query.addCriteria(Criteria.where("item").is(order.getItem()));
		}
		if(order.getPrice()!=0 ) {
			query.addCriteria(Criteria.where("price").is(order.getPrice()));
		}
		if(order.getQuantity() != 0) {
			query.addCriteria(Criteria.where("quantity").is(order.getQuantity()));
		}
		List<Orders> list =(List<Orders>)mongoTemplate.find(query, order.getClass());
		return list;
	}
	
	
}
