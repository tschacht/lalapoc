package lalapoc.entity;

import lalapoc.entity.factory.SampleNodeFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

// cf. reference: http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#graphid_neo4j_id_field
public class SampleNodeTest {

	private SampleNode sampleNode1WithId;
	private SampleNode sampleNodeWithSameIdAs1;
	private SampleNode sampleNode2WithId;

	private SampleNode sampleNode1NoId;
	private SampleNode sampleNode2NoId;

	@Before
	public void before() {
		sampleNode1NoId = SampleNodeFactory.newSampleNode( "node1" );
		sampleNode2NoId = SampleNodeFactory.newSampleNode( "node2" );

		sampleNode1WithId = SampleNodeFactory.newSampleNode( "node1" );
		sampleNode1WithId.setId( 1L );
		sampleNodeWithSameIdAs1 = SampleNodeFactory.newSampleNode( "same id" );
		sampleNodeWithSameIdAs1.setId( 1L );

		sampleNode2WithId = SampleNodeFactory.newSampleNode( "node2" );
		sampleNode2WithId.setId( 2L );
	}

	@Test
	public void testEquals() throws Exception {
		// quote reference:
		// 1. Before you attach an entity to the database, i.e. before the entity has had its id-field populated,
		//    we suggest you rely on object identity for comparisons.
		// 2. Once an entity is attached, we suggest you rely solely on the id-field for equality.

		// equal to self
		assertThat( sampleNode1NoId, equalTo( sampleNode1NoId ) );
		assertThat( sampleNode2NoId, equalTo( sampleNode2NoId ) );
		assertThat( sampleNode1WithId, equalTo( sampleNode1WithId ) );
		assertThat( sampleNode2WithId, equalTo( sampleNode2WithId ) );

		// equal to other object with same id but different name property
		assertThat( sampleNode1WithId, equalTo( sampleNodeWithSameIdAs1 ) );
		// consistency with hashCode()
		assertThat( sampleNode1WithId.hashCode(), equalTo( sampleNodeWithSameIdAs1.hashCode() ) );

		// not equal to other object with other id
		assertThat( sampleNode1NoId, not( equalTo( sampleNode1WithId ) ) );
		assertThat( sampleNode2NoId, not( equalTo( sampleNode2WithId ) ) );
		assertThat( sampleNode1WithId, not( equalTo( sampleNode2WithId ) ) );

		// completely different objects not equal
		assertThat( sampleNode1NoId, not( equalTo( sampleNode2WithId ) ) );
		assertThat( sampleNode2NoId, not( equalTo( sampleNode1WithId ) ) );
		assertThat( sampleNode1NoId, not( equalTo( sampleNode2NoId ) ) );
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
