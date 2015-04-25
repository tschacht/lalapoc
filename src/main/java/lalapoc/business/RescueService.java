package lalapoc.business;

import lalapoc.entity.Name;
import lalapoc.entity.Need;
import lalapoc.repository.NameRepository;
import lalapoc.repository.NeedRepository;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

@Named
public class RescueService implements RescueServiceMethods {

	@Inject
	private NameRepository nameRepository;

	@Inject
	private NeedRepository needRepository;

	@Override
	@Transactional
	@Fetch
	public Name createName() {
		return null;
	}

	@Override
	@Transactional
	@Fetch
	public Need createNeed() {
		return null;
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Name> findNames() {
		return null;
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Name> findNamesByName() {
		return null;
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Need> findNeeds() {
		return null;
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Need> findNeedsByDescr() {
		return null;
	}

}
