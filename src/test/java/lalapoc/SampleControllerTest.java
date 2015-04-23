package lalapoc;

import lalapoc.controller.SampleController;
import lalapoc.entity.SampleNode;
import lalapoc.repository.SampleNodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class SampleControllerTest {

	private MockMvc mvc;

	@Mock
	private SampleNodeRepository sampleNodeRepositoryMock;

	@InjectMocks
	private SampleController testling;

	@Before
	public void setUp() throws Exception {
		SampleNode n1 = new SampleNode();
		SampleNode n2 = new SampleNode();

		n1.setName("N1");
		n2.setName("N2");

		n1.setId(1L);
		n2.setId(2L);

		when(sampleNodeRepositoryMock.findAll()).thenReturn(QueryResultBuilder.from(n1, n2));
		when(sampleNodeRepositoryMock.findCustomQuery()).thenReturn(QueryResultBuilder.from(n1, n2));

		mvc = MockMvcBuilders.standaloneSetup(testling).build();
	}

	@Test
	public void testHome() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk());
	}

	@Test
	public void testReadCustomQueryNodes() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/custom").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testReadSampleNodes() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/samples").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testCreateSampleNode() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/samples").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
