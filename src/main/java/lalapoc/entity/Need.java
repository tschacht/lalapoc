package lalapoc.entity;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.*;

import java.util.Collection;

import static org.springframework.data.neo4j.support.index.IndexType.FULLTEXT;

@NodeEntity
public class Need {

	@GraphId
	private Long id;

	@Indexed(unique = true, indexType = FULLTEXT, indexName = "search_need")
	private String description;

	//@RelatedTo( type = "ASKS_FOR", direction = Direction.INCOMING )
	//private Set<Name> requester;

	@Fetch
	@RelatedToVia(type = "ASKS_FOR", direction = Direction.INCOMING)
	private Collection<Solicitation> solicitations;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public Collection<Solicitation> getSolicitations() {
		return solicitations;
	}

}
