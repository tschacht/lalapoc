package lalapoc.business;

import lalapoc.entity.Name;
import lalapoc.entity.Need;

import java.util.Collection;

public interface RescueServiceMethods {

	Name createName();

	Need createNeed();

	Collection<Name> findNames();

	Collection<Name> findNamesByName();

	Collection<Need> findNeeds();

	Collection<Need> findNeedsByDescr();

}
