package lalapoc.controller;

import lalapoc.business.SampleServiceMethods;
import lalapoc.entity.SampleNode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

//@RestResource
@RestController
@EnableAutoConfiguration
public class SampleController {

	@Inject
	private SampleServiceMethods sampleService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView( "home-view" );
	}

	@RequestMapping(value = "/samples", method = RequestMethod.GET)
	public Iterable<SampleNode> readSampleNodes() {
		return sampleService.readSampleNodes();
	}

	@RequestMapping(value = "/custom", method = RequestMethod.GET)
	public Iterable<SampleNode> readNodesByCustomQuery() {
		return sampleService.readNodesByCustomQuery();
	}

	@RequestMapping(value = "/custom/{number}", method = RequestMethod.GET)
	public Iterable<SampleNode> readNodesByNumber( @PathVariable long number ) {
		return sampleService.readNodesByNumber( number );
	}

	@RequestMapping(value = "/samples", method = RequestMethod.POST)
	public SampleNode createSampleNode() {
		return sampleService.createSampleNode();
	}

	@RequestMapping(value = "/typed/{id}", method = RequestMethod.GET)
	public Iterable<SampleNode> readNodesTyped( @PathVariable Long id ) {
		return sampleService.readTyped( id );
	}

}
