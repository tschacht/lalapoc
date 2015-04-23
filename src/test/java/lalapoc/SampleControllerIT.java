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
	public void testReadSampleNodes() throws Exception {
		ResponseEntity<String> response = template.getForEntity(base.toString() + "samples", String.class);
		assertThat(response.getBody().matches("(.*pipapo.*){2,}"), is(true));
	}

	@Test
	public void testCreateSampleNode() throws Exception {
		ResponseEntity<String> response = template.postForEntity(base.toString() + "samples", null, String.class);
		assertThat(response.getBody().matches("(.*pipapo.*){1}"), is(true));
	}

}