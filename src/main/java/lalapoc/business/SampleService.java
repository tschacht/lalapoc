package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.Sample;
import lalapoc.entity.factory.SampleFactory;
import lalapoc.repository.SampleRepository;
import org.neo4j.cypherdsl.grammar.Execute;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.Instant;
import java.util.Collection;
import java.util.Random;

import static org.neo4j.cypherdsl.CypherQuery.identifier;
import static org.neo4j.cypherdsl.CypherQuery.start;

@Named
public class SampleService implements SampleServiceMethods {

	@Inject
	private SampleRepository sampleRepository;

	@Override
	@Fetch
	public Collection<Sample> readSamples() {
		return Lists.newArrayList( sampleRepository.findAll() );
	}

	@Override
	@Fetch
	public Collection<Sample> readSamplesByCustomQuery() {
		return sampleRepository.findByCustomQuery();
	}

	@Override
	@Fetch
	public Collection<Sample> readSamplesByNumber( Long number ) {
		return sampleRepository.findByCustomPatternQuery( "pipapo_" + number + ".*" );
	}

	@Override
	@Transactional
	@Fetch
	public Sample createSample() {
		Random r = new Random();
		Sample 	s = SampleFactory.newSample( "pipapo_" + r.nextInt( 10 ) + " - " + Instant.now() );
		return sampleRepository.save( s );
	}

	@Override
	@Fetch
	public Collection<Sample> readTyped( Long number ) {
		/*
		examples:

		https://github.com/spring-projects/spring-data-neo4j/blob/3.3.x/spring-data-neo4j/src/test/java/org/springframework/data/neo4j/repository/CypherDslRepositoryTests.java

		private Map<String,Object> peopleParams;
		private Execute query = start(nodeByParameter("n", "people")).returns(identifier("n"));
		private Execute countQuery = start(nodeByParameter("n", "people")).returns(count());
		private Execute query2 = CypherQueryDSL.start(CypherQueryDSL.nodeByparameter(identifier(QPerson.person), "people")).
																						where(toBooleanExpression(QPerson.person.name.eq("Michael"))).
																						returns(identifier(QPerson.person));

		peopleParams = map("people", asList(team.michael.getId(), team.david.getId(), team.emil.getId()));

		Page<Person> result = personRepository.query(query, countQuery, peopleParams, new PageRequest(0, 1));
		List<Person> result = personRepository.query(query, peopleParams).as(List.class);
		List<Person> michaelOnly = personRepository.query(query2, peopleParams).as(List.class);

		https://github.com/spring-projects/spring-data-book/blob/master/neo4j/src/test/java/com/oreilly/springdata/neo4j/core/ProductRepositoryIntegrationTest.java

		Execute query = start(lookup(c, index, identifier(customer.emailAddress), param("email"))).
										match(node(c).in("customer").node().out("ITEMS").as(item).node(p)).
										where(toBooleanExpression(product.price.gt(400))).
										returns(p).
										orderBy(order(property(product.name), Order.ASCENDING));

		List<Product> result = repository.query(query, map("email", dave.getEmailAddress())).as(List.class);
		 */

		// match (s:Sample) where s.name =~ 'pipapo_5.*' return s

		Execute query = start()
				.match( identifier( "s" ) )
				.where( identifier( "s" ).property( "name" )
						.regexp( "pipapo_" + number + ".*" ) )
				.returns( identifier( "s" ) );

		return Lists.newArrayList( sampleRepository.query( query, null ) );
	}

}
