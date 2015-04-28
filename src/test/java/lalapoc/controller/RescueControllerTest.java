package lalapoc.controller;

import lalapoc.business.RescueService;
import lalapoc.entity.Name;
import lalapoc.entity.factory.NameFactory;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class RescueControllerTest {

	private MockMvc mvc;

	@Mock
	private RescueService rescueServiceMock;

	@InjectMocks
	private RescueController testling;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup( testling ).build();
	}

	@Test
	public void testReadNames() throws Exception {
		mvc.perform( MockMvcRequestBuilders.get( "/names" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
		verify( rescueServiceMock, times( 1 ) ).findNames();
	}

	@Test
	public void testReadNeeds() throws Exception {
		mvc.perform( MockMvcRequestBuilders.get( "/needs" ).accept( MediaType.APPLICATION_JSON ) ).andExpect( status().isOk() );
		verify( rescueServiceMock, times( 1 ) ).findNeeds();
	}

	@Test
	public void testCreateName() throws Exception {
		// TODO: the default mapping by com.fasterxml.jackson.databind.ObjectMapper of org.springframework.data.geo.Point yields a 400 bad request
		// (but persisting the same JSON with (Point position) in the integration test works though i.e. is correctly converted by neo4j)

		// test without coordinates for (Point position)
		String jsonContent = NameFactory.newNameJson( "John Doe", 1, null );

		mvc.perform( MockMvcRequestBuilders
				.post( "/names" )
				.contentType( MediaType.APPLICATION_JSON )
				.content( jsonContent )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() );

		verify( rescueServiceMock, times( 1 ) ).createName( any( Name.class ) );
	}

}
