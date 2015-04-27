package lalapoc;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.URISyntaxException;

@Configuration
@Import(RepositoryRestMvcConfiguration.class)
@EnableNeo4jRepositories(basePackages = "lalapoc.repository")
@EnableTransactionManagement
public class LalapocNeo4jConfiguration extends Neo4jConfiguration {

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

	public LalapocNeo4jConfiguration() {
		setBasePackage( "lalapoc.entity" );
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

}
