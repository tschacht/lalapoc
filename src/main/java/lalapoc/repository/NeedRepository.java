package lalapoc.repository;

import lalapoc.entity.Need;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.Collection;

public interface NeedRepository extends GraphRepository<Need> {

	@Fetch
	@Query("match (n:Need) where n.description =~ {0} return n")
	Collection<Need> findByDescr( String pattern );

}
