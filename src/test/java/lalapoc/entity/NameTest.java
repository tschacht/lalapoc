package lalapoc.entity;

import com.google.common.collect.Sets;
import lalapoc.entity.factory.NameFactory;
import lalapoc.entity.factory.NeedFactory;
import lalapoc.entity.factory.SolicitationFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class NameTest {

	private final int PEOPLE = 5;
	private final double LAT = 85;
	private final double LON = 9;
	private final LocalTime TIME = LocalTime.now();

	private Name name1WithId;
	private Name nameWithSameIdAs1;
	private Name name2WithId;

	private Name name1NoId;
	private Name name2NoId;

	@Before
	public void before() {
		name1NoId = NameFactory.newName( "name1", PEOPLE, LAT, LON, TIME );
		name2NoId = NameFactory.newName( "name2", PEOPLE, LAT, LON, TIME );

		name1WithId = NameFactory.newName( "name1", PEOPLE, LAT, LON, TIME );
		name1WithId.setId( 1L );
		nameWithSameIdAs1 = NameFactory.newName( "same id", PEOPLE, LAT, LON, TIME );
		nameWithSameIdAs1.setId( 1L );

		name2WithId = NameFactory.newName( "name2", PEOPLE, LAT, LON, TIME );
		name2WithId.setId( 2L );

		Need need1WithId = NeedFactory.newNeed( "descr1" );
		need1WithId.setId( 100L );

		Solicitation solicitation1 = SolicitationFactory.newSolicitation( name1WithId, 5, need1WithId );

		name1WithId.setSolicitations( Sets.newHashSet( solicitation1 ) );
		need1WithId.setSolicitations( Sets.newHashSet( solicitation1 ) );
	}

	@Test
	public void testEquals() throws Exception {
		// equal to self
		assertThat( name1NoId, equalTo( name1NoId ) );
		assertThat( name2NoId, equalTo( name2NoId ) );
		assertThat( name1WithId, equalTo( name1WithId ) );
		assertThat( name2WithId, equalTo( name2WithId ) );

		// equal to other object with same id but different name property
		assertThat( name1WithId, equalTo( nameWithSameIdAs1 ) );
		// consistency with hashCode()
		assertThat( name1WithId.hashCode(), equalTo( nameWithSameIdAs1.hashCode() ) );

		// not equal to other object with other id
		assertThat( name1NoId, not( equalTo( name1WithId ) ) );
		assertThat( name2NoId, not( equalTo( name2WithId ) ) );
		assertThat( name1WithId, not( equalTo( name2WithId ) ) );

		// completely different objects not equal
		assertThat( name1NoId, not( equalTo( name2WithId ) ) );
		assertThat( name2NoId, not( equalTo( name1WithId ) ) );
		assertThat( name1NoId, not( equalTo( name2NoId ) ) );
	}

	@Test
	public void testHashCodeSameAfterAttach() throws Exception {
		Name n = NameFactory.newName( "n", PEOPLE, LAT, LON, TIME );

		int hashBefore = n.hashCode();
		// simulate persist/attach to graph in db
		n.setId( 123L );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, equalTo( hashAfter ) );
	}

	@Test
	public void testHashCodeDifferentAfterReload() throws Exception {
		Name n = NameFactory.newName( "n", PEOPLE, LAT, LON, TIME );

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
		Name n = NameFactory.newName( "n", PEOPLE, LAT, LON, TIME );

		int hashBefore = n.hashCode();
		n.setName( "nn" );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

}
