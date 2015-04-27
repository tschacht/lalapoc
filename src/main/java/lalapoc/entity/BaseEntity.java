package lalapoc.entity;

import org.springframework.data.neo4j.annotation.GraphId;

public abstract class BaseEntity {

	@GraphId
	protected Long id;

	transient private Integer hash;

	public Long getId() {
		return id;
	}

	// cf. http://docs.spring.io/spring-data/data-neo4j/docs/current/reference/html/#graphid_neo4j_id_field

	public void setId( Long id ) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		if( hash == null ) hash = id == null ? System.identityHashCode( this ) : id.hashCode();
		return hash.hashCode();
	}

}
