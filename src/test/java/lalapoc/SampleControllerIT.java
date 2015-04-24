package lalapoc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.Instant;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LalapocApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class SampleControllerIT {

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void getHome() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getBody(), containsString("hello form templates/home.ftl"));
	}

	@Test
	public void testReadCustomNodesByQuery() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString() + "custom", String.class);
		assertThat(response.getBody().matches("(.*pipapo.*){2,}"), is(true));
	}

	@Test
	public void testReadCustomNodesByPatternQuery() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString() + "custom/5", String.class);
		assertThat(response.getBody().matches("(.*pipapo_5.*){2,}"), is(true));
		assertThat(response.getBody().matches("(.*pipapo_2.*)"), is(false));
		assertThat(response.getBody().matches("(.*pipapo\\s.*)"), is(false));
	}

	@Test
	public void testReadSampleNodes() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString() + "samples", String.class);
		assertThat(response.getBody().matches("(.*pipapo.*){2,}"), is(true));
	}

	@Test
	public void testCreateSampleNode() throws Exception {
		int n = 100;
		System.out.println("#####");
		System.out.println("creating n nodes. n=" + n);
		Instant begin = Instant.now();
		System.out.println("BEGIN: " + begin);
		for( int i = 0; i < n; i++ ) {
			template.postForEntity(base.toString() + "samples", null, String.class);
			if( i % 5 == 0 ) System.out.print(i + ", ");
		}
		Instant end = Instant.now();
		System.out.println("\nEND " + end);
		System.out.println("took millis: " + (end.toEpochMilli() - begin.toEpochMilli()));
		System.out.println("#####");

		ResponseEntity<String> response = template.postForEntity(base.toString() + "samples", null, String.class);
		assertThat(response.getBody().matches("(.*pipapo.*){1}"), is(true));
	}

}
