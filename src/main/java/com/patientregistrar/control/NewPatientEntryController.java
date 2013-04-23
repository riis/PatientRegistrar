package com.patientregistrar.control;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.patientregistrar.domain.Patient;
import com.patientregistrar.persistence.mongodb.PatientRepository;

/**
 * <p>
 * The class <code>NewPatientEntryController</code> handles web requests
 * for new patient registration. 
 * </p>
 */
@Controller
public class NewPatientEntryController {

	private static final Logger LOGGER = Logger.getLogger(NewPatientEntryController.class);
	
	@Resource
	private PatientRepository patientRepositoryJdbc;

	@Autowired
	@Qualifier("patientRepository")
	private PatientRepository patientRepositoryMongoDB;			
	
	@RequestMapping(value = "/register.htm",method = RequestMethod.GET)
	public ModelAndView displayLandingPage() {
		LOGGER.info("received a request for new patient registration");
		return new ModelAndView("register",new ModelMap("newpatient", new Patient()));
	}

	@RequestMapping(value = "/register.htm",method = RequestMethod.POST)
	public ModelAndView register(@Valid @ModelAttribute("newpatient") Patient patient, BindingResult binding, ModelMap model, 
			@RequestParam(required=false) boolean jdbc) {
		LOGGER.info("received a request to register a new patient");
		LOGGER.debug(patient);
		
		model.addAttribute("newpatient", patient);
		
		// if errors, back you go to correct them!
		if (binding.hasErrors()) {
			LOGGER.warn("validation errors exist for this patient!");
			LOGGER.warn(binding.getAllErrors());
			return new ModelAndView("register",model);
		}
				
		determineRepository(jdbc).save(patient);
		LOGGER.info("patient successfully saved");
		return new ModelAndView("complete",model);
	}	
	
	private PatientRepository determineRepository(boolean jdbc) {
		if(jdbc) {
			LOGGER.info("Using the JDBC repository implementation");
			return patientRepositoryJdbc;
		}
		LOGGER.info("Using the MongoDB repository implementation");
		return patientRepositoryMongoDB;			
	}
	
}
