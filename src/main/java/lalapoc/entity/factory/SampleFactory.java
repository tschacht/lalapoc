package lalapoc.entity.factory;

import lalapoc.entity.Sample;

public abstract class SampleFactory {

	public static Sample newSample( String name ) {
		Sample result = new Sample();
		result.setName( name );
		return result;
	}

}
