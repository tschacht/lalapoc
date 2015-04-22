package lalapoc.controller;

import lalapoc.entity.SampleNode;
import lalapoc.repository.SampleNodeRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//@RestResource
@RestController
@EnableAutoConfiguration
public class SampleController {

	@Inject
	private SampleNodeRepository sampleNodeRepository;

	@Transactional
	@RequestMapping(value = "/samples", method = RequestMethod.GET)
	public Iterable<SampleNode> readSampleNodes() {
		List<SampleNode> result = new ArrayList<>();
		for( SampleNode n : sampleNodeRepository.findAll() ) {
			result.add(n);
		}
		return result;
	}

	@Transactional
	@RequestMapping(value = "/samples", method = RequestMethod.PUT)
	public SampleNode createSampleNode() {
		SampleNode n = new SampleNode();
		n.setName("pipapo - " + Calendar.getInstance().getTime());
		sampleNodeRepository.save(n);
		return n;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("home");
	}

}
