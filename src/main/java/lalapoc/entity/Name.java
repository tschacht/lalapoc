package lalapoc.entity;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.time.LocalTime;
import java.util.Set;

import static org.springframework.data.neo4j.support.index.IndexType.FULLTEXT;

@NodeEntity
public class Name {

	@GraphId
	private Long id;

	@Indexed( unique = true, indexType = FULLTEXT, indexName = "search_name" )
	private String name;

	private int people;

	private LocalTime time;

	private String position;

	@RelatedTo( type = "ASKS_FOR", direction = Direction.OUTGOING )
	private Set<Need> needs;

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

	public int getPeople() {
		return people;
	}

	public void setPeople( int people ) {
		this.people = people;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime( LocalTime time ) {
		this.time = time;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition( String position ) {
		this.position = position;
	}

}
