package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.Asking;
import lalapoc.repository.NameRepository;
import lalapoc.repository.NeedRepository;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named
public class RescueService implements RescueServiceMethods {

	@Inject
	private Neo4jTemplate template;

	@Inject
	private NameRepository nameRepository;

	@Inject
	private NeedRepository needRepository;

	@Override
	@Transactional
	@Fetch
	public Name createName( Name name ) {
		return nameRepository.save( name );
	}

	@Override
	@Transactional
	@Fetch
	public Need createNeed( Need need ) {
		return needRepository.save( need );
	}

	@Override
	@Fetch
	public Collection<Name> findNames() {
		return Lists.newArrayList( nameRepository.findAll() );
	}

	@Override
	@Fetch
	public Collection<Name> findNamesByName( String name ) {
		String pattern = ".*" + name.trim().toLowerCase() + ".*";
		return nameRepository.findByName( pattern );
	}

	@Override
	@Fetch
	public Collection<Need> findNeeds() {
		return Lists.newArrayList( needRepository.findAll() );
	}

	@Override
	@Fetch
	public Collection<Need> findNeedsByDescr( String descr ) {
		String pattern = ".*" + descr.trim().toLowerCase() + ".*";
		return needRepository.findByDescr( pattern );
	}

	@Override
	@Transactional
	@Fetch
	public Asking createAsking( Name name, int quantity, Need need ) {
		/*
		 * Name#asksFor(..) vs. RelationshipOperationsRepository#createRelationshipBetween(..):
		 *
		 * A)
		 * cf. http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#reference_programming_model_relationships_relatedto
		 *
		 * "If this Set of related entities is modified, the changes are reflected in the graph, relationships are added, removed or updated accordingly.
		 * Spring Data Neo4j ensures by default that there is only one relationship of a given type between any two given entities.
		 *
		 * This can be circumvented by using the createRelationshipBetween() method with the allowDuplicates parameter on repositories or entities."
		 *
		 * B)
		 * cf. http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#relationshipentity_rich_relationships
		 *
		 * "Relationship entities either be instantiated directly and set or added to @RelatedToVia-annotated fields or created by [...]
		 * template|repository.createRelationshipBetween() [...]"
		 *
		 * C)
		 * cf. http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#creating_relationships
		 *
		 * "Role role = tomHanks.playedIn(forrestGump, "Forrest Gump");
		 * // either save the actor
		 * template.save(tomHanks);
		 * // or the role
		 * template.save(role);
		 *
		 * // alternative approach
		 * Role role = template.createRelationshipBetween(actor,movie,Role.class, "ACTS_IN");
		 *
		 * Saving just the actor would take care of relationships with the same type between two entities and remove the duplicates.
		 * Whereas just saving the role happily creates another relationship with the same type."
		 */

		// Asking here is unattached i.e. is a non-persisted instance without populated id-field
		//Asking asking = name.asksFor( need, quantity );
		//nameRepository.save( name );

		// delegates to Neo4jTemplate.createRelationshipBetween(..) and Asking here should be attached/persisted i.e. carry the populated id-field
		//Asking asking = nameRepository.createRelationshipBetween( name, need, Asking.class, "ASKS_FOR" );
		//asking.setQuantity( quantity );
		// how to persist the changed property?

		//return asking;

		// inject and use the Neo4jTemplate
		Asking asking = template.createRelationshipBetween( name, need, Asking.class, "ASKS_FOR", false );
		asking.setQuantity( quantity );
		// only save and not reload the entity from the database
		template.saveOnly( asking );
		return asking;
	}

	@Override
	@Fetch
	public Collection<Name> findNear( double lat, double lon, double distanceKm ) {
		return Lists.newArrayList( nameRepository.findWithinDistance( Name.INDEX_POSITION, lat, lon, distanceKm ) );
	}

}
