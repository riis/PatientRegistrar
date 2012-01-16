package com.patientregistrar.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.patientregistrar.domain.Patient;
import com.patientregistrar.persistence.mongodb.PatientRepository;

@Component
public class PatientRepositoryImpl implements PatientRepository {

	private static final String SQL_INSERT_EMERGENCY_CONTACT = "insert into emergencyContact( firstName, lastName, middleInitial, phoneNumber ) values (?,?,?,?);";

	private static final String SQL_INSERT_EMPLOYER = "insert into employer( name, phoneNumber, addressLine1, addressLine2, city, state, zip ) values (?,?,?,?,?,?,?);";

	private static final String SQL_INSERT_PERSON = "insert into person ( firstName, lastName, middleInitial, phoneNumber, dateOfBirth, ssn, addressLine1, addressLine2, city, state, zip, insuranceThruSelf, insuranceSourceId, employerid, emergencyContactid1, emergencyContactid2 ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	
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

	@Override
	public List<Patient> save(Iterable<? extends Patient> arg0) {
		throw new UnimplementedException();
	}

	@Override
	public Page<Patient> findAll(Pageable arg0) {
		throw new UnimplementedException();
	}

	@Override
	public long count() {
		throw new UnimplementedException();
	}

	@Override
	public void delete(String arg0) {
		throw new UnimplementedException();
	}

	@Override
	public void delete(Patient arg0) {
		throw new UnimplementedException();
	}

	@Override
	public void delete(Iterable<? extends Patient> arg0) {
		throw new UnimplementedException();
	}

	@Override
	public void deleteAll() {
		throw new UnimplementedException();
	}

	@Override
	public boolean exists(String arg0) {
		throw new UnimplementedException();
	}

	@Override
	public Patient findOne(String arg0) {
		throw new UnimplementedException();
	}

	@Override
	public Patient save(Patient patient) {
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
				psPerson.setNull(14, Types.INTEGER);
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
			
			c.commit();
			
		} catch(SQLException sqle) {
			LOGGER.error(sqle, sqle);
			throw new RuntimeException(sqle);
		}
		return patient;
	}

	@Override
	public List<Patient> findByLastName(String lastname) {
		throw new UnimplementedException();
	}

}
