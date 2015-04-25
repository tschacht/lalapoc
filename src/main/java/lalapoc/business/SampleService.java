package lalapoc.business;

import lalapoc.entity.SampleNode;
import lalapoc.repository.SampleNodeRepository;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Random;

public class SampleService implements SampleServiceMethods {

 @Inject
 private SampleNodeRepository sampleNodeRepository;

 @Override
 @Transactional
 @Fetch
 public Iterable<SampleNode> readSampleNodes() {
	return sampleNodeRepository.findAll();
 }

 @Override
 @Transactional
 @Fetch
 public Iterable<SampleNode> readNodesByCustomQuery() {
	return sampleNodeRepository.findByCustomQuery();
 }

 @Override
 @Transactional
 @Fetch
 public Iterable<SampleNode> readNodesByNumber(Long number) {
	return sampleNodeRepository.findByCustomPatternQuery("pipapo_" + number + ".*");
 }

 @Override
 @Transactional
 @Fetch
 public SampleNode createSampleNode() {
	SampleNode n = new SampleNode();
	Random r = new Random();
	n.setName("pipapo_" + r.nextInt(10) + " - " + Instant.now());
	sampleNodeRepository.save(n);
	return n;
 }

}
