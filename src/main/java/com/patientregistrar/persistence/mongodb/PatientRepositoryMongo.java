package com.patientregistrar.persistence.mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.patientregistrar.domain.Patient;

/**
 * <p>
 * The interface <code>PatientRepository</code> serves as a data access object
 * using MongoDB for persistence. 
 * </p><p>
 * The <a href="http://www.springsource.org/spring-data/mongodb">Spring Data MongoDB</a> API allows you to create
 * your own DAOs, or you can use the templating Spring has provided. By extending the <code>MongoRepository</code>
 * interface, and adding the "repositories" element to a Spring beans context file, Spring will create a concrete
 * implementation of this interface that can be then used for persistence needs. 
 * </p>
 * @author Jeff Drost
 */
public interface PatientRepositoryMongo extends MongoRepository<Patient, String> {
	
	/**
	 * <p>
	 * The amazing thing about spring data mongodb is that this method signature dictates 
	 * the repository implementation behind the scenes!
	 * </p>
	 * @param lastname The last name to search by
	 * @return All patients matching the given argument for last name
	 */
	List<Patient> findByLastName(String lastname);

}
