package lalapoc;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URISyntaxException;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = "lalapoc.repository")
@EnableTransactionManagement
public class LalapocApplication extends Neo4jConfiguration {

	//@Value("${neo4j.rest.url}")
	//private String NEO4J_REST_URL;
	private static final String NEO4J_REST_URL = "http://localhost:7474/db/data";

	//@Value("${neo4j.embedded.path}")
	//private String NEO4J_EMBEDDED_PATH;
	private static final String NEO4J_EMBEDDED_PATH = "neo4j";

	//@Value("${neo4j.user}")
	//private String NEO4J_USER;
	private static final String NEO4J_USER = "neo4j";

	//@Value("${neo4j.password}")
	//private String NEO4J_PASSWORD;
	private static final String NEO4J_PASSWORD = "neo4j";

	public LalapocApplication() {
		setBasePackage( "lalapoc.entity" );
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public static GraphDatabaseService graphDatabaseService() throws URISyntaxException {
		//System.out.println("###");
		//System.out.println(NEO4J_REST_URL + NEO4J_USER + NEO4J_PASSWORD + NEO4J_EMBEDDED_PATH);
		//System.out.println("###");
		//return new RestGraphDatabase(NEO4J_REST_URL, NEO4J_USER, NEO4J_PASSWORD);
		//return new GraphDatabaseFactory().newEmbeddedDatabase(NEO4J_EMBEDDED_PATH);
		return new SpringCypherRestGraphDatabase( NEO4J_REST_URL, NEO4J_USER, NEO4J_PASSWORD );
	}

	public static void main( String[] args ) {
		SpringApplication.run( LalapocApplication.class, args );
	}

}
