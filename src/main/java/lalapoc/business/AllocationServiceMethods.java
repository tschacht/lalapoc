package lalapoc.business;

import lalapoc.entity.Asking;
import lalapoc.entity.Name;
import lalapoc.entity.Need;

import java.time.Instant;
import java.util.Collection;

public interface AllocationServiceMethods {

	Name createName( Name name );

	Need createNeed( Need need );

	Collection<Name> findNames();

	Collection<Name> findNamesByName( String name );

	Collection<Need> findNeeds();

	Collection<Need> findNeedsByDescr( String descr );

	Asking createAsking( Name name, int quantity, Need need );

	Collection<Name> findNear( double lat, double lon, double distanceKm );

	Collection<Name> findBetween( Instant start, Instant end );

}
