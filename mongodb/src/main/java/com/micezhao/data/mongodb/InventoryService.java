package com.micezhao.data.mongodb;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.micezhao.data.mongodb.dao.InventoryDao;
import com.micezhao.data.mongodb.pojo.Inventory;
import com.mongodb.MongoClient;
import com.mongodb.client.ClientSession;

@Service
public class InventoryService {
	
	private final static Logger logger = LoggerFactory.getLogger(InventoryService.class);
	
	@Autowired
	private MongoClient client;
	
	@Autowired
	private InventoryDao invenDao;
	
	/**
	 * 使用默认注解实现mongodb的控制
	 */
	@Transactional
	public void transAction() {
		invenDao.insert(new Inventory("abcd", "test", "transTest", "test", "test", null, null));
		List list = null;
		Query query = new Query(Criteria.where("id").is("abcd"));
		list.get(10);
		invenDao.delete(query, new Inventory());
	}
	
	public void custAction() {
		ClientSession clientSession = client.startSession();
		clientSession.startTransaction();
		try {
			Inventory  item= invenDao.insert(new Inventory("abcd", "test", "transTest", "test", "test", null, null));
			logger.info(">>>>>>插入的item"+item.toString());
			List list = null;
			Query query = new Query(Criteria.where("id").is("abcd"));
			list.get(10);
			invenDao.delete(query, new Inventory());
			clientSession.commitTransaction();
		}catch (Exception e) {
			clientSession.abortTransaction();
			logger.error(">>>>>执行失败，事务回滚");
		}
	}
	
}
