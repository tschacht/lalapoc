package lalapoc.repository;

import lalapoc.entity.Need;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "rest-needs", path = "rest-needs")
public interface NeedRepository extends GraphRepository<Need>, RelationshipOperationsRepository<Need> {

	@Fetch
	@Query("match (n:Need) where n.description =~ {0} return n")
	Collection<Need> findByDescr( String pattern );

}
