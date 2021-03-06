package lalapoc.entity;

import com.google.common.annotations.VisibleForTesting;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedToVia;

import java.util.Set;

import static org.springframework.data.neo4j.support.index.IndexType.FULLTEXT;

@NodeEntity
public class Need extends BaseEntity {

	public static final String INDEX_SEARCH_NEED = "search_need";

	@Indexed(unique = true, indexType = FULLTEXT, indexName = INDEX_SEARCH_NEED)
	private String description;

	//@RelatedTo( type = "ASKS_FOR", direction = Direction.INCOMING )
	//private Set<Name> requester;

	@Fetch
	@RelatedToVia(type = "ASKS_FOR", direction = Direction.INCOMING)
	private Set<Asking> askings;

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public Set<Asking> getAskings() {
		return askings;
	}

	@VisibleForTesting
	void setAskings( Set<Asking> askings ) {
		this.askings = askings;
	}

}
