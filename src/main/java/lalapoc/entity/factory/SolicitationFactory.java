package lalapoc.entity.factory;

import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.Solicitation;

public abstract class SolicitationFactory {

	public static Solicitation newSolicitation( Name name, int quantity, Need need ) {
		return newSolicitation( name, quantity, need, null );
	}

	public static Solicitation newSolicitation( Name name, int quantitiy, Need need, Long id ) {
		Solicitation solicitation = new Solicitation();

		if( id != null ) {
			solicitation.setId( id );
		}

		solicitation.setName( name );
		solicitation.setQuantity( quantitiy );
		solicitation.setNeed( need );

		return solicitation;
	}

}
