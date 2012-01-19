package com.patientregistrar.persistence.mongodb;

import static com.patientregistrar.domain.DomainUtility.makeFakePatient;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patientregistrar.domain.Patient;

/**
 * <p>
 * The integration test class <code>PatientRepositoryTest</code> shows how to use the Spring Data MongoDB API for a single domain object.
 * </p><p>
 * Specifically, it shows how to use 
 * <a href="http://static.springsource.org/spring-data/data-commons/docs/current/api/org/springframework/data/repository/package-summary.html">Repository Interfaces</a> 
 * Spring has made available (instead of custom DAO implementations).
 * </p>
 * @author Jeff Drost
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:WebContent/WEB-INF/patient-registrar-servlet.xml"})
public class SinglePatientTest {

	private static final Logger LOGGER = Logger.getLogger(SinglePatientTest.class);
	
	@Autowired
	private PatientRepository repository;

	/**
	 * Tests persistence of one patient. Creation, updating, fetching by ID, and deletion.
	 */
	@Test
	public void testOneSimplePatient() {
		
		// create
		Patient singlePatient = makeFakePatient();
		repository.save(singlePatient);
		String id = singlePatient.getId();
		LOGGER.debug("Persisted patient: " + singlePatient);
		
		// lookup
		assertTrue(StringUtils.isNotEmpty(id));
		singlePatient = repository.findOne(id);
		assertNotNull(singlePatient);
		assertEquals(id,singlePatient.getId());
		LOGGER.debug("Looked up patient: " + singlePatient);
		
		// update
		String oldFirstName = singlePatient.getFirstName();
		singlePatient.setFirstName("Updated First Name");
		singlePatient = repository.save(singlePatient);
		id = singlePatient.getId();
		singlePatient = repository.findOne(id);
		assertNotNull(singlePatient);
		assertNotSame(oldFirstName, singlePatient.getFirstName());
		LOGGER.debug("Updated patient: " + singlePatient);
		
		// cleanup: delete
		repository.delete(id);
		assertFalse(repository.exists(id));
		LOGGER.debug("Deleted patient with id: " + id);
	}
	
}
