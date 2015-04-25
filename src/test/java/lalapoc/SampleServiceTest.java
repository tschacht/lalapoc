package lalapoc;

import com.google.common.collect.Lists;
import lalapoc.business.SampleService;
import lalapoc.entity.SampleNode;
import lalapoc.repository.SampleNodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.neo4j.conversion.QueryResultBuilder;

import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleServiceTest {

 @Mock
 private SampleNodeRepository sampleNodeRepositoryMock;

 @InjectMocks
 private SampleService testling;

 @Before
 public void setUp() throws Exception {
	SampleNode n1 = new SampleNode();
	SampleNode n2 = new SampleNode();

	n1.setName("N1");
	n2.setName("N2");

	n1.setId(1L);
	n2.setId(2L);

	when(sampleNodeRepositoryMock.findAll()).thenReturn(QueryResultBuilder.from(n1, n2));
	when(sampleNodeRepositoryMock.findByCustomQuery()).thenReturn(Lists.newArrayList(QueryResultBuilder.from(n1, n2)));
 }

 @Test
 public void testReadSampleNodes() throws Exception {
	Collection<SampleNode> result = testling.readSampleNodes();

	assertThat(result, notNullValue());
	assertThat(result.size(), is(2));
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
