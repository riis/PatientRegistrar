package com.patientregistrar.control;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.patientregistrar.domain.Patient;
import com.patientregistrar.persistence.mongodb.PatientRepositoryMongo;

/**
 * <p>
 * The class <code>NewPatientEntryController</code> handles requests
 * for new patient registration. 
 * </p>
 * @author Jeff Drost
 */
@Controller
public class NewPatientEntryController {

	private static final Logger LOGGER = Logger.getLogger(NewPatientEntryController.class);
	
	@Resource
	private PatientRepositoryMongo patientRepositoryJdbc;

	@Resource
	private PatientRepositoryMongo patientRepositoryMongo;			
	
	@RequestMapping(value = "/register.htm",method = RequestMethod.GET)
	public ModelAndView displayLandingPage() {
		LOGGER.info("received a request for new patient registration");
		return new ModelAndView("register",new ModelMap("newpatient", new Patient()));
	}

	@RequestMapping(value = "/register.htm",method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("newpatient") Patient patient, BindingResult binding, ModelMap model) {
		LOGGER.info("received a request to register a new patient");
		LOGGER.debug(patient);
		
		model.addAttribute("newpatient", patient);
		
		// if errors, back you go to correct them!
		if (binding.hasErrors()) {
			LOGGER.warn("validation errors exist for this patient!");
			LOGGER.warn(binding.getAllErrors());
			return new ModelAndView("register",model);
		}
		
		PatientRepositoryMongo repository = patientRepositoryMongo; // TODO: this
		repository.save(patient);
		LOGGER.info("patient successfully sasved");
		return new ModelAndView("complete",model);
	}	
	
}
