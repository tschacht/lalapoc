package lalapoc.entity;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "ASKS_FOR")
public class Solicitation extends BaseEntity {

	private int quantity;

	@StartNode
	private Name name;

	@EndNode
	private Need need;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity( int quantity ) {
		this.quantity = quantity;
	}

	public Name getName() {
		return name;
	}

	public void setName( Name name ) {
		this.name = name;
	}

	public Need getNeed() {
		return need;
	}

	public void setNeed( Need need ) {
		this.need = need;
	}

	/*
	@Override
	public boolean equals( Object other ) {
		return this == other || id != null && other instanceof Solicitation && id.equals( ( (Solicitation) other ).id );
	}

	@Override
	public int hashCode() {
		return super.hashCode() +
				( name == null ? 0 : name.hashCode() ) +
				quantity +
				( need == null ? 0 : need.hashCode() );
	}
	*/

}
