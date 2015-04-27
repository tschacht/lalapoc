package lalapoc.controller;

import lalapoc.business.RescueServiceMethods;
import lalapoc.entity.Name;
import lalapoc.entity.Need;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@EnableAutoConfiguration
public class RescueController {

	@Inject
	private RescueServiceMethods rescueService;

	@RequestMapping(value = "/names", method = RequestMethod.GET)
	public Iterable<Name> readNames() {
		return rescueService.findNames();
	}

	@RequestMapping(value = "/needs", method = RequestMethod.GET)
	public Iterable<Need> readNeeds() {
		return rescueService.findNeeds();
	}

	@RequestMapping(value = "/names", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Name createName( @RequestBody Name name ) {
		return rescueService.createName( name );
	}

}
