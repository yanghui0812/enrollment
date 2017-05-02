package config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:common.properties", "classpath:hibernate.properties" })
@ComponentScan(value = "com.enroll")
public class ApplicationContext {

	public static final String hibernate_connection_autocommit = "hibernate.connection.autocommit";

	public static final String hibernate_format_sql = "hibernate.format_sql";

	public static final String hibernate_hbm2ddl_auto = "hibernate.hbm2ddl.auto";

	public static final String hibernate_show_sql = "hibernate.show_sql";

	public static final String hibernate_current_session_context_class = "hibernate.current_session_context_class";

	public static final String hibernate_dialect = "hibernate.dialect";

	public static final String package_to_scan = "com.enroll.**.entity";

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	@Autowired
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setMaxTotal(30);
		dataSource.setDefaultAutoCommit(false);
		return dataSource;
	}

	@Bean
	@Autowired
	public LocalSessionFactoryBean sessionFactory(DataSource datasouce) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(datasouce);
		sessionFactory.setPackagesToScan(package_to_scan);
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put(hibernate_dialect, env.getProperty(hibernate_dialect));
		hibernateProperties.put(hibernate_current_session_context_class, env.getProperty(hibernate_current_session_context_class));
		hibernateProperties.put(hibernate_connection_autocommit, env.getProperty(hibernate_connection_autocommit));
		hibernateProperties.put(hibernate_format_sql, env.getProperty(hibernate_format_sql));
		hibernateProperties.put(hibernate_hbm2ddl_auto, env.getProperty(hibernate_hbm2ddl_auto));
		hibernateProperties.put(hibernate_show_sql, env.getProperty(hibernate_show_sql));
		return hibernateProperties;

	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
		return txManager;
	}
}