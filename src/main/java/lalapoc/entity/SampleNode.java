package lalapoc.entity;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import static org.springframework.data.neo4j.support.index.IndexType.FULLTEXT;

@NodeEntity
public class SampleNode extends BaseEntity {

	@Indexed(unique = true, indexType = FULLTEXT, indexName = "search_sample")
	private String name;

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public boolean equals( Object other ) {
		return this == other || id != null && other instanceof SampleNode && id.equals( ( (SampleNode) other ).id );
	}

}
