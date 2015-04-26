package lalapoc.business;

import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.Solicitation;

import java.util.Collection;

public interface RescueServiceMethods {

	Name createName( Name name );

	Need createNeed( Need need );

	Collection<Name> findNames();

	Collection<Name> findNamesByName( String name );

	Collection<Need> findNeeds();

	Collection<Need> findNeedsByDescr( String descr );

	Solicitation createSolicitation( Name name, int quantity, Need need );

}
