package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.entity.Asking;
import lalapoc.entity.factory.NameFactory;
import lalapoc.entity.factory.NeedFactory;
import lalapoc.entity.factory.AskingFactory;
import lalapoc.repository.NameRepository;
import lalapoc.repository.NeedRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.data.neo4j.support.Neo4jTemplate;

import java.time.LocalTime;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
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

	private LocalTime time;
	private Need need1;
	private Need need2;
	private Need need3;
	private Name name1;
	private Name name2;

	@InjectMocks
	private AllocationService testling;

	@Before
	public void setUp() throws Exception {
		time = LocalTime.now();

		need1 = NeedFactory.newNeed( "Water 1l" );
		need2 = NeedFactory.newNeed( "Blanket" );
		need3 = NeedFactory.newNeed( "Antibiotics" );

		name1 = NameFactory.newName( "Refugee A", 5, 0, 0, time );
		name2 = NameFactory.newName( "Refugee B", 10, 0, 0, time );

		// TODO: relations

		when( nameRepositoryMock.save( name1 ) ).thenReturn( name1 );
		when( nameRepositoryMock.save( name2 ) ).thenReturn( name2 );
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
		Name result = testling.createName( name1 );

		assertThat( result, is( name1 ) );

		verify( nameRepositoryMock, times( 1 ) ).save( name1 );
	}

	@Test
	public void testCreateNeed() throws Exception {
		Need result = testling.createNeed( need1 );

		assertThat( result, is( need1 ) );

		verify( needRepositoryMock, times( 1 ) ).save( need1 );
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
