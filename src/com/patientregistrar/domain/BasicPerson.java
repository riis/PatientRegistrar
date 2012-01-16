package com.patientregistrar.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * <p>
 * The class <code>BasicPerson</code> represents some base information
 * about a person - names and phone numbers.
 * </p>
 * @author Jeff Drost
 */
public class BasicPerson implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String firstName;
	
	private String lastName;
	
	private Character middleInitial;
	
	private String phoneNumber;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Character getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(Character middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
