package lalapoc.entity;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "ASKS_FOR")
public class Asking extends BaseEntity {

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

}
