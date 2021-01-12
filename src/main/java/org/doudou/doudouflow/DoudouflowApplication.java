package org.doudou.doudouflow;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DoudouflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoudouflowApplication.class, args);
	}
	
	//@Bean
	public EntityManagerFactory entityManagerFactory() {
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		properties.put("hibernate.connection.provider_class", "org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
		properties.put("hibernate.hikari.username", "root");
		properties.put("hibernate.hikari.password", "honesty");
		properties.put("hibernate.hikari.minimumIdle", "5");
		properties.put("hibernate.hikari.maximumPoolSize", "15");
		properties.put("hibernate.connection.url", "jdbc:mysql://192.168.0.199:3307/eocentre_dev?useUnicode=true&autoReconnect=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=exception&serverTimezone=GMT%2B8");
		properties.put("hibernate.max_fetch_depth", "3");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "true");
		//EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("jpa");
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("jpa", properties);
		return entityManagerFactory;
	}
	
	//@Bean
	public EntityManager entityManager() {
		return entityManagerFactory().createEntityManager();
	}

}
