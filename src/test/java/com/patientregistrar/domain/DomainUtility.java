package com.patientregistrar.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * <p>
 * The class <code>DomainUtility</code> gives methods that create 
 * fake <code>Patient</code> data for testing purposes.
 * </p>
 */
public class DomainUtility {

	/**
	 * Makes 3 fake patients with random data, first names Jeff, Angela, and Nick.
	 * All three patients have the same last name of "PatientRegistrar".
	 * @return A collection of 3 patients
	 * @see #makeFakePatient()
	 */
	public static List<Patient> makeThreePatients() {
		List<Patient> patients = new ArrayList<Patient>();
		
		Patient singlePatient = makeFakePatient();
		singlePatient.setFirstName("Jeff");
		singlePatient.setLastName("PatientRegistrar");
		patients.add(singlePatient);
		
		singlePatient = makeFakePatient();
		singlePatient.setFirstName("Angela");
		singlePatient.setLastName("PatientRegistrar");
		patients.add(singlePatient);
		
		singlePatient = makeFakePatient();
		singlePatient.setFirstName("Nick");
		singlePatient.setLastName("PatientRegistrar");
		patients.add(singlePatient);
		
		return patients;
	}	
	
	/**
	 * Makes a fake patient using random data.
	 * @return A fabricated patient made of random data
	 * @see #makeFakeEmployer()
	 * @see #makeFakePerson()
	 * @see #makeFakeAddress()
	 */
	public static Patient makeFakePatient() {
		Patient patient = new Patient();
		patient.setAddress(makeFakeAddress());
		patient.setDateOfBirth(new Date());		
		patient.setEmergencyContact1(makeFakeBasicPerson());
		patient.setEmergencyContact2(makeFakeBasicPerson());
		patient.setEmployer(makeFakeEmployer());
		patient.setFirstName(RandomStringUtils.randomAlphanumeric(20));
		patient.setInsuranceSource(makeFakePerson());
		patient.setInsuranceThroughSelf(false);
		patient.setLastName(RandomStringUtils.randomAlphanumeric(20));
		patient.setMiddleInitial(RandomStringUtils.randomAlphanumeric(1).charAt(0));
		patient.setPhoneNumber(RandomStringUtils.randomNumeric(10));
		patient.setSsn(RandomStringUtils.randomNumeric(9));
		return patient;
	}
	
	/**
	 * Makes a fake employer using random data.
	 * @return A fabricated employer made of random data
	 * @see #makeFakeAddress()
	 */
	public static Employer makeFakeEmployer() {
		Employer employer = new Employer();
		employer.setAddress(makeFakeAddress());
		employer.setName(RandomStringUtils.randomAlphanumeric(20));
		employer.setPhoneNumber(RandomStringUtils.randomNumeric(10));
		return employer;
	}

	/**
	 * Makes a fake Person
	 * @return A fabricated person made of random data
	 * @see #makeFakeAddress()
	 * @see #makeFakeEmployer()
	 */
	public static Person makeFakePerson() {
		Person person = new Person();
		person.setAddress(makeFakeAddress());
		person.setDateOfBirth(new Date());
		person.setEmployer(makeFakeEmployer());
		person.setFirstName(RandomStringUtils.randomAlphanumeric(20));
		person.setLastName(RandomStringUtils.randomAlphanumeric(20));
		person.setMiddleInitial(RandomStringUtils.randomAlphanumeric(1).charAt(0));
		person.setPhoneNumber(RandomStringUtils.randomNumeric(10));
		person.setSsn(RandomStringUtils.randomNumeric(9));		
		return person;
	}
	
	/**
	 * Makes a fake BasicPerson
	 * @return A fabricated basic person made of random data
	 */
	public static BasicPerson makeFakeBasicPerson() {
		BasicPerson person = new BasicPerson();
		person.setFirstName(RandomStringUtils.randomAlphanumeric(20));
		person.setLastName(RandomStringUtils.randomAlphanumeric(20));
		person.setMiddleInitial(RandomStringUtils.randomAlphanumeric(1).charAt(0));
		person.setPhoneNumber(RandomStringUtils.randomNumeric(10));
		return person;		
	}
	
	/**
	 * Makes a fake Address
	 * @return A fabricated address made of random data
	 */	
	public static Address makeFakeAddress() {
		Address address = new Address();
		address.setAddressLine1(RandomStringUtils.randomAlphanumeric(30));
		address.setAddressLine2(RandomStringUtils.randomAlphanumeric(30));
		address.setCity(RandomStringUtils.randomAlphanumeric(30));
		address.setState(RandomStringUtils.randomAlphanumeric(2));
		address.setZip(RandomStringUtils.randomNumeric(5));
		return address;
	}	
	
}
