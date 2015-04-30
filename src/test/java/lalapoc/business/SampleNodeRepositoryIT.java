package lalapoc.business;

import lalapoc.LalapocApplication;
import lalapoc.entity.SampleNode;
import lalapoc.entity.factory.SampleNodeFactory;
import lalapoc.repository.SampleNodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LalapocApplication.class)
public class SampleNodeRepositoryIT {

	@Inject
	private SampleNodeRepository testling;

	@Test
	public void testEqualsFollowingReference() throws Exception {
		// example from the reference:
		/*
		Studio studio = new Studio("Ghibli")
		studioRepository.save(studio); // this populates the id field of studio
		Studio sameStudio = studioRepository.findOne(studio.id);
		assertThat(studio, is(equalTo(sameStudio));
		assertThat(studio.hashCode(), is(not( equalTo( sameStudio.hashCode() ) ));
		*/

		SampleNode node = SampleNodeFactory.newSampleNode( "node" );

		testling.save( node );

		SampleNode sameNode = testling.findOne( node.getId() );

		assertThat( node, equalTo( sameNode ) );
		assertThat( node.hashCode(), not( equalTo( sameNode.hashCode() ) ) );
	}

}
