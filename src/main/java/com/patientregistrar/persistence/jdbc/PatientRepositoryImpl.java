package com.patientregistrar.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.patientregistrar.domain.Address;
import com.patientregistrar.domain.BasicPerson;
import com.patientregistrar.domain.Employer;
import com.patientregistrar.domain.Patient;
import com.patientregistrar.domain.Person;
import com.patientregistrar.persistence.mongodb.PatientRepository;

@Component
public class PatientRepositoryImpl implements PatientRepository {

	private static final String SQL_INSERT_EMERGENCY_CONTACT = "insert into emergencyContact( firstName, lastName, middleInitial, phoneNumber ) values (?,?,?,?);";

	private static final String SQL_INSERT_EMPLOYER = "insert into employer( name, phoneNumber, addressLine1, addressLine2, city, state, zip ) values (?,?,?,?,?,?,?);";

	private static final String SQL_INSERT_PERSON = "insert into person ( firstName, lastName, middleInitial, phoneNumber, dateOfBirth, ssn, addressLine1, addressLine2, city, state, zip, insuranceThruSelf, insuranceSourceId, employerid, emergencyContactid1, emergencyContactid2 ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
	private static final String SQL_GET_ONE_PERSON = "select * from person where personid = ?";
	
	private static final String SQL_GET_ONE_ER_CONTACT = "select * from emergencycontact where emergencyContactId = ?";
	
	private static final String SQL_GET_ONE_EMPLOYER = "select * from employer where employerid = ?";
	
	private static final String SQL_DELETE_EMPLOYER = "delete from employer where employerid = ?";
	
	private static final String SQL_DELETE_PERSON = "delete from person where personid = ?";
	
	private static final String SQL_DELETE_ER_CONTACT = "delete from emergencycontact where emergencyContactid = ?";
	
	private static final String SQL_GET_DELETE_IDS = "select insuranceSourceId, employerid, emergencyContactid1, emergencyContactid2 from person where personid = ?";
	
	private static final String SQL_FIND_BY_LASTNAME = "select personid from person where upper(lastname) like ?";
	
	private static final Logger LOGGER = Logger.getLogger(PatientRepositoryImpl.class);

	@Autowired
	private DataSource dataSource;
	
	public static class UnimplementedException extends RuntimeException {
		
	}
	
	@Override
	public List<Patient> findAll() {
		throw new UnimplementedException();
	}

	@Override
	public List<Patient> findAll(Sort arg0) {
		throw new UnimplementedException();
	}

	/*
	@Override
	public List<Patient> save(Iterable<? extends Patient> patients) {
		List<Patient> savedPatients = new ArrayList<Patient>();
		for(Patient patient : patients) {
			savedPatients.add(save(patient));
		}
		return savedPatients;
	}
	
	@Override
	public List<Patient> save(Iterable<? extends Patient> patients) {
		// TODO Auto-generated method stub
		return super.save(patients);
	}
	^ both above are messed up		
	*/

	@Override
	public Page<Patient> findAll(Pageable arg0) {
		throw new UnimplementedException();
	}

	@Override
	public long count() {
		throw new UnimplementedException();
	}

	@Override
	public void delete(String id) {		
		try(Connection c = dataSource.getConnection(); 
				PreparedStatement psContact = c.prepareStatement(SQL_DELETE_ER_CONTACT); 
				PreparedStatement psEmployer = c.prepareStatement(SQL_DELETE_EMPLOYER); 
				PreparedStatement psPerson = c.prepareStatement(SQL_DELETE_PERSON);
				PreparedStatement psLookupIds = c.prepareStatement(SQL_GET_DELETE_IDS)) {
			
			c.setAutoCommit(false);			
			int personId = Integer.valueOf(id);
			
			psLookupIds.setInt(1, personId);
			ResultSet rs = psLookupIds.executeQuery();
			rs.next();
			Integer insuranceSourceId = rs.getInt("insuranceSourceId");
			int employerid = rs.getInt("employerid");
			int emergencyContactid1 = rs.getInt("emergencyContactid1");
			int emergencyContactid2 = rs.getInt("emergencyContactid2");
			
			if(insuranceSourceId != null) {
				psLookupIds.clearParameters();
				psLookupIds.setInt(1, personId);
				rs = psLookupIds.executeQuery();
				rs.next();
				int altemergencyContactid1 = rs.getInt("emergencyContactid1");
				int altemergencyContactid2 = rs.getInt("emergencyContactid2");

				psPerson.clearParameters();
				psPerson.setInt(1, personId);
				psPerson.executeUpdate();				
				
				psContact.clearParameters();
				psContact.setInt(1, altemergencyContactid1);
				psContact.executeUpdate();

				psContact.clearParameters();
				psContact.setInt(1, altemergencyContactid2);
				psContact.executeUpdate();
			}			
			
			psPerson.clearParameters();
			psPerson.setInt(1, personId);
			psPerson.executeUpdate();			
			
			psContact.clearParameters();
			psContact.setInt(1, emergencyContactid1);
			psContact.executeUpdate();
			
			psContact.clearParameters();
			psContact.setInt(1, emergencyContactid2);
			psContact.executeUpdate();
					
			psEmployer.clearParameters();
			psEmployer.setInt(1,employerid);
			psEmployer.executeUpdate();
						
			c.commit();
			
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
	}

	@Override
	public void delete(Patient patient) {
		delete(patient.getId());
	}

	@Override
	public void delete(Iterable<? extends Patient> patients) {
		for(Patient patient : patients) {
			delete(patient.getId());
		}
	}

	@Override
	public void deleteAll() {
		throw new UnimplementedException();
	}

	@Override
	public boolean exists(String id) {
		try(Connection c = dataSource.getConnection(); PreparedStatement psPerson = c.prepareStatement(SQL_GET_ONE_PERSON)) {
			psPerson.setInt(1, Integer.valueOf(id));
			ResultSet rs = psPerson.executeQuery();
			return rs.next();			
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
	}

	@Override
	public Patient findOne(String id) {
		Patient patient = null;
		try(Connection c = dataSource.getConnection(); PreparedStatement psPerson = c.prepareStatement(SQL_GET_ONE_PERSON)) {
					
			psPerson.setInt(1, Integer.valueOf(id));
			ResultSet rs = psPerson.executeQuery();
			boolean exists = rs.next();
			if(!exists) {
				return patient;
			}
			
			patient = new Patient();
			patient.setAddress(new Address());
			patient.getAddress().setAddressLine1(rs.getString("addressLine1"));
			patient.getAddress().setAddressLine2(rs.getString("addressLine2"));
			patient.getAddress().setCity(rs.getString("city"));
			patient.getAddress().setState(rs.getString("state"));
			patient.getAddress().setZip(rs.getString("zip"));
			patient.setDateOfBirth(rs.getDate("dateOfBirth"));
			patient.setEmergencyContact1(fetchEmergencyContact(rs.getInt("emergencyContactid1")));
			patient.setEmergencyContact2(fetchEmergencyContact(rs.getInt("emergencyContactid2")));
			patient.setEmployer(fetchEmployer(rs.getInt("employerid")));
			patient.setFirstName(rs.getString("firstName"));
			patient.setId(id);			
			patient.setInsuranceThroughSelf(rs.getBoolean("insuranceThruSelf"));
			if(!patient.getInsuranceThroughSelf()) {
				patient.setInsuranceSource(fetchInsuranceSource(rs.getInt("insuranceSourceId")));
			}
			patient.setLastName(rs.getString("lastname"));
			patient.setMiddleInitial(rs.getString("middleInitial").charAt(0));
			patient.setPhoneNumber(rs.getString("phoneNumber"));
			patient.setSsn(rs.getString("ssn"));
			
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
		return patient;
	}
	
	private Person fetchInsuranceSource(int id) {
		Person person = null;
		
		try(Connection c = dataSource.getConnection(); PreparedStatement psPerson = c.prepareStatement(SQL_GET_ONE_PERSON)) {
			
			psPerson.setInt(1, id);
			ResultSet rs = psPerson.executeQuery();
			boolean exists = rs.next();
			if(!exists) {
				return person;
			}

			person = new Person();
			person.setAddress(new Address());
			person.getAddress().setAddressLine1(rs.getString("addressLine1"));
			person.getAddress().setAddressLine2(rs.getString("addressLine2"));
			person.getAddress().setCity(rs.getString("city"));
			person.getAddress().setState(rs.getString("state"));
			person.getAddress().setZip(rs.getString("zip"));
			person.setDateOfBirth(rs.getDate("dateOfBirth"));
			person.setEmployer(fetchEmployer(rs.getInt("employerid")));
			person.setFirstName(rs.getString("firstName"));
			person.setLastName(rs.getString("lastname"));
			person.setMiddleInitial(rs.getString("middleInitial").charAt(0));
			person.setPhoneNumber(rs.getString("phoneNumber"));
			person.setSsn(rs.getString("ssn"));

		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}		
		
		return person;
	}
	
	private BasicPerson fetchEmergencyContact(int id) {
		BasicPerson contact = null;
		
		try(Connection c = dataSource.getConnection(); PreparedStatement psContact = c.prepareStatement(SQL_GET_ONE_ER_CONTACT)) {
			
			psContact.setInt(1, id);
			ResultSet rs = psContact.executeQuery();
			boolean exists = rs.next();
			if(!exists) {
				return contact;
			}

			contact = new BasicPerson();
			contact.setFirstName(rs.getString("firstName"));
			contact.setLastName(rs.getString("lastName"));
			contact.setMiddleInitial(rs.getString("middleInitial").charAt(0));
			contact.setPhoneNumber(rs.getString("phoneNumber"));
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
		
		return contact;
	}
	
	private Employer fetchEmployer(int id) {
		Employer employer = null;
		
		try(Connection c = dataSource.getConnection(); PreparedStatement psEmployer = c.prepareStatement(SQL_GET_ONE_EMPLOYER)) {
			
			psEmployer.setInt(1, id);
			ResultSet rs = psEmployer.executeQuery();
			boolean exists = rs.next();
			if(!exists) {
				return employer;
			}

			employer = new Employer();
			employer.setName(rs.getString("name"));
			employer.setPhoneNumber(rs.getString("phoneNumber"));
			employer.setAddress(new Address());
			employer.getAddress().setAddressLine1(rs.getString("addressLine1"));
			employer.getAddress().setAddressLine2(rs.getString("addressLine2"));
			employer.getAddress().setCity(rs.getString("city"));
			employer.getAddress().setState(rs.getString("state"));
			employer.getAddress().setZip(rs.getString("zip"));			
			
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
		
		return employer;
		
	}

	@Override
	public Patient save(Patient patient) {		
		if(patient.getId() != null) {
			return update(patient);
		} else {
			return insert(patient);
		}
	}

	private Patient insert(Patient patient) {
		try(Connection c = dataSource.getConnection(); 
				PreparedStatement psEmergency = c.prepareStatement(SQL_INSERT_EMERGENCY_CONTACT,Statement.RETURN_GENERATED_KEYS);
				PreparedStatement psEmployer = c.prepareStatement(SQL_INSERT_EMPLOYER,Statement.RETURN_GENERATED_KEYS); 
				PreparedStatement psPerson = c.prepareStatement(SQL_INSERT_PERSON,Statement.RETURN_GENERATED_KEYS)) {
			
			c.setAutoCommit(false);
			
			psEmergency.setString(1, patient.getEmergencyContact1().getFirstName());
			psEmergency.setString(2, patient.getEmergencyContact1().getLastName());
			psEmergency.setString(3, String.valueOf(patient.getEmergencyContact1().getMiddleInitial()));
			psEmergency.setString(4, patient.getEmergencyContact1().getPhoneNumber());
			psEmergency.executeUpdate();
			ResultSet rs = psEmergency.getGeneratedKeys();
			rs.next();
			int emergencyContactId1 = rs.getInt(1);

			psEmergency.clearParameters();
			psEmergency.setString(1, patient.getEmergencyContact2().getFirstName());
			psEmergency.setString(2, patient.getEmergencyContact2().getLastName());
			psEmergency.setString(3, String.valueOf(patient.getEmergencyContact2().getMiddleInitial()));
			psEmergency.setString(4, patient.getEmergencyContact2().getPhoneNumber());
			psEmergency.executeUpdate();
			rs = psEmergency.getGeneratedKeys();
			rs.next();
			int emergencyContactId2 = rs.getInt(1);
			
			psEmployer.setString(1, patient.getEmployer().getName());
			psEmployer.setString(2, patient.getEmployer().getPhoneNumber());
			psEmployer.setString(3, patient.getEmployer().getAddress().getAddressLine1());
			psEmployer.setString(4, patient.getEmployer().getAddress().getAddressLine2());
			psEmployer.setString(5, patient.getEmployer().getAddress().getCity());
			psEmployer.setString(6, patient.getEmployer().getAddress().getState());
			psEmployer.setString(7, patient.getEmployer().getAddress().getZip());
			psEmployer.executeUpdate();
			rs = psEmployer.getGeneratedKeys();
			rs.next();
			int employerId = rs.getInt(1);
			
			int insuranceSourceId = 0;
			if(!patient.getInsuranceThroughSelf()) {				
				
				psEmployer.clearParameters();
				psEmployer.setString(1, patient.getInsuranceSource().getEmployer().getName());
				psEmployer.setString(2, patient.getInsuranceSource().getEmployer().getPhoneNumber());
				psEmployer.setString(3, patient.getInsuranceSource().getEmployer().getAddress().getAddressLine1());
				psEmployer.setString(4, patient.getInsuranceSource().getEmployer().getAddress().getAddressLine2());
				psEmployer.setString(5, patient.getInsuranceSource().getEmployer().getAddress().getCity());
				psEmployer.setString(6, patient.getInsuranceSource().getEmployer().getAddress().getState());
				psEmployer.setString(7, patient.getInsuranceSource().getEmployer().getAddress().getZip());
				psEmployer.executeUpdate();
				rs = psEmployer.getGeneratedKeys();
				rs.next();
				int insuranceEmployerId = rs.getInt(1);				
				
				psPerson.setString(1, patient.getInsuranceSource().getFirstName());
				psPerson.setString(2, patient.getInsuranceSource().getLastName());
				psPerson.setString(3, String.valueOf(patient.getInsuranceSource().getMiddleInitial()));
				psPerson.setString(4, patient.getInsuranceSource().getPhoneNumber());
				psPerson.setDate(5, new java.sql.Date(patient.getInsuranceSource().getDateOfBirth().getTime()));
				psPerson.setString(6, patient.getInsuranceSource().getSsn());
				psPerson.setString(7, patient.getInsuranceSource().getAddress().getAddressLine1());
				psPerson.setString(8, patient.getInsuranceSource().getAddress().getAddressLine2());
				psPerson.setString(9, patient.getInsuranceSource().getAddress().getCity());
				psPerson.setString(10, patient.getInsuranceSource().getAddress().getState());
				psPerson.setString(11, patient.getInsuranceSource().getAddress().getZip());
				psPerson.setBoolean(12, true);
				psPerson.setNull(13, Types.INTEGER);
				psPerson.setInt(14, insuranceEmployerId);
				psPerson.setNull(15, Types.INTEGER);
				psPerson.setNull(16, Types.INTEGER);
				psPerson.executeUpdate();
				rs = psPerson.getGeneratedKeys();
				rs.next();
				insuranceSourceId = rs.getInt(1);
			}			
			
			psPerson.clearParameters();
			psPerson.setString(1, patient.getFirstName());
			psPerson.setString(2, patient.getLastName());
			psPerson.setString(3, String.valueOf(patient.getMiddleInitial()));
			psPerson.setString(4, patient.getPhoneNumber());
			psPerson.setDate(5, new java.sql.Date(patient.getDateOfBirth().getTime()));
			psPerson.setString(6, patient.getSsn());
			psPerson.setString(7, patient.getAddress().getAddressLine1());
			psPerson.setString(8, patient.getAddress().getAddressLine2());
			psPerson.setString(9, patient.getAddress().getCity());
			psPerson.setString(10, patient.getAddress().getState());
			psPerson.setString(11, patient.getAddress().getZip());
			psPerson.setBoolean(12, patient.getInsuranceThroughSelf());
			if(!patient.getInsuranceThroughSelf()) {
				psPerson.setInt(13, insuranceSourceId);
			} else {
				psPerson.setNull(13, Types.INTEGER);
			}
			psPerson.setInt(14, employerId); 
			psPerson.setInt(15, emergencyContactId1); 
			psPerson.setInt(16, emergencyContactId2);
			psPerson.executeUpdate();
			
			rs = psPerson.getGeneratedKeys();
			rs.next();
			patient.setId(String.valueOf(rs.getInt(1)));			
			
			c.commit();
			
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
		return patient;		
	}

	private Patient update(Patient patient) {
		delete(patient.getId());
		return insert(patient);
	}

	@Override
	public List<Patient> findByLastName(String lastname) {
		List<Patient> patients = new ArrayList<Patient>();		
		try(Connection c = dataSource.getConnection(); PreparedStatement psFind = c.prepareStatement(SQL_FIND_BY_LASTNAME)) {
			psFind.setString(1, lastname.toUpperCase());
			ResultSet rs = psFind.executeQuery();
			while(rs.next()) {
				patients.add(findOne(String.valueOf(rs.getInt("personid"))));
			}
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}		
		return patients;
	}
	
	// THE BELOW ARE NEW
	
	@Override
	public Iterable<Patient> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <S extends Patient> List<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
