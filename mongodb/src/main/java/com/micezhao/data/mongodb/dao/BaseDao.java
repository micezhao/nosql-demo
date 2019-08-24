package com.micezhao.data.mongodb.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.micezhao.data.mongodb.aop.LoggerAspect;

public class BaseDao <T> {
	
	@Autowired
	protected MongoTemplate mongoTemplate;
	
	@SuppressWarnings("unchecked")
	@LoggerAspect
	public  List<T> findAll(T t){
		return (List<T>) mongoTemplate.findAll(t.getClass());
	}
	
	public  Object findById(Object _id,T t) {		
		Query query = new Query(Criteria.where("_id").is(_id));
		return  mongoTemplate.findOne(query, t.getClass());
	}
	
	public T insert(T t) {
		return mongoTemplate.insert(t);
	}
	
	public boolean modify(Query query,Update update,T t) {
		boolean flag = mongoTemplate.updateFirst(query, update, t.getClass()).wasAcknowledged();
		return flag;
	}
	
	public boolean delete(Query query,T t) {
		boolean flag= mongoTemplate.remove(query, t.getClass()).wasAcknowledged();
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> doAggregate(Aggregation aggregation,String collectionName) {
		return (List<String>) mongoTemplate.aggregate(aggregation, collectionName, new String().getClass()).getMappedResults();
	}
	
	public void insertBatch(List<T> list,T t ) {
		mongoTemplate.insert(list, t.getClass());
	}
	
	@SuppressWarnings("unchecked")
	@LoggerAspect
	public List<T> findByPage(T t,Long pageNum,int rowNum){
		Query query = new Query();
		query.skip( (pageNum-1) *rowNum)
        .limit(rowNum);
		return (List<T>) mongoTemplate.find(query, t.getClass());
	}
}

