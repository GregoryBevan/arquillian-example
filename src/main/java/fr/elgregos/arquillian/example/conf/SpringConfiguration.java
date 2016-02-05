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
@ComponentScan({ "io.github.tomacla.failsafe.sample.domain", "io.github.tomacla.failsafe.sample.service" })
public class SpringConfiguration {

    @Bean
    public BasicDataSource datasource() {
	BasicDataSource connectionPool = new BasicDataSource();
	connectionPool.setDriverClassName("org.postgresql.Driver");
	connectionPool.setUsername("postgres");
	connectionPool.setPassword("postgres");
	connectionPool.setUrl("jdbc:postgresql://localhost:5431/failsafe");
	connectionPool.setInitialSize(1);
	connectionPool.setDefaultAutoCommit(false);
	return connectionPool;
    }

    private Properties hibernateProperties() {
	Properties properties = new Properties();
	properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
	properties.put("hibernate.show_sql", "true");
	properties.put("hibernate.format_sql", "true");
	return properties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
	LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	sessionFactory.setDataSource(datasource());
	sessionFactory.setPackagesToScan(new String[] { "io.github.tomacla.failsafe.sample.domain" });
	sessionFactory.setHibernateProperties(hibernateProperties());
	return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
	HibernateTransactionManager txManager = new HibernateTransactionManager();
	txManager.setSessionFactory(sessionFactory);
	return txManager;
    }

}