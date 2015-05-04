package lalapoc.entity;

import com.google.common.annotations.VisibleForTesting;
import lalapoc.entity.factory.AskingFactory;
import org.neo4j.graphdb.Direction;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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

	private ZonedDateTime time;

	@Indexed(indexType = POINT, indexName = INDEX_POSITION)
	//private String wkt; // must be named "wkt"
	private Point wkt; // must be named "wkt"

	//@RelatedTo( type = "ASKS_FOR", direction = Direction.OUTGOING )
	//private Set<Need> needs;

	@Fetch
	@RelatedToVia(type = "ASKS_FOR", direction = Direction.OUTGOING)
	private Set<Asking> askings;

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

	public ZonedDateTime getTime() {
		return time;
	}

	public void setTime( ZonedDateTime time ) {
		// convert to GMT
		this.time = time == null ? null : ZonedDateTime.ofInstant( time.toInstant(), ZoneId.of( "GMT" ) );
	}

	public Point getPosition() {
		return wkt;
	}

	public void setPosition( Point position ) {
		// latitude -> y-axis (move vertically), longitude -> x-axis (move horizontally)
		//this.wkt = String.format( "POINT( %.4f %.4f )", position.getY(), position.getX() );
		this.wkt = position;
	}

	public Set<Asking> getAskings() {
		return askings;
	}

	@VisibleForTesting
	void setAskings( Set<Asking> askings ) {
		this.askings = askings;
	}

	/**
	 * Returns an unattached {@link Asking} without populated id-field.
	 *
	 * @deprecated One should use NameRepository#createRelationshipBetween(..) instead,
	 * but its unclear, how one should deal with properties on the @RelationshipEntity.
	 */
	@Deprecated
	public Asking asksFor( Need need, int quantity ) {
		Asking asking = AskingFactory.newAsking( this, quantity, need );

		if( askings == null ) {
			askings = new HashSet<>();
		}
		askings.add( asking );

		return asking;
	}

}
