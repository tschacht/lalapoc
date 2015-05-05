package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.Asking;
import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.factory.AskingFactory;
import lalapoc.entity.factory.NameFactory;
import lalapoc.entity.factory.NeedFactory;
import lalapoc.entity.index.LuceneTimelineIndexFactory;
import lalapoc.repository.NameRepository;
import lalapoc.repository.NeedRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;
import org.neo4j.index.lucene.TimelineIndex;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.time.ZonedDateTime;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AllocationServiceTest {

	@Mock
	private Neo4jTemplate templateMock;

	@Mock
	private NameRepository nameRepositoryMock;

	@Mock
	private NeedRepository needRepositoryMock;

	@Mock
	private Name nameMock;

	@Mock
	private Need needMock;

	@Mock
	private LuceneTimelineIndexFactory luceneTimelineIndexFactoryMock;

	@Mock
	private TimelineIndex<Node> nodeTimelineIndexMock;

	@Mock
	private Node nodeMock;

	private ZonedDateTime time;
	private Need need1;
	private Need need2;
	private Need need3;

	private Name name1;
	private Name name1Attached;
	private Name name2;
	private Name name2Attached;

	@InjectMocks
	private AllocationService testling;

	@Before
	public void setUp() throws Exception {
		when( luceneTimelineIndexFactoryMock.getNodeInstance() ).thenReturn( nodeTimelineIndexMock );
		when( templateMock.getNode( anyLong() ) ).thenReturn( nodeMock );

		time = ZonedDateTime.now();

		need1 = NeedFactory.newNeed( "Water 1l" );
		need2 = NeedFactory.newNeed( "Blanket" );
		need3 = NeedFactory.newNeed( "Antibiotics" );

		name1 = NameFactory.newName( "Refugee A", 5, 0, 0, time );
		name1Attached = NameFactory.newName( "Refugee A", 5, 0, 0, time );
		name1Attached.setId( 123L );
		name2 = NameFactory.newName( "Refugee B", 10, 0, 0, time );
		name2Attached = NameFactory.newName( "Refugee B", 10, 0, 0, time );
		name2Attached.setId( 321L );

		when( nameRepositoryMock.save( name1 ) ).thenReturn( name1Attached );
		when( nameRepositoryMock.save( name2 ) ).thenReturn( name2Attached );
		when( nameRepositoryMock.findAll() ).thenReturn( QueryResultBuilder.from( name1, name2 ) );
		when( nameRepositoryMock.findByName( ".*refugee a.*" ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( name1 ) ) );
		when( nameRepositoryMock.findByName( ".*refugee b.*" ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( name2 ) ) );

		when( needRepositoryMock.save( need1 ) ).thenReturn( need1 );
		when( needRepositoryMock.save( need2 ) ).thenReturn( need2 );
		when( needRepositoryMock.save( need3 ) ).thenReturn( need3 );
		when( needRepositoryMock.findAll() ).thenReturn( QueryResultBuilder.from( need1, need2, need3 ) );
		when( needRepositoryMock.findByDescr( ".*water.*" ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( need1 ) ) );
		when( needRepositoryMock.findByDescr( ".*blanket.*" ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( need2 ) ) );
		when( needRepositoryMock.findByDescr( ".*antibiotics.*" ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( need3 ) ) );
	}

	@Test
	public void testCreateName() throws Exception {
		Name result = testling.createName( name1Attached );

		assertThat( result.getName(), equalTo( name1Attached.getName() ) );
		assertThat( result.getPeople(), equalTo( name1Attached.getPeople() ) );
		assertThat( result.getPosition(), equalTo( name1Attached.getPosition() ) );
		assertThat( result.getTime(), equalTo( name1Attached.getTime() ) );
		assertThat( result.getAskings(), equalTo( name1Attached.getAskings() ) );

		verify( nameRepositoryMock, times( 1 ) ).saveOnly( name1Attached );
	}

	@Test
	public void testCreateNeed() throws Exception {
		Need result = testling.createNeed( need1 );

		assertThat( result, is( need1 ) );

		verify( needRepositoryMock, times( 1 ) ).saveOnly( need1 );
	}

	@Test
	public void testFindNames() throws Exception {
		Collection<Name> result = testling.findNames();

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );

		verify( nameRepositoryMock, times( 1 ) ).findAll();
	}

	@Test
	public void testFindNamesByName() throws Exception {
		Collection<Name> result = testling.findNamesByName( "Refugee A " );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 1 ) );
		assertThat( result.iterator().next(), is( name1 ) );

		verify( nameRepositoryMock, times( 1 ) ).findByName( ".*refugee a.*" );
	}

	@Test
	public void testFindNeeds() throws Exception {
		Collection<Need> result = testling.findNeeds();

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 3 ) );
		assertThat( result.iterator().next(), is( need1 ) );

		verify( needRepositoryMock, times( 1 ) ).findAll();
	}

	@Test
	public void testFindNeedsByDescr() throws Exception {
		Collection<Need> result = testling.findNeedsByDescr( " Water " );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 1 ) );
		assertThat( result.iterator().next(), is( need1 ) );

		verify( needRepositoryMock, times( 1 ) ).findByDescr( ".*water.*" );
	}

	@Test
	public void testCreateAsking() throws Exception {
		Asking createdRelationship = AskingFactory.newAsking( name2, 0, need2 );
		when( templateMock.createRelationshipBetween( name2, need2, Asking.class, "ASKS_FOR", false ) ).thenReturn( createdRelationship );
		when( templateMock.save( any( Asking.class ) ) ).thenReturn( createdRelationship );

		Asking result = testling.createAsking( name2, 15, need2 );

		assertThat( result, notNullValue() );
		assertThat( result.getName(), is( name2 ) );
		assertThat( result.getQuantity(), is( 15 ) );
		assertThat( result.getNeed(), is( need2 ) );

		verify( templateMock, times( 1 ) ).createRelationshipBetween( name2, need2, Asking.class, "ASKS_FOR", false );
		verify( templateMock, times( 1 ) ).saveOnly( createdRelationship );
	}

	@Test
	public void testFindNear() throws Exception {
		when( nameRepositoryMock.findWithinDistance( anyString(), anyDouble(), anyDouble(), anyDouble() ) )
				.thenReturn( QueryResultBuilder.from( name1, name2 ) );

		double lat = 53;
		double lon = 8;

		Collection<Name> result = testling.findNear( lat, lon, 10 );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );

		verify( nameRepositoryMock, times( 1 ) ).findWithinDistance( Name.INDEX_POSITION, lat, lon, 10 );
	}

}
