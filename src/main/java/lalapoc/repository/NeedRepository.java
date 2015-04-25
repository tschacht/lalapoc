package lalapoc.repository;

import lalapoc.entity.Need;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface NeedRepository extends GraphRepository<Need> {

	@Fetch
	@Query( "match (n:Need) where n.description =~ {0} return n" )
	Iterable<Need> findByDescr( String pattern );

}
