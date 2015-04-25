package lalapoc.repository;

import lalapoc.entity.Name;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface NameRepository extends GraphRepository<Name> {

	@Fetch
	@Query( "match (n:Name) where n.name =~ {0} return n" )
	Iterable<Name> findByName( String pattern );

}
