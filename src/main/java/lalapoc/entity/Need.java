package lalapoc.entity;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Set;

import static org.springframework.data.neo4j.support.index.IndexType.FULLTEXT;

@NodeEntity
public class Need {

	@GraphId
	private Long id;

	@Indexed( unique = true, indexType = FULLTEXT, indexName = "search_need" )
	private String name;

	@RelatedTo( type = "ASKS_FOR", direction = Direction.INCOMING )
	private Set<Name> requester;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

}
