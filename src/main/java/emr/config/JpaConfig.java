package emr.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "aclEntityManagerFactory", 
        transactionManagerRef = "aclTransactionManager",
        basePackages = { "emr.acl" })
public class JpaConfig {
	
    @Bean(name = "aclDataSource")
    public DataSource dataSource() {
    	
    	if(System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME") != null) {
	    	String username = System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME");
	        String password = System.getenv("OPENSHIFT_POSTGRESQL_DB_PASSWORD");
	        String host = System.getenv("OPENSHIFT_POSTGRESQL_DB_HOST");
	        String port = System.getenv("OPENSHIFT_POSTGRESQL_DB_PORT");
	        String databaseName = System.getenv("OPENSHIFT_APP_NAME");
	        String url = "jdbc:postgresql://" + host + ":" + port + "/"+databaseName;
	        
	        DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setUrl(url);
	        dataSource.setDriverClassName("org.postgresql.Driver");
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        return dataSource;
    	} 
    	
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	    return builder.setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean(name = "aclEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "aclEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
    	LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    	entityManagerFactory.setDataSource(dataSource());
    	entityManagerFactory.setPackagesToScan("emr.acl");
    	
    	HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    	hibernateJpaVendorAdapter.setGenerateDdl(true);
    	
    	if(System.getenv("OPENSHIFT_POSTGRESQL_DB_USERNAME") != null) {
	        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
	        hibernateJpaVendorAdapter.setShowSql(true);
	        
	        entityManagerFactory.setPersistenceUnitName("acl-postgresql");
	        entityManagerFactory.setPersistenceXmlLocation("classpath:META-INF/jpa-persistence.xml");
    	}
    	
        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        entityManagerFactory.afterPropertiesSet();

        return entityManagerFactory.getObject();
    }

    @Bean(name = "aclTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}
