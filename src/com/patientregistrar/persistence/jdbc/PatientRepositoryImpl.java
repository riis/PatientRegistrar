package com.patientregistrar.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

	private static final String SQL_INSERT_PERSON = "insert into person ( firstName, lastName, middleInitial, phoneNumber, dateOfBirth, ssn, addressLine1, addressLine2, city, state, zip, patient, insuranceSource, insuranceSourceId, employerid, emergencyContactid1, emergencyContactid2 ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";	
	
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
		try(Connection c = dataSource.getConnection()) {
			
			try(PreparedStatement ps = c.prepareStatement(SQL_INSERT_EMERGENCY_CONTACT)) {
				
			}
			
			LOGGER.info("GOT HERE");
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
