package org.doudou.doudouflow.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.doudou.doudouflow.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

//	@Autowired
//	private ConfigurableApplicationContext applicationContext;

	@Autowired
	private DefaultListableBeanFactory beanFactory;
	@Autowired
	private UserRepository userRepository;

	// @Autowired
	private DataSource dataSource;

	@Autowired
	DataSourceProperties properties;

//	@Autowired
//    private UserRepository userRepository;

//	@Autowired
//	@Qualifier("entityManager")
//	EntityManager entityManager;
//	
//	@Autowired
//	@Qualifier("entityManagerFactory")
//	EntityManagerFactory entityManagerFactory;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public Callable<String> loginPage(ModelMap map) throws IOException {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "login";
			}
		};
	}

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> indexPage(ModelMap map) throws IOException {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "index";
			}
		};
	}

	@GetMapping("/test")
	@ResponseBody
	public Callable<String> test() {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				synchronized (beanFactory) {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
					properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
					properties.put("hibernate.connection.provider_class",
							"org.hibernate.hikaricp.internal.HikariCPConnectionProvider");
					properties.put("hibernate.hikari.username", "root");
					properties.put("hibernate.hikari.password", "honesty");
					properties.put("hibernate.hikari.minimumIdle", "5");
					properties.put("hibernate.hikari.maximumPoolSize", "15");
					properties.put("hibernate.connection.url",
							"jdbc:mysql://192.168.0.199:3307/eocentre_test?useUnicode=true&autoReconnect=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=exception&serverTimezone=GMT%2B8");
					properties.put("hibernate.max_fetch_depth", "3");
					properties.put("hibernate.hbm2ddl.auto", "update");
					properties.put("hibernate.show_sql", "true");
					properties.put("hibernate.transaction.jta.platform", "org.hibernate.service.jta.platform.internal.WeblogicJtaPlatform");
					if (beanFactory.containsSingleton("entityManagerFactory")) {
						EntityManagerFactory entityManagerFactory = beanFactory.getBean(EntityManagerFactory.class);
						entityManagerFactory.close();
						beanFactory.destroySingleton("entityManagerFactory");
						//beanFactory.destroySingleton("entityManager");
						//beanFactory.destroySingleton("transactionManager");
					}
					EntityManagerFactory entityManagerFactory2 = Persistence.createEntityManagerFactory("jpa",
							properties);
//					EntityManager entityManager = entityManagerFactory2.createEntityManager();
					beanFactory.registerSingleton("entityManagerFactory", entityManagerFactory2);
//					beanFactory.registerSingleton("entityManager", entityManager);
//					JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory2);
//					transactionManager.setEntityManagerFactory(entityManagerFactory2);
//					beanFactory.registerSingleton("transactionManager", transactionManager);
				}
				return "test";
			}
		};
	}

	@GetMapping("/test2")
	@ResponseBody
	public String test2() {
		userRepository.test2();
		return "test2";
	}

	private void runScripts(List<Resource> resources, String username, String password) {
		if (resources.isEmpty()) {
			return;
		}
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.setContinueOnError(this.properties.isContinueOnError());
		populator.setSeparator(this.properties.getSeparator());
		if (this.properties.getSqlScriptEncoding() != null) {
			populator.setSqlScriptEncoding(this.properties.getSqlScriptEncoding().name());
		}
		for (Resource resource : resources) {
			populator.addScript(resource);
		}
		DataSource dataSource = this.dataSource;
		if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
			dataSource = DataSourceBuilder.create(this.properties.getClassLoader())
					.driverClassName(this.properties.determineDriverClassName()).url(this.properties.determineUrl())
					.username(username).password(password).build();
		}
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	/**
	 * Create the schema if necessary.
	 * 
	 * @return {@code true} if the schema was created
	 * @see DataSourceProperties#getSchema()
	 */
	boolean createSchema() {
		List<Resource> scripts = getScripts(this.properties.getSchema(), "schema");
		if (!scripts.isEmpty()) {
			String username = this.properties.getSchemaUsername();
			String password = this.properties.getSchemaPassword();
			runScripts(scripts, username, password);
		}
		return !scripts.isEmpty();
	}

	/**
	 * Initialize the schema if necessary.
	 * 
	 * @see DataSourceProperties#getData()
	 */
	void initSchema() {
		List<Resource> scripts = getScripts(this.properties.getData(), "data");
		if (!scripts.isEmpty()) {
			String username = this.properties.getDataUsername();
			String password = this.properties.getDataPassword();
			runScripts(scripts, username, password);
		}
	}

	private List<Resource> getScripts(List<String> resources, String fallback) {
		String platform = this.properties.getPlatform();
		List<String> fallbackResources = new ArrayList<>();
		fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
		fallbackResources.add("classpath*:" + fallback + ".sql");
		return getResources(fallbackResources);
	}

	private List<Resource> getResources(List<String> locations) {
		List<Resource> resources = new ArrayList<>();
		for (String location : locations) {
			for (Resource resource : doGetResources(location)) {
				if (resource.exists()) {
					resources.add(resource);
				}
			}
		}
		return resources;
	}

	private Resource[] doGetResources(String location) {
		try {
			SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(new DefaultResourceLoader(null),
					Collections.singletonList(location));
			factory.afterPropertiesSet();
			return factory.getObject();
		} catch (Exception ex) {
			throw new IllegalStateException("Unable to load resources from " + location, ex);
		}
	}
}
