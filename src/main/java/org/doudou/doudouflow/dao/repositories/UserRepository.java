package org.doudou.doudouflow.dao.repositories;

import java.util.Date;

import javax.persistence.EntityManager;

import org.doudou.doudouflow.ThreadLocalEntityManagerFactory;
import org.doudou.doudouflow.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private DefaultListableBeanFactory beanFactory;

    //@Transactional
	public String test2() {
		synchronized (beanFactory) {
			EntityManager entityManager = ThreadLocalEntityManagerFactory.getInstance(beanFactory).getEntityMananger();
			entityManager.getTransaction().begin();
			User user = entityManager.find(User.class, "admin");
			if (user == null) {
				user = new User();
				user.setUsername("admin");
				entityManager.persist(user);
			}else {
				user.setLastLoginTime(new Date());
				entityManager.persist(user);
			}
			//entityManager.flush();
			entityManager.getTransaction().commit();
		}
		return "test2";
	}
}
