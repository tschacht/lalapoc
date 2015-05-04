package lalapoc.repository;

import lalapoc.entity.Sample;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.CypherDslRepository;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "rest-samples", path = "rest-samples")
public interface SampleRepository extends GraphRepository<Sample>, CypherDslRepository<Sample> {

	@Fetch
	@Query("match (s:Sample) where s.name =~ 'pipapo_5.*' return s")
	Collection<Sample> findByCustomQuery();

	@Fetch
	@Query("match (s:Sample) where s.name =~ {pattern} return s")
	Collection<Sample> findByCustomPatternQuery( @Param("pattern") String pattern );

}
