package org.doudou.doudouflow.dao.repositories;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.doudou.doudouflow.dao.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public String m1() {
		return null;
	}
	
	@Transactional(transactionManager = "transactionManager")
	public String m() {
		//test2();
//		transactionTemplate.execute(new TransactionCallback<Integer>() {
//
//			@Override
//			public Integer doInTransaction(TransactionStatus status) {
//				User user = entityManager.find(User.class, "admin");
//				if (user == null) {
//					user = new User();
//					user.setUsername("admin");
//					entityManager.persist(user);
//				} else {
//					user.setLastLoginTime(new Date());
//					// entityManager.remove(user);
//					entityManager.persist(user);
//				}
//				entityManager.flush();
//				return null;
//			}
//		});
		
		User user = entityManager.find(User.class, "admin");
		if (user == null) {
			user = new User();
			user.setUsername("admin");
			entityManager.persist(user);
		} else {
			user.setLastLoginTime(new Date());
			entityManager.persist(user);
		}
		//System.out.println(entityManager);
		//entityManager.flush();
		
		return null;
	}

}
