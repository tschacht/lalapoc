package lalapoc.controller;

import lalapoc.LalapocApplication;
import lalapoc.entity.factory.NameFactory;
import lalapoc.entity.factory.NeedFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.data.geo.Point;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LalapocApplication.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class AllocationControllerIT {

	private static final RestTemplate template = new TestRestTemplate();
	private static boolean testDataCreated;

	@Value("${local.server.port}")
	private int port;
	private URL base;

	private static Point randPos( Random r ) {
		final double LAT = 52.5;
		final double LON = 13.5;
		double lat = LAT + ( r.nextInt( 1000 ) / 10000. * ( r.nextBoolean() ? 1. : -1. ) );
		double lon = LON + ( r.nextInt( 1000 ) / 10000. * ( r.nextBoolean() ? 1. : -1. ) );
		return new Point( lon, lat );
	}

	private static String randNameJSON( Random r ) {
		Point p = randPos( r );
		// latitude -> y-axis (move vertically), longitude -> x-axis (move horizontally)
		try {
			return NameFactory.newNameJson( "John Doe " + r.nextInt( 100 ), r.nextInt( 20 ), p.getY(), p.getX(), ZonedDateTime.now() );
		} catch( IOException e ) {
			e.printStackTrace();
			fail( e.getMessage() );
			return null;
		}
	}

	private void createTestNames( int n ) {
		String url = base.toString() + "names";
		Random r = new Random();

		System.out.println( "Creating Names:" );

		for( int i = 0; i < n; i++ ) {
			doPostJson( randNameJSON( r ), url );
			if( i % 5 == 0 ) System.out.print( i + ", " );
		}
	}

	private void createTestNeeds() {
		final String url = base.toString() + "needs";
		final List<String> needDescriptionsSample = Arrays.asList( "Water", "Blankets", "Medical Assistance", "Medicine", "Cloth", "Protection", "Shelter" );

		System.out.println( "Creating Needs:" );

		for( String descr : needDescriptionsSample ) {
			try {
				doPostJson( NeedFactory.newNeedJSON( descr ), url );
			} catch( IOException e ) {
				e.printStackTrace();
				fail( e.getMessage() );
			}
			System.out.print( descr + ", " );
		}
	}

	private void runDecorated( Runnable r ) {
		System.out.println( "#####" );
		System.out.println( "Creating test data:" );
		Instant begin = Instant.now();
		System.out.println( "BEGIN: " + begin );

		r.run();

		Instant end = Instant.now();
		System.out.println( "\nEND " + end );
		System.out.println( "took millis: " + ( end.toEpochMilli() - begin.toEpochMilli() ) );
		System.out.println( "#####" );
	}

	private void createTestData() throws IOException {
		Runnable runCreateNames = () -> createTestNames( 50 );
		Runnable runCreateNeeds = this::createTestNeeds;

		runDecorated( runCreateNames );
		runDecorated( runCreateNeeds );
	}

	@Before
	public void setUp() throws Exception {
		this.base = new URL( "http://localhost:" + port + "/" );

		if( !testDataCreated ) {
			createTestData();
			testDataCreated = true;
		}
	}

	@Test
	public void testReadNames() throws Exception {
		ResponseEntity<String> response = template.getForEntity( base.toString() + "names", String.class );
		assertThat( response.getBody().matches( "(.*John Doe.*){2,}" ), is( true ) );
	}

	@Test
	public void testCreateName() throws Exception {
		Random r = new Random();
		String url = base.toString() + "names";

		ResponseEntity<String> responseEntity = doPostJson( randNameJSON( r ), url );
		assertThat( responseEntity.getBody().matches( "(.*John Doe.*){1}" ), is( true ) );
	}

	@Test
	public void testReadNeed() throws Exception {
		ResponseEntity<String> responseEntity = template.getForEntity( base.toString() + "needs", String.class );
		assertThat( responseEntity.getBody().matches( "(.*description.*){5,}" ), is( true ) );
	}

	@Test
	public void testCreateNeed() throws Exception {
		String url = base.toString() + "needs";

		ResponseEntity<String> responseEntity = doPostJson( NeedFactory.newNeedJSON( "Pants" ), url );
		assertThat( responseEntity.getBody().matches( "(.*Pants.*){1}" ), is( true ) );
	}

	private ResponseEntity<String> doPostJson( String jsonContent, String url ) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
		headers.setContentType( MediaType.APPLICATION_JSON );

		HttpEntity<String> requestEntity = new HttpEntity<>( jsonContent, headers );
		return template.exchange( url, HttpMethod.POST, requestEntity, String.class );
	}

}
