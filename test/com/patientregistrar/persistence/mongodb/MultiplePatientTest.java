package com.patientregistrar.persistence.mongodb;

import static com.patientregistrar.domain.DomainUtility.makeThreePatients;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patientregistrar.domain.Patient;

/**
 * <p>
 * The test class <code>PatientRepositoryTest</code> shows how to use the Spring Data MongoDB API for a collections of domain objects.
 * </p><p>
 * Specifically, it shows how to use 
 * <a href="http://static.springsource.org/spring-data/data-commons/docs/current/api/org/springframework/data/repository/package-summary.html">Repository Interfaces</a> 
 * Spring has made available (instead of custom DAO implementations).
 * </p>
 * @author Jeff Drost
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:WebContent/WEB-INF/patient-registrar-servlet.xml"})
public class MultiplePatientTest {

	private static final Logger LOGGER = Logger.getLogger(MultiplePatientTest.class);
	
	@Autowired
	private PatientRepository repository;
		
	/**
	 * Tests finding, persistence, and deletion of multiple patients.
	 * Find by last name uses Spring's "query methods" in the repository interface.
	 */
	@Test
	public void testMultiplePatients() {
		List<Patient> patients = makeThreePatients();
		repository.save(patients);
		LOGGER.debug("Persisted three patients: " + patients);
		
		patients = repository.findByLastName("PatientRegistrar");
		assertNotNull(patients);
		assertFalse(patients.isEmpty());
		assertEquals(3,patients.size());
		LOGGER.debug("Found three patients: " + patients);
		
		repository.delete(patients);
		patients = repository.findByLastName("PatientRegistrar");
		LOGGER.debug("Deleted 3 patients");
	}	
	
	
}
