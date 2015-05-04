package lalapoc.controller;

import lalapoc.business.SampleServiceMethods;
import lalapoc.entity.Sample;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@RestController
public class SampleController {

	@Inject
	private SampleServiceMethods sampleService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView( "home-view" );
	}

	@RequestMapping(value = "/samples", method = RequestMethod.GET)
	public Iterable<Sample> readSamples() {
		return sampleService.readSamples();
	}

	@RequestMapping(value = "/custom", method = RequestMethod.GET)
	public Iterable<Sample> readSamplesyCustomQuery() {
		return sampleService.readSamplesByCustomQuery();
	}

	@RequestMapping(value = "/custom/{number}", method = RequestMethod.GET)
	public Iterable<Sample> readSamplesByNumber( @PathVariable long number ) {
		return sampleService.readSamplesByNumber( number );
	}

	@RequestMapping(value = "/samples", method = RequestMethod.POST)
	public Sample createSample() {
		return sampleService.createSample();
	}

	@RequestMapping(value = "/typed/{number}", method = RequestMethod.GET)
	public Iterable<Sample> readSamplesTyped( @PathVariable Long number ) {
		return sampleService.readTyped( number );
	}

}
