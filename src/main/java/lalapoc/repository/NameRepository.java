package lalapoc.repository;

import lalapoc.entity.Name;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface NameRepository extends GraphRepository<Name> {

 @Fetch
 @Query( "match (n:Name) where n.name =~ {pattern} return n" )
 Iterable<Name> findByPattern( @Param( "pattern" ) String pattern );

}
