package fr.elgregos.arquillian.example.conf;

import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "fr.elgregos.arquillian.example.domain", "fr.elgregos.arquillian.example.service" })
public class SpringConfiguration {

	@Bean
	public BasicDataSource datasource() {
		final BasicDataSource connectionPool = new BasicDataSource();
		connectionPool.setDriverClassName("org.postgresql.Driver");
		connectionPool.setUsername("it");
		connectionPool.setPassword("it");
		connectionPool.setUrl("jdbc:postgresql://localhost:5432/it");
		connectionPool.setInitialSize(1);
		connectionPool.setDefaultAutoCommit(false);
		return connectionPool;
	}

	private Properties hibernateProperties() {
		final Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(this.datasource());
		sessionFactory.setPackagesToScan(new String[] { "fr.elgregos.arquillian.example.domain" });
		sessionFactory.setHibernateProperties(this.hibernateProperties());
		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
		final HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

}