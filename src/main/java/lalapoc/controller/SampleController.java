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
import java.util.Random;

//@RestResource
@RestController
@EnableAutoConfiguration
public class SampleController {

	@Inject
	private SampleNodeRepository sampleNodeRepository;

	@Transactional
	@RequestMapping(value = "/custom", method = RequestMethod.GET)
	public Iterable<SampleNode> readCustomQueryNodes() {
		return yield(sampleNodeRepository.findCustomQuery());
	}

	@Transactional
	@RequestMapping(value = "/samples", method = RequestMethod.GET)
	public Iterable<SampleNode> readSampleNodes() {
		return yield(sampleNodeRepository.findAll());
	}

	@Transactional
	@RequestMapping(value = "/samples", method = RequestMethod.POST)
	public SampleNode createSampleNode() {
		SampleNode n = new SampleNode();
		Random r = new Random();
		n.setName("pipapo_" + r.nextInt(10) + " - " + Calendar.getInstance().getTime());
		sampleNodeRepository.save(n);
		return n;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("home");
	}

	private <T> Iterable<T> yield(Iterable<T> result) {
		List<T> list = new ArrayList<>();
		if( result != null ) {
			for( T o : result ) {
				list.add(o);
			}
		}
		return list;
	}

}
