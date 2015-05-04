package lalapoc.business;

import lalapoc.LalapocApplication;
import lalapoc.entity.Name;
import lalapoc.entity.factory.NameFactory;
import lalapoc.repository.NameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.ZonedDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LalapocApplication.class)
public class NameRepositoryIT {

	@Inject
	private NameRepository testling;

	@Test
	public void testPersistWithZonedDateTime() throws Exception {
		Name name = NameFactory.newName( "Time Test", 3, 0, 0, ZonedDateTime.now() );
		testling.saveOnly( name );

		Name sameName = testling.findOne( name.getId() );

		assertThat( sameName, notNullValue() );
		assertThat( sameName.getTime(), notNullValue() );
		assertThat( sameName.getTime(), equalTo( name.getTime() ) );
		assertThat( name, equalTo( sameName ) );
	}

}
