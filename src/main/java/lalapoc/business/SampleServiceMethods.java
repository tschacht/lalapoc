package lalapoc.business;

import lalapoc.entity.SampleNode;

import java.util.Collection;

public interface SampleServiceMethods {

	Collection<SampleNode> readSampleNodes();

	Collection<SampleNode> readNodesByCustomQuery();

	Collection<SampleNode> readNodesByNumber( Long number );

	SampleNode createSampleNode();

	Collection<SampleNode> readTyped( Long id );

}
