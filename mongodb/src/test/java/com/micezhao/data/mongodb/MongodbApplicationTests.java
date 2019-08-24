package com.micezhao.data.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.micezhao.data.mongodb.dao.InventoryDao;
import com.micezhao.data.mongodb.dao.OrdersDao;
import com.micezhao.data.mongodb.pojo.Inventory;
import com.micezhao.data.mongodb.pojo.Orders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {
	
	@Autowired
	private OrdersDao orderDao;
	
	@Autowired
	private InventoryDao invenDao;
	
	private Orders order = new Orders();
	
	@Autowired
	private InventoryService invenService;
		
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void find_1() {
		List<Orders> list= (List<Orders>) orderDao.findAll(order);
		for (Orders item : list) {
			System.out.println(item);
		}
	}
	
	@Test
	public void find_1_1() {
		 Orders item = (Orders) orderDao.findById(89.0, order);
		 System.out.println(item);
	}
	
	@Test
	public void find_condition() {
		order.setItem("MON1003");
		List<Orders> list= orderDao.getOrderByConditions(order);
		for (Orders item : list) {
			System.out.println(">>>>>>"+item.toString());
		}
	}
	
	@Test
	public void find_2() {
		List<Inventory> list= (List<Inventory>)invenDao.findAll(new Inventory());
		for (Inventory inventory : list) {
			System.out.println(inventory);
		}
	}
	
	@Test
	public void insert_0() {
		Inventory t = new Inventory(null, "aa", "test", "dfer", "iock", null, null);
		invenDao.insert(t);
	}
	
	@Test
	public void insert_1() {
		ArrayList<String> specs = new ArrayList<String>();
		specs.add("test1");
		specs.add("2test");
		specs.add("mock");
		Orders order1 = new Orders(null, "mockItem", 23.4, 198.9, specs);
		Orders returnOrder= orderDao.insert(order1);
		System.out.println(returnOrder);
	}
	
	@Test
	public void update_0() {
		Query query = new Query();
		query.addCriteria(new Criteria("id").is("987655"));
		Update update = new Update();
		update.set("item", "insertItemUpdate");
		boolean flag = orderDao.modify(query, update, order);
		System.out.println(flag);
	}
	
	@Test
	public void delete() {
		Query query = new Query();
		query.addCriteria(new Criteria("price").lte(20.0));
		orderDao.delete(query, order);
	}
	
	@Test
	public void delete_inv() {
		invenDao.delete(new Query(), new Inventory());
	}
	
	@Test
	public void doAggregation() {
		Aggregation aggregation = 
				Aggregation.newAggregation( // .newAggregation() 方法相当于是开启了聚合管道 
						Aggregation.match(new Criteria().where("status").is("A")), //{$match:{status:"A"}}
						Aggregation.unwind("tags"),  // {$unwind:"$tags"}
						Aggregation.group("tags").count().as("count"), //{$group:{_id:"$tags",count : {$sum:1 d}}}
						Aggregation.sort(Direction.DESC, "count"),   //  {$sort : { count : -1}}
						Aggregation.project("count").andExclude("_id") // {$project : {count:1,_id:0}}
				);
		List<String> list =invenDao.doAggregate(aggregation, "inventory");
		for (String item : list) {
			System.out.println(item);
		}
	}
	
	@Test
	public void insertBatch() {
		List<Inventory> list = new ArrayList<Inventory>();
		for (int i = 0; i < 10000; i++) {
			if(i % 3 == 1) {
				list.add(new Inventory(String.valueOf(i),"almonds","product 1","123","Monitor",null,null));
			}else if(i % 3 == 2) {
				list.add(new Inventory(String.valueOf(i),"MON1003","product 2","123","iock",null,null));
			}else {
				list.add(new Inventory(String.valueOf(i),"cashews","product 3","123","dfer",null,null));
			}
		}
		invenDao.insertBatch(list, new Inventory());
	}
	
	
	@Test
	public void findByPage() {
		List<Inventory> list= invenDao.findByPage(new Inventory(), 2L, 5);
		for (Inventory item : list) {
			System.out.println(item);
		}
	}
	
	/**
	 * mongodb 4.0 以上版本才支持事务控制
	 * In version 4.0, MongoDB supports multi-document transactions on replica sets. 多副本集支持事务
	 * In version 4.2, MongoDB introduces distributed transactions, which adds support for multi-document 分片支持事务
	 * 当前demo连接的mongo版本为4.0.11
	 */
	@Test
	public void transTest() {
		invenService.transAction();
	}
	
	@Test
	public void cusTest() {
		invenService.custAction();
	}
}
