package lalapoc.entity.factory;

import lalapoc.entity.Need;

public abstract class NeedFactory {

	public static Need newNeed( String description ) {
		Need result = new Need();
		result.setDescription( description );
		return result;
	}

}
