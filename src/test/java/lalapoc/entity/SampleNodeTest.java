package lalapoc.entity;

import lalapoc.entity.factory.SampleNodeFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

// cf. reference: http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#graphid_neo4j_id_field
public class SampleNodeTest {

	private SampleNode node_1_Id;
	private SampleNode node_1b_Id;
	private SampleNode node_2_Id;

	private SampleNode node_1_unattached;
	private SampleNode node_2_unattached;

	@Before
	public void before() {
		node_1_unattached = SampleNodeFactory.newSampleNode( "node1" );
		node_2_unattached = SampleNodeFactory.newSampleNode( "node2" );

		node_1_Id = SampleNodeFactory.newSampleNode( "node1" );
		node_1_Id.setId( 1L );
		node_1b_Id = SampleNodeFactory.newSampleNode( "node1" );
		node_1b_Id.setId( 1L );

		node_2_Id = SampleNodeFactory.newSampleNode( "node2" );
		node_2_Id.setId( 2L );
	}

	@Test
	public void testEquals() throws Exception {
		// quote reference:
		// 1. Before you attach an entity to the database, i.e. before the entity has had its id-field populated,
		//    we suggest you rely on object identity for comparisons.
		// 2. Once an entity is attached, we suggest you rely solely on the id-field for equality.

		// equal to self
		assertThat( node_1_unattached, equalTo( node_1_unattached ) );
		assertThat( node_2_unattached, equalTo( node_2_unattached ) );
		assertThat( node_1_Id, equalTo( node_1_Id ) );
		assertThat( node_2_Id, equalTo( node_2_Id ) );

		// equal to other object with same id and same properties
		assertThat( node_1_Id, equalTo( node_1b_Id ) );
		// consistency with hashCode()
		assertThat( node_1_Id.hashCode(), equalTo( node_1b_Id.hashCode() ) );

		// not equal to other object with other id
		assertThat( node_1_Id, not( equalTo( node_2_Id ) ) );
		// also not equal to node with same properties but without id
		assertThat( node_1_unattached, not( equalTo( node_1_Id ) ) );
		assertThat( node_2_unattached, not( equalTo( node_2_Id ) ) );

		// completely different objects not equal
		assertThat( node_1_unattached, not( equalTo( node_2_Id ) ) );
		assertThat( node_2_unattached, not( equalTo( node_1_Id ) ) );
		assertThat( node_1_unattached, not( equalTo( node_2_unattached ) ) );
	}

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

		assertThat( node_1_Id, equalTo( node_1b_Id ) );
		assertThat( node_1_Id.hashCode(), not( equalTo( node_1b_Id.hashCode() ) ) );
	}

	// quote reference:
	// A work-around for the problem of un-attached entities having their hashcode change when they get saved is to cache the hashcode.
	// The hashcode will change next time you load the entity, but at least if you have the entity sitting in a collection,
	// you will still be able to find it

	@Test
	public void testHashCodeSameAfterAttach() throws Exception {
		SampleNode n = SampleNodeFactory.newSampleNode( "n" );

		int hashBefore = n.hashCode();
		// simulate persist/attach to graph in db
		n.setId( 123L );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, equalTo( hashAfter ) );
	}

	@Test
	public void testHashCodeDifferentAfterReload() throws Exception {
		SampleNode n = SampleNodeFactory.newSampleNode( "n" );

		int hashBefore = n.hashCode();
		// simulate reload from db
		n.setHash( null );
		n.setId( 123L );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

	@Ignore // TODO: must the hash of the same object change when a property other than id changes?
	@Test
	public void testHashCodeDifferentAfterPropertyChange() throws Exception {
		SampleNode n = SampleNodeFactory.newSampleNode( "n" );

		int hashBefore = n.hashCode();
		n.setName( "nn" );
		int hashAfter = n.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

}
