package lalapoc.business;

import lalapoc.entity.SampleNode;

public interface SampleServiceMethods {

 Iterable<SampleNode> readSampleNodes();

 Iterable<SampleNode> readNodesByCustomQuery();

 Iterable<SampleNode> readNodesByNumber(Long number);

 SampleNode createSampleNode();

}
