package com.patientregistrar.persistence.mongodb;

import static com.patientregistrar.domain.DomainUtility.makeThreePatients;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patientregistrar.domain.Patient;

/**
 * <p>
 * The integration test class <code>PatientRepositoryTest</code> tests and displays how to use Spring Data's 
 * repositories. Also tests the concrete JDBC implementation.
 * </p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/patient-registrar-servlet.xml"})
public class MultiplePatientTest {

	private static final Logger LOGGER = Logger.getLogger(MultiplePatientTest.class);
	
	@Resource
	private PatientRepository patientRepositoryJdbc;

	@Autowired
	@Qualifier("patientRepository")
	private PatientRepository patientRepositoryMongoDB;		
		
	/**
	 * Tests finding, persistence, and deletion of multiple patients.
	 * Find by last name uses Spring's "query methods" in the repository interface.
	 */
	@Test
	public void testMultiplePatientsMongoDB() {		
		test(patientRepositoryMongoDB);
	}	
	
	/**
	 * Tests finding, persistence, and deletion of multiple patients.
	 * Find by last name uses Spring's "query methods" in the repository interface.
	 */
	@Test
	public void testMultiplePatientsJDBC() {		
		test(patientRepositoryJdbc);
	}		

	private void test(PatientRepository repository) {
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
