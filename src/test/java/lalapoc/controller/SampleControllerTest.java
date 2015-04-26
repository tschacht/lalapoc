package lalapoc.controller;

import lalapoc.business.SampleServiceMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class SampleControllerTest {

	private MockMvc mvc;

	@Mock
	private SampleServiceMethods sampleServiceMock;

	@InjectMocks
	private SampleController testling;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup( testling ).build();
	}

	@Test
	public void testHome() throws Exception {
		mvc.perform( MockMvcRequestBuilders.get( "/" ).accept( MediaType.TEXT_HTML ) ).andExpect( status().isOk() );
	}

	@Test
	public void testReadCustomNodesByQuery() throws Exception {
		mvc.perform( MockMvcRequestBuilders.get( "/custom" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
		verify( sampleServiceMock, times( 1 ) ).readNodesByCustomQuery();
	}

	@Test
	public void testReadCustomNodesByPatternQuery() throws Exception {
		mvc.perform( MockMvcRequestBuilders.get( "/custom/5" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
		verify( sampleServiceMock, times( 1 ) ).readNodesByNumber( 5L );
	}

	@Test
	public void testReadSampleNodes() throws Exception {
		mvc.perform( MockMvcRequestBuilders.get( "/samples" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
		verify( sampleServiceMock, times( 1 ) ).readSampleNodes();
	}

	@Test
	public void testCreateSampleNode() throws Exception {
		mvc.perform( MockMvcRequestBuilders.post( "/samples" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
		verify( sampleServiceMock, times( 1 ) ).createSampleNode();
	}

}
