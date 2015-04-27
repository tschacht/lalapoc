package lalapoc.repository;

import lalapoc.entity.Name;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource(collectionResourceRel = "rest-names", path = "rest-names")
public interface NameRepository extends GraphRepository<Name>, RelationshipOperationsRepository<Name> {

	@Fetch
	@Query("match (n:Name) where n.name =~ {0} return n")
	Collection<Name> findByName( String pattern );

}
