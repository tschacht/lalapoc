package lalapoc.business;

import lalapoc.LalapocApplication;
import lalapoc.entity.Need;
import lalapoc.entity.factory.NeedFactory;
import lalapoc.repository.NeedRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LalapocApplication.class)
public class NeedRepositoryIT {

	@Inject
	private NeedRepository testling;

	@Test
	public void testPersist() throws Exception {
		Need need = NeedFactory.newNeed( "Pants" );
		testling.saveOnly( need );

		Need sameNeed = testling.findOne( need.getId() );

		assertThat( sameNeed, notNullValue() );
		assertThat( sameNeed.getDescription(), notNullValue() );
		assertThat( sameNeed.getDescription(), equalTo( need.getDescription() ) );
		assertThat( need, equalTo( sameNeed ) );
	}

}
