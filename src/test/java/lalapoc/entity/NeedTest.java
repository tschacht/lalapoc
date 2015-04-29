package lalapoc.entity;

import com.google.common.collect.Sets;
import lalapoc.entity.factory.NameFactory;
import lalapoc.entity.factory.NeedFactory;
import lalapoc.entity.factory.SolicitationFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class NeedTest {

	private Need need1WithId;
	private Need needWithSameIdAs1;
	private Need need2WithId;

	private Need need1NoId;
	private Need need2NoId;

	@Before
	public void before() {
		need1NoId = NeedFactory.newNeed( "descr1" );
		need2NoId = NeedFactory.newNeed( "descr2" );

		need1WithId = NeedFactory.newNeed( "descr1" );
		need1WithId.setId( 1L );
		needWithSameIdAs1 = NeedFactory.newNeed( "same id" );
		needWithSameIdAs1.setId( 1L );

		need2WithId = NeedFactory.newNeed( "descr2" );
		need2WithId.setId( 2L );

		int PEOPLE = 5;
		double LAT = 85;
		double LON = 9;
		LocalTime TIME = LocalTime.now();
		Name name1WithId = NameFactory.newName( "name1", PEOPLE, LAT, LON, TIME );
		name1WithId.setId( 100L );

		Solicitation solicitation1 = SolicitationFactory.newSolicitation( name1WithId, 5, need1WithId );

		need1WithId.setSolicitations( Sets.newHashSet( solicitation1 ) );
		name1WithId.setSolicitations( Sets.newHashSet( solicitation1 ) );
	}

	@Test
	public void testEquals() throws Exception {
		// equal to self
		assertThat( need1NoId, equalTo( need1NoId ) );
		assertThat( need2NoId, equalTo( need2NoId ) );
		assertThat( need1WithId, equalTo( need1WithId ) );
		assertThat( need2WithId, equalTo( need2WithId ) );

		// equal to other object with same id but different description property
		assertThat( need1WithId, equalTo( needWithSameIdAs1 ) );
		// consistency with hashCode()
		assertThat( need1WithId.hashCode(), equalTo( needWithSameIdAs1.hashCode() ) );

		// not equal to other object with other id
		assertThat( need1NoId, not( equalTo( need1WithId ) ) );
		assertThat( need2NoId, not( equalTo( need2WithId ) ) );
		assertThat( need1WithId, not( equalTo( need2WithId ) ) );

		// completely different objects not equal
		assertThat( need1NoId, not( equalTo( need2WithId ) ) );
		assertThat( need2NoId, not( equalTo( need1WithId ) ) );
		assertThat( need1NoId, not( equalTo( need2NoId ) ) );
	}

	@Test
	public void testHashCodeSameAfterAttach() throws Exception {
		Need n = NeedFactory.newNeed( "n" );

		int hashBefore = n.hashCode();
		// simulate persist/attach to graph in db
		n.setId( 123L );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, equalTo( hashAfter ) );
	}

	@Test
	public void testHashCodeDifferentAfterReload() throws Exception {
		Need n = NeedFactory.newNeed( "n" );

		int hashBefore = n.hashCode();
		// simulate reload from db
		n.setHash( null );
		n.setId( 123L );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

	@Ignore // see SampleNodeTest.testHashCodeDifferentAfterPropertyChange()
	@Test
	public void testHashCodeDifferentAfterPropertyChange() throws Exception {
		Need n = NeedFactory.newNeed( "n" );

		int hashBefore = n.hashCode();
		n.setDescription( "nn" );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

}
