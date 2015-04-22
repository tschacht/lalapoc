package lalapoc.repository;

import lalapoc.entity.SampleNode;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface SampleNodeRepository extends GraphRepository<SampleNode> {
}
