package emr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(
		basePackages = { "emr.patient", "emr.assessment" })
public class MongoConfig {
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
	    if (System.getenv("OPENSHIFT_MONGODB_DB_HOST") != null) {
	        String openshiftMongoDbHost = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
	        int openshiftMongoDbPort = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
	        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
	        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
	        Mongo mongo = new MongoClient(openshiftMongoDbHost, openshiftMongoDbPort);
	        UserCredentials userCredentials = new UserCredentials(username, password);
	        String databaseName = System.getenv("OPENSHIFT_APP_NAME");
	        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, databaseName, userCredentials);
	        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
	        return mongoTemplate;
	    } else {
	        return new MongoTemplate(new SimpleMongoDbFactory(new MongoClient(), "test"));
	    }
	}
}
