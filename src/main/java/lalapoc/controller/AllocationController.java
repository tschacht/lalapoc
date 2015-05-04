package lalapoc.controller;

import lalapoc.business.AllocationServiceMethods;
import lalapoc.entity.Name;
import lalapoc.entity.Need;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class AllocationController {

	@Inject
	private AllocationServiceMethods allocationService;

	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public Iterable<Name> readNames() {
		return allocationService.findNames();
	}

	@RequestMapping(value = "/needs", method = RequestMethod.GET)
	public Iterable<Need> readNeeds() {
		return allocationService.findNeeds();
	}

	@RequestMapping(value = "/names", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Name createName( @RequestBody Name name ) {
		return allocationService.createName( name );
	}

	@RequestMapping(value = "/needs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Need createNeed( @RequestBody Need need ) {
		return allocationService.createNeed( need );
	}

}
