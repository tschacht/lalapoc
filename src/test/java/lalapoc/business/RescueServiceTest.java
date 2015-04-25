package lalapoc.business;

import lalapoc.repository.NameRepository;
import lalapoc.repository.NeedRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith( MockitoJUnitRunner.class )
public class RescueServiceTest {

	@Mock
	private NameRepository nameRepositoryMock;

	@Mock
	private NeedRepository needRepositoryMock;

	@InjectMocks
	private RescueService testling;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateName() throws Exception {
	}

	@Test
	public void testCreateNeed() throws Exception {
	}

	@Test
	public void testFindNames() throws Exception {
	}

	@Test
	public void testFindNamesByName() throws Exception {
	}

	@Test
	public void testFindNeeds() throws Exception {
	}

	@Test
	public void testFindNeedsByDescr() throws Exception {
	}

}
