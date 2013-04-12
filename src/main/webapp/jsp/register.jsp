<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Patient Registrar - New Patient Entry</title>
	
	<style>	
		div#leftNav { height: 100%; width: 11em; background-color: olive; float: left; overflow: hidden; }
		div#mainContent { height: 100%; float: left; }
	
		span {font-weight: bold; color: red;}
	
		div.majorSection { border: thin; border-color: black; border-style: solid; margin-bottom: 10px; margin-left: 5px; }
		div.majorSection input { display: block; }		
		div.majorSection h3 { margin-bottom: 5px; margin-top: 5px; margin-left: 5px; width: 100%; }
		div.majorSection div { width: 49%; float: left; margin-left: 1%; } 
				
		div#base { width: 525px; height: 360px; }
		div#emergencyContacts { width: 525px; height: 230px; }
		div#employer { width: 525px; height: 280px; }
		div#insurance { width: 525px; height: 325px; }
		div#insuranceEmployer { width: 525px; height: 270px; }		
	</style>
	
</head>

<body>
<form:form name="newpatient" modelAttribute="newpatient" commandName="newpatient" method="post">

<%-- header --%>
<h2 style="display: inline;">Welcome to the New Patient Registrar!</h2>
<div><form:errors path="*" /></div>
<p>Fill in all of the fields below, then present your photo ID and insurance cards to the front desk.</p>

<%-- left nav --%>
<div id="leftNav">
	<img src="images/swineflu.jpg" />
</div>

<%-- main content area --%>
<div id="mainContent">

	<div id="base" class="majorSection">	
		<h3>Basic Patient Information</h3>
		<div>		
			First Name: <form:input maxlength="35" size="35" path="firstName" />
			Last Name: <form:input maxlength="35" size="35" path="lastName" />
			Middle Initial: <form:input maxlength="1" size="35" path="middleInitial" />
			Phone Number: <form:input maxlength="35" size="35" path="phoneNumber" />
			Date Of Birth: <form:input maxlength="35" size="35" path="dateOfBirth" />
			Social Security Number: <form:input maxlength="35" size="35" path="ssn" />
			Insurance Through Self: <form:checkbox path="insuranceThroughSelf" />						
		</div>	
		<div>		
			Address Line 1: <form:input maxlength="35" size="35" path="address.addressLine1" />
			Address Line 2: <form:input maxlength="35" size="35" path="address.addressLine2" />
			City: <form:input maxlength="35" size="35" path="address.city" />
			State: <form:input maxlength="35" size="35" path="address.state" />
			Zip Code: <form:input maxlength="35" size="35" path="address.zip" />
		</div>
	</div>
	
	<div id="emergencyContacts" class="majorSection">
		<h3>Emergency Contacts</h3>
		<div>
			First Name: <form:input maxlength="35" size="35" path="emergencyContact1.firstName" />
			Last Name: <form:input maxlength="35" size="35" path="emergencyContact1.lastName" />
			Middle Initial: <form:input maxlength="1" size="35" path="emergencyContact1.middleInitial" />
			Phone Number: <form:input maxlength="35" size="35" path="emergencyContact1.phoneNumber" />		
		</div>	
		<div>
			First Name: <form:input maxlength="35" size="35" path="emergencyContact2.firstName" />
			Last Name: <form:input maxlength="35" size="35" path="emergencyContact2.lastName" />
			Middle Initial: <form:input maxlength="1" size="35" path="emergencyContact2.middleInitial" />
			Phone Number: <form:input maxlength="35" size="35" path="emergencyContact2.phoneNumber" />		
		</div>
	</div>
	
	<div id="employer" class="majorSection">
		<h3>Employer</h3>
		<div>		
			Company Name: <form:input maxlength="35" size="35" path="employer.name" />
			Phone Number: <form:input maxlength="35" size="35" path="employer.phoneNumber" />	
		</div>	
		<div>
			Address Line 1: <form:input maxlength="35" size="35" path="employer.address.addressLine1" />
			Address Line 2: <form:input maxlength="35" size="35" path="employer.address.addressLine2" />
			City: <form:input maxlength="35" size="35" path="employer.address.city" />
			State: <form:input maxlength="35" size="35" path="employer.address.state" />
			Zip Code: <form:input maxlength="35" size="35" path="employer.address.zip" />	
		</div>
	</div>
	
	<div id="insurance" class="majorSection">	
		<h3>Source of Insurance</h3>
		<div>		
			First Name: <form:input maxlength="35" size="35" path="insuranceSource.firstName" />
			Last Name: <form:input maxlength="35" size="35" path="insuranceSource.lastName" />
			Middle Initial: <form:input maxlength="1" size="35" path="insuranceSource.middleInitial" />
			Phone Number: <form:input maxlength="35" size="35" path="insuranceSource.phoneNumber" />
			Date Of Birth: <form:input maxlength="35" size="35" path="insuranceSource.dateOfBirth" />
			Social Security Number: <form:input maxlength="35" size="35" path="insuranceSource.ssn" />
		</div>
		<div>
			Address Line 1: <form:input maxlength="35" size="35" path="insuranceSource.address.addressLine1" />
			Address Line 2: <form:input maxlength="35" size="35" path="insuranceSource.address.addressLine2" />
			City: <form:input maxlength="35" size="35" path="insuranceSource.address.city" />
			State: <form:input maxlength="35" size="35" path="insuranceSource.address.state" />
			Zip Code: <form:input maxlength="35" size="35" path="insuranceSource.address.zip" />	
		</div>	
	</div>
	
	<div id="insuranceEmployer" class="majorSection">	
		<h3>Insurance Source's Employer</h3>
		<div>
			Company Name: <form:input maxlength="35" size="35" path="insuranceSource.employer.name" />
			Phone Number: <form:input maxlength="35" size="35" path="insuranceSource.employer.phoneNumber" />	
		</div>	
		<div>
			Address Line 1: <form:input maxlength="35" size="35" path="insuranceSource.employer.address.addressLine1" />
			Address Line 2: <form:input maxlength="35" size="35" path="insuranceSource.employer.address.addressLine2" />
			City: <form:input maxlength="35" size="35" path="insuranceSource.employer.address.city" />
			State: <form:input maxlength="35" size="35" path="insuranceSource.employer.address.state" />
			Zip Code: <form:input maxlength="35" size="35" path="insuranceSource.employer.address.zip" />	
		</div>	
	</div>
	
	<input type="submit" />

</div>

</form:form>
</body>

</html>