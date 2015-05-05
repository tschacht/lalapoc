package lalapoc.entity.index;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.index.lucene.LuceneTimeline;
import org.neo4j.index.lucene.TimelineIndex;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class LuceneTimelineIndexFactory {

	@Inject
	private GraphDatabaseService graphDatabaseService;

	// TODO: org.neo4j.index.lucene.TimelineIndex<T> vs. org.neo4j.index.timeline.TimelineIndex?
	private TimelineIndex<Node> nodeIndex;

	@Transactional
	public TimelineIndex<Node> getNodeInstance() {
		if( nodeIndex == null ) {
			Index<Node> nodeIndex = graphDatabaseService.index().forNodes( "timeline" );
			this.nodeIndex = new LuceneTimeline<>( graphDatabaseService, nodeIndex );
		}
		return nodeIndex;
	}

}
