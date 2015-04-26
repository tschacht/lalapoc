package lalapoc.entity.factory;

import lalapoc.entity.SampleNode;

public abstract class SampleNodeFactory {

	public static SampleNode newSampleNode( String name ) {
		return newSampleNode( name, null );
	}

	public static SampleNode newSampleNode( String name, Long id ) {
		SampleNode result = new SampleNode();

		if( id != null ) {
			result.setId( id );
		}

		result.setName( name );

		return result;
	}

}
