package lalapoc.business;

import com.google.common.collect.Lists;
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
	public Name createName( Name name ) {
		return nameRepository.save( name );
	}

	@Override
	@Transactional
	@Fetch
	public Need createNeed( Need need ) {
		return needRepository.save( need );
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Name> findNames() {
		return Lists.newArrayList( nameRepository.findAll() );
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Name> findNamesByName( String name ) {
		String pattern = ".*" + name.trim().toLowerCase() + ".*";
		return nameRepository.findByName( pattern );
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Need> findNeeds() {
		return Lists.newArrayList( needRepository.findAll() );
	}

	@Override
	@Transactional
	@Fetch
	public Collection<Need> findNeedsByDescr( String descr ) {
		String pattern = ".*" + descr.trim().toLowerCase() + ".*";
		return needRepository.findByDescr( pattern );
	}

}
