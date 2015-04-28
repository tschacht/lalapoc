package lalapoc.entity;

import org.springframework.data.neo4j.annotation.GraphId;

public abstract class BaseEntity {

	@GraphId
	protected Long id;

	transient private Integer hash;

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	// cf. http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#graphid_neo4j_id_field
	@Override
	public int hashCode() {
		if( hash == null ) hash = id == null ? System.identityHashCode( this ) : id.hashCode();
		return hash.hashCode();
	}

}
