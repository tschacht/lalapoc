package lalapoc.repository;

import lalapoc.entity.Need;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface NeedRepository extends GraphRepository<Need> {

 @Fetch
 @Query("match (n:Need) where n.name =~ {pattern} return n")
 Iterable<Need> findByPattern(@Param("pattern") String pattern);

}
