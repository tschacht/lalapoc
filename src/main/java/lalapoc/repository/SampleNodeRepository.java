package lalapoc.repository;

import lalapoc.entity.SampleNode;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface SampleNodeRepository extends GraphRepository<SampleNode> {

	@Fetch
	@Query("match (n:SampleNode) where n.name =~ 'pipapo_5.*' return n")
	Iterable<SampleNode> findByCustomQuery();

	@Fetch
	@Query("match (n:SampleNode) where n.name =~ {pattern} return n")
	Iterable<SampleNode> findByCustomPatternQuery(@Param("pattern") String pattern);

}
