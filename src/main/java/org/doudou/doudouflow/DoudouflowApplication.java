package org.doudou.doudouflow;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootApplication
public class DoudouflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoudouflowApplication.class, args);
	}
	
//	@Bean
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		Map<String,Object> properties = new HashMap<String,Object>();
//		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		properties.put("hibernate.connection.datasource", dataSource);
//		properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		properties.put("hibernate.connection.provider_class", "org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl");
//		properties.put("hibernate.hikari.username", "root");
//		properties.put("hibernate.hikari.password", "honesty");
//		properties.put("hibernate.hikari.minimumIdle", "5");
//		properties.put("hibernate.hikari.maximumPoolSize", "15");
//		properties.put("hibernate.connection.url", "jdbc:mysql://192.168.0.199:3307/eocentre_dev?useUnicode=true&autoReconnect=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=exception&serverTimezone=GMT%2B8");
		properties.put("hibernate.max_fetch_depth", "3");
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.show_sql", "true");
		//EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("jpa");
		EntityManagerFactory entityManagerFactory =  Persistence.createEntityManagerFactory("jpa", properties);
		return entityManagerFactory;
	}
	
	//@Bean
	public DataSource dataSource() {
		HikariConfig configuration = new HikariConfig();
		configuration.setDriverClassName("com.mysql.cj.jdbc.Driver");
		configuration.setUsername("root");
		configuration.setPassword("honesty");
		configuration.setMinimumIdle(5);
		configuration.setMaximumPoolSize(15);
		configuration.setJdbcUrl("jdbc:mysql://192.168.0.199:3307/eocentre_dev?useUnicode=true&autoReconnect=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=exception&serverTimezone=GMT%2B8");
		DataSource dataSource = new HikariDataSource(configuration);
		return dataSource;
	}
	
	@Autowired
	private DataSource dataSource;
	
	
	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder){
        LocalContainerEntityManagerFactoryBean entityManagerFactory =  builder.dataSource(dataSource)
                .packages("org.doudou.doudouflow.dao.entities").build();
        Properties jpaProperties = new Properties();
        //jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        jpaProperties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        jpaProperties.put("hibernate.connection.charSet", "utf-8");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.show_sql", "true");
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
    	EntityManagerFactory  entityManagerFactory = entityManagerFactoryBean(builder).getObject();
        return new JpaTransactionManager(entityManagerFactory);
    }
    
	

}
