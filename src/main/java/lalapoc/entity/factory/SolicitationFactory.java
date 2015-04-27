package lalapoc.entity.factory;

import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.Solicitation;

public abstract class SolicitationFactory {

	public static Solicitation newSolicitation( Name name, int quantity, Need need ) {
		Solicitation solicitation = new Solicitation();

		solicitation.setName( name );
		solicitation.setQuantity( quantity );
		solicitation.setNeed( need );

		return solicitation;
	}

}
