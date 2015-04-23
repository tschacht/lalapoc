package lalapoc.repository;

import lalapoc.entity.SampleNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface SampleNodeRepository extends GraphRepository<SampleNode> {

	@Query("match (n:SampleNode) where n.name =~ 'pipapo_5.*' return n")
	Iterable<SampleNode> findCustomQuery();

}
