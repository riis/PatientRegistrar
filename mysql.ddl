create database patientRegistrar;

use patientRegistrar;

create table emergencyContact(
emergencyContactid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
firstName varchar(35),
lastName varchar(35),
middleInitial varchar(1),
phoneNumber varchar(35)
);

create table employer (
employerid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(35),
phoneNumber varchar(35),
addressLine1 varchar(35),
addressLine2 varchar(35),
city varchar(35),
state varchar(35),
zip varchar(35)
);

create table person (
personid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
firstName varchar(35),
lastName varchar(35),
middleInitial varchar(1),
phoneNumber varchar(35),
dateOfBirth date,
ssn varchar(35),
addressLine1 varchar(35),
addressLine2 varchar(35),
city varchar(35),
state varchar(35),
zip varchar(35),
insuranceThruSelf boolean,
insuranceSourceId integer,
employerid integer,
emergencyContactid1 integer,
emergencyContactid2 integer,
CONSTRAINT FK_insuranceSourceId FOREIGN KEY (insuranceSourceId) REFERENCES person(personid),
CONSTRAINT FK_employerid FOREIGN KEY (employerid) REFERENCES employer(employerid),
CONSTRAINT FK_emergencyContactid1 FOREIGN KEY (emergencyContactid1) REFERENCES emergencyContact(emergencyContactid),
CONSTRAINT FK_emergencyContactid2 FOREIGN KEY (emergencyContactid2) REFERENCES emergencyContact(emergencyContactid)
);