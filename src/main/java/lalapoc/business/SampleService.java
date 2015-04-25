package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.SampleNode;
import lalapoc.repository.SampleNodeRepository;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.Instant;
import java.util.Collection;
import java.util.Random;

@Named
public class SampleService implements SampleServiceMethods {

 @Inject
 private SampleNodeRepository sampleNodeRepository;

 @Override
 @Transactional
 @Fetch
 public Collection<SampleNode> readSampleNodes() {
	return Lists.newArrayList(sampleNodeRepository.findAll());
 }

 @Override
 @Transactional
 @Fetch
 public Collection<SampleNode> readNodesByCustomQuery() {
	return sampleNodeRepository.findByCustomQuery();
 }

 @Override
 @Transactional
 @Fetch
 public Collection<SampleNode> readNodesByNumber(Long number) {
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
