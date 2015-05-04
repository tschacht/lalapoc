package lalapoc.business;

import lalapoc.entity.Sample;

import java.util.Collection;

public interface SampleServiceMethods {

	Collection<Sample> readSamples();

	Collection<Sample> readSamplesByCustomQuery();

	Collection<Sample> readSamplesByNumber( Long number );

	Sample createSample();

	Collection<Sample> readTyped( Long number );

}
