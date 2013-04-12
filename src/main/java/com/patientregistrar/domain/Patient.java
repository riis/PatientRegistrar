package com.patientregistrar.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * <p>
 * The class <code>Patient</code> represents a walk-in patient at a doctor's office.
 * </p>
 * @author Jeff Drost
 */
@Document(collection="patient")
public class Patient extends Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Boolean insuranceThroughSelf;
	
	private BasicPerson emergencyContact1;
	
	private BasicPerson emergencyContact2;
	
	private Person insuranceSource;
	
	public BasicPerson getEmergencyContact1() {
		return emergencyContact1;
	}

	public void setEmergencyContact1(BasicPerson emergencyContact1) {
		this.emergencyContact1 = emergencyContact1;
	}

	public BasicPerson getEmergencyContact2() {
		return emergencyContact2;
	}

	public void setEmergencyContact2(BasicPerson emergencyContact2) {
		this.emergencyContact2 = emergencyContact2;
	}

	public Boolean getInsuranceThroughSelf() {
		return insuranceThroughSelf;
	}

	public void setInsuranceThroughSelf(Boolean insuranceThroughSelf) {
		this.insuranceThroughSelf = insuranceThroughSelf;
	}

	public Person getInsuranceSource() {
		return insuranceSource;
	}

	public void setInsuranceSource(Person insuranceSource) {
		this.insuranceSource = insuranceSource;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
}
