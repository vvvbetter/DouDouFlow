package org.doudou.doudouflow;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.BeanFactory;

public class ThreadLocalEntityManagerFactory {

	private static ThreadLocal<EntityManager> local = new ThreadLocal<>();

	private static ThreadLocalEntityManagerFactory factory;

	private BeanFactory beanFactory;

	private ThreadLocalEntityManagerFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public static synchronized ThreadLocalEntityManagerFactory getInstance(BeanFactory beanFactory) {
		if (factory == null) {
			factory = new ThreadLocalEntityManagerFactory(beanFactory);
		}
		return factory;

	}

	public EntityManager getEntityMananger() {
		EntityManager em = local.get();
		if (em != null)
			return em;
		if (beanFactory.containsBean("entityManagerFactory")) {
			EntityManagerFactory entityManagerFactory = beanFactory.getBean(EntityManagerFactory.class);
			em = entityManagerFactory.createEntityManager();
			local.set(em);
		}
		return em;

	}

}
