package lalapoc.entity;

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

public class SolicitationTest {

	private final int PEOPLE = 5;
	private final double LAT = 85;
	private final double LON = 9;
	private final LocalTime TIME = LocalTime.now();

	private final Name name1 = NameFactory.newName( "name1", PEOPLE, LAT, LON, TIME );
	private final Name name2 = NameFactory.newName( "name2", PEOPLE, LAT, LON, TIME );

	private final Need need1 = NeedFactory.newNeed( "descr1" );
	private final Need need2 = NeedFactory.newNeed( "descr2" );

	private Solicitation solicitation1WithId;
	private Solicitation solicitationWithSameIdAs1;
	private Solicitation solicitation2WithId;

	private Solicitation solicitation1NoId;
	private Solicitation solicitation2NoId;

	@Before
	public void before() {
		solicitation1NoId = SolicitationFactory.newSolicitation( name1, 1, need1 );
		solicitation2NoId = SolicitationFactory.newSolicitation( name2, 2, need2 );

		solicitation1WithId = SolicitationFactory.newSolicitation( name1, 1, need1 );
		solicitation1WithId.setId( 1L );
		solicitationWithSameIdAs1 = SolicitationFactory.newSolicitation( name1, 3, need2 );
		solicitationWithSameIdAs1.setId( 1L );

		solicitation2WithId = SolicitationFactory.newSolicitation( name2, 2, need2 );
		solicitation2WithId.setId( 2L );
	}

	@Test
	public void testEquals() throws Exception {
		// equal to self
		assertThat( solicitation1NoId, equalTo( solicitation1NoId ) );
		assertThat( solicitation2NoId, equalTo( solicitation2NoId ) );
		assertThat( solicitation1WithId, equalTo( solicitation1WithId ) );
		assertThat( solicitation2WithId, equalTo( solicitation2WithId ) );

		// equal to other object with same id but different quantity property
		assertThat( solicitation1WithId, equalTo( solicitationWithSameIdAs1 ) );
		// consistency with hashCode()
		assertThat( solicitation1WithId.hashCode(), equalTo( solicitationWithSameIdAs1.hashCode() ) );

		// not equal to other object with other id
		assertThat( solicitation1NoId, not( equalTo( solicitation1WithId ) ) );
		assertThat( solicitation2NoId, not( equalTo( solicitation2WithId ) ) );
		assertThat( solicitation1WithId, not( equalTo( solicitation2WithId ) ) );

		// completely different objects not equal
		assertThat( solicitation1NoId, not( equalTo( solicitation2WithId ) ) );
		assertThat( solicitation2NoId, not( equalTo( solicitation1WithId ) ) );
		assertThat( solicitation1NoId, not( equalTo( solicitation2NoId ) ) );
	}

	@Test
	public void testHashCodeSameAfterAttach() throws Exception {
		Solicitation n = SolicitationFactory.newSolicitation( name1, 5, need1 );

		int hashBefore = n.hashCode();
		// simulate persist/attach to graph in db
		n.setId( 123L );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, equalTo( hashAfter ) );
	}

	@Test
	public void testHashCodeDifferentAfterReload() throws Exception {
		Solicitation n = SolicitationFactory.newSolicitation( name1, 5, need1 );

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
		Solicitation n = SolicitationFactory.newSolicitation( name1, 5, need1 );

		int hashBefore = n.hashCode();
		n.setQuantity( 10 );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

}
