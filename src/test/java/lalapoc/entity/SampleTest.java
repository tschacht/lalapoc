package lalapoc.entity;

import lalapoc.entity.factory.SampleFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

// cf. reference: http://docs.spring.io/spring-data/data-neo4j/docs/3.3.0.RELEASE/reference/html/#graphid_neo4j_id_field
public class SampleTest {

	private Sample sample_1_Id;
	private Sample sample_1b_Id;
	private Sample sample_2_Id;

	private Sample sample_1_unattached;
	private Sample sample_2_unattached;

	@Before
	public void before() {
		sample_1_unattached = SampleFactory.newSample( "sample1" );
		sample_2_unattached = SampleFactory.newSample( "sample2" );

		sample_1_Id = SampleFactory.newSample( "sample1" );
		sample_1_Id.setId( 1L );
		sample_1b_Id = SampleFactory.newSample( "sample1" );
		sample_1b_Id.setId( 1L );

		sample_2_Id = SampleFactory.newSample( "sample2" );
		sample_2_Id.setId( 2L );
	}

	@Test
	public void testEquals() throws Exception {
		// quote reference:
		// 1. Before you attach an entity to the database, i.e. before the entity has had its id-field populated,
		//    we suggest you rely on object identity for comparisons.
		// 2. Once an entity is attached, we suggest you rely solely on the id-field for equality.

		// equal to self
		assertThat( sample_1_unattached, equalTo( sample_1_unattached ) );
		assertThat( sample_2_unattached, equalTo( sample_2_unattached ) );
		assertThat( sample_1_Id, equalTo( sample_1_Id ) );
		assertThat( sample_2_Id, equalTo( sample_2_Id ) );

		// equal to other object with same id and same properties
		assertThat( sample_1_Id, equalTo( sample_1b_Id ) );
		// consistency with hashCode()
		assertThat( sample_1_Id.hashCode(), equalTo( sample_1b_Id.hashCode() ) );

		// not equal to other object with other id
		assertThat( sample_1_Id, not( equalTo( sample_2_Id ) ) );
		// also not equal to sample with same properties but without id
		assertThat( sample_1_unattached, not( equalTo( sample_1_Id ) ) );
		assertThat( sample_2_unattached, not( equalTo( sample_2_Id ) ) );

		// completely different objects not equal
		assertThat( sample_1_unattached, not( equalTo( sample_2_Id ) ) );
		assertThat( sample_2_unattached, not( equalTo( sample_1_Id ) ) );
		assertThat( sample_1_unattached, not( equalTo( sample_2_unattached ) ) );
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

		assertThat( sample_1_Id, equalTo( sample_1b_Id ) );
		assertThat( sample_1_Id.hashCode(), not( equalTo( sample_1b_Id.hashCode() ) ) );
	}

	// quote reference:
	// A work-around for the problem of un-attached entities having their hashcode change when they get saved is to cache the hashcode.
	// The hashcode will change next time you load the entity, but at least if you have the entity sitting in a collection,
	// you will still be able to find it

	@Test
	public void testHashCodeSameAfterAttach() throws Exception {
		Sample s = SampleFactory.newSample( "s" );

		int hashBefore = s.hashCode();
		// simulate persist/attach to graph in db
		s.setId( 123L );
		int hashAfter = s.hashCode();

		assertThat( hashBefore, equalTo( hashAfter ) );
	}

	@Test
	public void testHashCodeDifferentAfterReload() throws Exception {
		Sample s = SampleFactory.newSample( "s" );

		int hashBefore = s.hashCode();
		// simulate reload from db
		s.setHash( null );
		s.setId( 123L );
		int hashAfter = s.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

	@Ignore // TODO: must the hash of the same object change when a property other than id changes?
	@Test
	public void testHashCodeDifferentAfterPropertyChange() throws Exception {
		Sample s = SampleFactory.newSample( "s" );

		int hashBefore = s.hashCode();
		s.setName( "s_" );
		int hashAfter = s.hashCode();

		assertThat( hashBefore, not( equalTo( hashAfter ) ) );
	}

}
