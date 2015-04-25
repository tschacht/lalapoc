package lalapoc.entity.factory;

import lalapoc.entity.Name;

import java.time.LocalTime;

public abstract class NameFactory {

	public static Name newName( String name, int people, String position, LocalTime time ) {
		return newName(name, people, position, time, null);
	}

	public static Name newName( String name, int people, String position, LocalTime time, Long id ) {
		Name result = new Name();

		if( id != null ) {
			result.setId(id);
		}

		result = new Name();
		result.setName(name);
		result.setPeople(people);
		result.setPosition(position);
		result.setTime(time);

		return result;
	}

}
