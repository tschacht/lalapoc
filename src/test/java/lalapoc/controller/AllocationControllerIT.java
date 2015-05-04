package lalapoc.controller;

import lalapoc.LalapocApplication;
import lalapoc.entity.factory.NameFactory;
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
import java.util.Collections;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

	private static String randNameJSON( Random r ) throws IOException {
		Point p = randPos( r );
		// latitude -> y-axis (move vertically), longitude -> x-axis (move horizontally)
		return NameFactory.newNameJson( "John Doe " + r.nextInt( 100 ), r.nextInt( 20 ), p.getY(), p.getX(), null );
	}

	private void createTestData() throws IOException {
		final int n = 50;

		Random r = new Random();
		String url = base.toString() + "names";

		System.out.println( "#####" );
		System.out.println( "creating n Name nodes. n=" + n );
		Instant begin = Instant.now();
		System.out.println( "BEGIN: " + begin );
		for( int i = 0; i < n; i++ ) {
			doPostJson( randNameJSON( r ), url );
			if( i % 5 == 0 ) System.out.print( i + ", " );
		}
		Instant end = Instant.now();
		System.out.println( "\nEND " + end );
		System.out.println( "took millis: " + ( end.toEpochMilli() - begin.toEpochMilli() ) );
		System.out.println( "#####" );
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

	private ResponseEntity<String> doPostJson( String jsonContent, String url ) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
		headers.setContentType( MediaType.APPLICATION_JSON );

		HttpEntity<String> requestEntity = new HttpEntity<>( jsonContent, headers );
		return template.exchange( url, HttpMethod.POST, requestEntity, String.class );
	}

}
