package lalapoc.entity;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.data.neo4j.annotation.GraphId;

public abstract class BaseEntity {

	@GraphId
	protected Long id;
	transient private Integer hash;

	@VisibleForTesting
	void setHash( Integer hash ) {
		this.hash = hash;
	}

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	// cf. http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#graphid_neo4j_id_field
	// 1. Before you attach an entity to the database, i.e. before the entity has had its id-field populated,
	//    we suggest you rely on object identity for comparisons.
	// 2. Once an entity is attached, we suggest you rely solely on the id-field for equality.

	@Override
	public final boolean equals( Object other ) {
		return this == other || id != null && other instanceof BaseEntity && id.equals( ( (BaseEntity) other ).id );
	}

	@Override
	public final int hashCode() {
		if( hash == null ) hash = id == null ? System.identityHashCode( this ) : id.hashCode();
		return hash.hashCode();
	}

}
