package lalapoc.entity.factory;

import lalapoc.entity.Need;

public abstract class NeedFactory {

	public static Need newNeed( String description ) {
		return newNeed(description, null);
	}

	public static Need newNeed( String description, Long id ) {
		Need result = new Need();

		if( id != null ) {
			result.setId(id);
		}

		result.setDescription(description);

		return result;
	}

}
