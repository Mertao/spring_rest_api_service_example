package com.artemoleshev.spring.rest.configuration;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan(basePackages = "com.artemoleshev.spring.rest")
@EnableWebMvc
@EnableTransactionManagement
public class MyConfig {
	
	@Bean
	public ComboPooledDataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("org.postgresql.Driver");
			dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/my_db?useSSL=false");
			dataSource.setUser("user");
			dataSource.setPassword("password");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.artemoleshev.spring.rest.entity");
		
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		
		sessionFactory.setHibernateProperties(hibernateProperties);
		
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		
		transactionManager.setSessionFactory(sessionFactory().getObject());
		
		return transactionManager;
	}
}
















