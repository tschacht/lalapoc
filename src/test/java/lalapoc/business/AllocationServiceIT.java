package lalapoc.business;

import lalapoc.LalapocApplication;
import lalapoc.entity.Name;
import lalapoc.entity.factory.NameFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LalapocApplication.class)
public class AllocationServiceIT {

	@Inject
	private AllocationServiceMethods testling;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateNameFeedsTimelineIndexCorrectly() throws Exception {
		ZonedDateTime now = ZonedDateTime.now();
		Name name = NameFactory.newName( "Name IT", 3, 0, 0, now );
		ZonedDateTime before = now.minusDays( 5 );
		ZonedDateTime after = now.plusSeconds( 7 );

		testling.createName( name );

		Collection<Name> result = testling.findBetween( before.toInstant(), after.toInstant() );

		assertThat( result, notNullValue() );
		assertThat( result.size(), greaterThan( 0 ) );

		Set<Long> resultIds = new HashSet<>();
		for( Name resultName : result ) {
			resultIds.add( resultName.getId() );
		}

		assertThat( resultIds, contains( name.getId() ) );
	}

}
