package lalapoc;

import lalapoc.business.SampleServiceMethods;
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
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class SampleServiceTest {

 @Mock
 private SampleNodeRepository sampleNodeRepositoryMock;

 @InjectMocks
 private SampleServiceMethods testling;

 @Before
 public void setUp() throws Exception {
	SampleNode n1 = new SampleNode();
	SampleNode n2 = new SampleNode();

	n1.setName("N1");
	n2.setName("N2");

	n1.setId(1L);
	n2.setId(2L);

	when(sampleNodeRepositoryMock.findAll()).thenReturn(QueryResultBuilder.from(n1, n2));
	when(sampleNodeRepositoryMock.findByCustomQuery()).thenReturn(QueryResultBuilder.from(n1, n2));
 }

 @Test
 public void testReadSampleNodes() throws Exception {
 }

 @Test
 public void testReadNodesByCustomQuery() throws Exception {
 }

 @Test
 public void testReadNodesByNumber() throws Exception {
 }

 @Test
 public void testCreateSampleNode() throws Exception {
 }

}
