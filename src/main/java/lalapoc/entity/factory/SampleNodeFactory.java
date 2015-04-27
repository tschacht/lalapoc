package lalapoc.entity.factory;

import lalapoc.entity.SampleNode;

public abstract class SampleNodeFactory {

	public static SampleNode newSampleNode( String name ) {
		SampleNode result = new SampleNode();
		result.setName( name );
		return result;
	}

}
