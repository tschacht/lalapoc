package lalapoc.entity.factory;

import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.Asking;

public abstract class AskingFactory {

	public static Asking newAsking( Name name, int quantity, Need need ) {
		Asking asking = new Asking();

		asking.setName( name );
		asking.setQuantity( quantity );
		asking.setNeed( need );

		return asking;
	}

}
