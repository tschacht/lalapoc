package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.SampleNode;
import lalapoc.entity.factory.SampleNodeFactory;
import lalapoc.repository.SampleNodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.cypherdsl.grammar.Execute;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SampleServiceTest {

	@Mock
	private SampleNodeRepository sampleNodeRepositoryMock;

	@Mock
	private SampleNode sampleNodeMock;

	private Map<String, Object> params;

	@InjectMocks
	private SampleService testling;

	@Before
	public void setUp() throws Exception {
		SampleNode n1 = SampleNodeFactory.newSampleNode( "N1" );
		SampleNode n2 = SampleNodeFactory.newSampleNode( "N2" );

		when( sampleNodeRepositoryMock.findAll() ).thenReturn( QueryResultBuilder.from( n1, n2 ) );
		when( sampleNodeRepositoryMock.findByCustomQuery() ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( n1, n2 ) ) );
		when( sampleNodeRepositoryMock.findByCustomPatternQuery( anyString() ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( n1, n2 ) ) );
		when( sampleNodeRepositoryMock.save( any( SampleNode.class ) ) ).thenReturn( sampleNodeMock );

		params = new HashMap<>();
		params.put( "id", 0L );

		when( sampleNodeRepositoryMock.query( any( Execute.class ), eq( params ) ) ).thenReturn( QueryResultBuilder.from( sampleNodeMock ) );
	}

	@Test
	public void testReadSampleNodes() throws Exception {
		Collection<SampleNode> result = testling.readSampleNodes();

		verify( sampleNodeRepositoryMock, times( 1 ) ).findAll();

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );
	}

	@Test
	public void testReadNodesByCustomQuery() throws Exception {
		Collection<SampleNode> result = testling.readNodesByCustomQuery();

		verify( sampleNodeRepositoryMock, times( 1 ) ).findByCustomQuery();

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );
	}

	@Test
	public void testReadNodesByNumber() throws Exception {
		Collection<SampleNode> result = testling.readNodesByNumber( 2L );

		verify( sampleNodeRepositoryMock, times( 1 ) ).findByCustomPatternQuery( "pipapo_2.*" );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );

	}

	@Test
	public void testCreateSampleNode() throws Exception {
		SampleNode n = testling.createSampleNode();

		verify( sampleNodeRepositoryMock, times( 1 ) ).save( any( SampleNode.class ) );

		assertThat( n, is( sampleNodeMock ) );
	}

	@Test
	public void testReadTyped() throws Exception {
		Collection<SampleNode> result = testling.readTyped( 0L );

		verify( sampleNodeRepositoryMock, times( 1 ) ).query( any( Execute.class ), eq( params ) );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 1 ) );
		assertThat( result.iterator().next(), is( sampleNodeMock ) );
	}

}
