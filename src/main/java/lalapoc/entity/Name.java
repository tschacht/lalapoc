package lalapoc.entity;

import lalapoc.entity.factory.SolicitationFactory;
import org.neo4j.graphdb.Direction;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.support.index.IndexType.FULLTEXT;
import static org.springframework.data.neo4j.support.index.IndexType.POINT;

@NodeEntity
public class Name extends BaseEntity {

	public static final String INDEX_SEARCH_NAME = "search_name";
	public static final String INDEX_POSITION = "position";

	@Indexed(unique = true, indexType = FULLTEXT, indexName = INDEX_SEARCH_NAME)
	private String name;

	private int people;

	private LocalTime time;

	@Indexed(indexType = POINT, indexName = INDEX_POSITION)
	//private String wkt; // must be named "wkt"
	private Point wkt; // must be named "wkt"

	//@RelatedTo( type = "ASKS_FOR", direction = Direction.OUTGOING )
	//private Set<Need> needs;

	@Fetch
	@RelatedToVia(type = "ASKS_FOR", direction = Direction.OUTGOING)
	private Set<Solicitation> solicitations;

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

	public Point getPosition() {
		return wkt;
	}

	public void setPosition( Point position ) {
		//this.wkt = String.format( "POINT( %.4f %.4f )", position.getY(), position.getX() );
		this.wkt = position;
	}

	public Set<Solicitation> getSolicitations() {
		return solicitations;
	}

	/**
	 * Returns an unattached {@link Solicitation} without populated id-field.
	 *
	 * @deprecated One should use NameRepository#createRelationshipBetween(..) instead,
	 * but its unclear, how one should deal with properties on the @RelationshipEntity.
	 */
	@Deprecated
	public Solicitation asksFor( Need need, int quantity ) {
		Solicitation solicitation = SolicitationFactory.newSolicitation( this, quantity, need );

		if( solicitations == null ) {
			solicitations = new HashSet<>();
		}
		solicitations.add( solicitation );

		return solicitation;
	}

	@Override
	public boolean equals( Object other ) {
		return this == other || id != null && other instanceof Name && id.equals( ( (Name) other ).id );
	}

}
