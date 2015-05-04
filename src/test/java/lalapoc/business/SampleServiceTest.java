package lalapoc.business;

import com.google.common.collect.Lists;
import lalapoc.entity.Sample;
import lalapoc.entity.factory.SampleFactory;
import lalapoc.repository.SampleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.cypherdsl.grammar.Execute;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;

import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SampleServiceTest {

	@Mock
	private SampleRepository sampleRepositoryMock;

	@Mock
	private Sample sampleMock;

	@InjectMocks
	private SampleService testling;

	@Before
	public void setUp() throws Exception {
		Sample n1 = SampleFactory.newSample( "N1" );
		Sample n2 = SampleFactory.newSample( "N2" );

		when( sampleRepositoryMock.findAll() ).thenReturn( QueryResultBuilder.from( n1, n2 ) );
		when( sampleRepositoryMock.findByCustomQuery() ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( n1, n2 ) ) );
		when( sampleRepositoryMock.findByCustomPatternQuery( anyString() ) ).thenReturn( Lists.newArrayList( QueryResultBuilder.from( n1, n2 ) ) );
		when( sampleRepositoryMock.save( any( Sample.class ) ) ).thenReturn( sampleMock );

		when( sampleRepositoryMock.query( any( Execute.class ), any() ) ).thenReturn( QueryResultBuilder.from( sampleMock ) );
	}

	@Test
	public void testReadSamples() throws Exception {
		Collection<Sample> result = testling.readSamples();

		verify( sampleRepositoryMock, times( 1 ) ).findAll();

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );
	}

	@Test
	public void testReadSamplesByCustomQuery() throws Exception {
		Collection<Sample> result = testling.readSamplesByCustomQuery();

		verify( sampleRepositoryMock, times( 1 ) ).findByCustomQuery();

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );
	}

	@Test
	public void testReadSamplesByNumber() throws Exception {
		Collection<Sample> result = testling.readSamplesByNumber( 2L );

		verify( sampleRepositoryMock, times( 1 ) ).findByCustomPatternQuery( "pipapo_2.*" );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 2 ) );

	}

	@Test
	public void testCreateSample() throws Exception {
		Sample s = testling.createSample();

		verify( sampleRepositoryMock, times( 1 ) ).save( any( Sample.class ) );

		assertThat( s, is( sampleMock ) );
	}

	@Test
	public void testReadTyped() throws Exception {
		Collection<Sample> result = testling.readTyped( 0L );

		verify( sampleRepositoryMock, times( 1 ) ).query( any( Execute.class ), any() );

		assertThat( result, notNullValue() );
		assertThat( result.size(), is( 1 ) );
		assertThat( result.iterator().next(), is( sampleMock ) );
	}

}
