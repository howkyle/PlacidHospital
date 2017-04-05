/*--------------------------Create Tables-----------------------------*/
DROP TABLE IF EXISTS doctor CASCADE;
DROP TABLE IF EXISTS  consultant CASCADE;
DROP TABLE IF EXISTS intern CASCADE;
DROP TABLE IF EXISTS resident CASCADE;
DROP TABLE IF EXISTS patient CASCADE;
DROP TABLE IF EXISTS nurse CASCADE;
DROP TABLE IF EXISTS registered CASCADE;
DROP TABLE IF EXISTS registered_midwife CASCADE;
DROP TABLE IF EXISTS enrolled CASCADE;
DROP TABLE IF EXISTS disease CASCADE;
DROP TABLE IF EXISTS family_history CASCADE;
DROP TABLE IF EXISTS procedure CASCADE;
DROP TABLE IF EXISTS test_results CASCADE;
DROP TABLE IF EXISTS diagnosis CASCADE;
DROP TABLE IF EXISTS treatment CASCADE;
DROP TABLE IF EXISTS medication CASCADE;
DROP TABLE IF EXISTS allergy CASCADE;
DROP TABLE IF EXISTS secretary CASCADE;
DROP TABLE IF EXISTS diagnosis_disease CASCADE;
DROP TABLE IF EXISTS scan CASCADE;
DROP TABLE IF EXISTS result_scan CASCADE;
DROP TABLE IF EXISTS administer_medication CASCADE;

CREATE TABLE secretary(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50),password varchar(30));
CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), category varchar (30), password varchar(50),tel_number varchar(50));
CREATE TABLE consultant(id SERIAL PRIMARY KEY, doctorID int references doctor(id), specialization varchar(30));
CREATE TABLE intern(id SERIAL PRIMARY KEY, doctorID int references doctor(id));
CREATE TABLE resident(id SERIAL PRIMARY KEY, doctorID int references doctor(id));
CREATE TABLE patient(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), tel_number varchar(50), dob date, address varchar(30));
CREATE TABLE nurse(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), dob date, address varchar(30), tel_number varchar(50), category varchar(20), password varchar(50));
CREATE TABLE registered(id SERIAL PRIMARY KEY, nurseID int references nurse(id));
CREATE TABLE registered_midwife(id SERIAL PRIMARY KEY, nurseID int references nurse(id));
CREATE TABLE enrolled(id SERIAL PRIMARY KEY, nurseID int references nurse(id));
CREATE TABLE disease(id SERIAL PRIMARY KEY, disease_name varchar(40));
CREATE TABLE family_history(id SERIAL PRIMARY KEY, first_name varchar(50), last_name varchar(50), disease int references disease(id), patient int references patient(id));
CREATE TABLE procedure(id SERIAL PRIMARY KEY, test varchar(50), patient int references patient(id));
CREATE TABLE test_results(id SERIAL PRIMARY KEY, results varchar(50), procedure int references procedure(id));
CREATE TABLE diagnosis(id SERIAL PRIMARY KEY, diagnosis_date date, resultID int references test_results(id));
CREATE TABLE treatment(id SERIAL PRIMARY KEY, doctorID int references doctor(id), patientID int references patient(id), treatment_date date);
CREATE TABLE medication(id SERIAL PRIMARY KEY, name varchar(50));
CREATE TABLE allergy(id SERIAL PRIMARY KEY, patientID int references patient(id), medicationID int references medication(id));
CREATE TABLE diagnosis_disease(id SERIAL PRIMARY KEY, diseaseID int references disease(id), diagnosisID int references diagnosis(id));
CREATE TABLE scan(id SERIAL PRIMARY KEY, scanImg varchar(100));
CREATE TABLE result_scan(id SERIAL PRIMARY KEY, resultID int references test_results(id), scanID int references scan(id));
CREATE TABLE administer_medication(id SERIAL PRIMARY KEY, nurseID int references nurse(id), patientID int references patient(id), administer_date date);
/*-----------------------------------------------------------------*/

/*-------------------------Secretaries----------------------------*/
INSERT INTO secretary(first_name, last_name, password) VALUES ('Robert','Roach','secpassword');
/*------------------------------------------------------------*/

/*-------------------------Patients----------------------------*/
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Vincent','Peck','4056192','1977-10-8','899 Fenton St Harlem');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Adam','Garza','2742068','1973-7-16','822 Hilling Street MoBay');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Bernard','Gream','4547216','1972-4-28','334 Qonti St Rocky');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Beverly','Frezzo','2253704','1988-7-21','26 Deva Ave Portmore');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Yolanda','Robinson','5292303','1974-8-9','494 Marine St Katon');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Carmen','Morin','3078272','1993-11-6','377 Fenton St Stonehenge');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Andre','Bruce','5486177','1958-8-7','492 Deva Ave Rocky');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Jeffrey','Moody','5217862','1956-12-17','447 Fenton St Rocky');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Lenard','Branch','4185244','1993-11-10','248 Fillo Ave Portmore');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Michael','Francescone','3335072','2000-6-20','699 Fenton St MoBay');
/*------------------------------------------------------------*/

/*-------------------------Doctors------------------------------*/
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Regina','Mettig','consultant','docpassword','6506580');
INSERT INTO consultant(id, specialization) VALUES (1,'neonatology');
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Ivan','Scott','consultant','docpassword','7414145');
INSERT INTO consultant(id, specialization) VALUES (2,'paediatrics');
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Ellen','Ford','consultant','docpassword','7904757');
INSERT INTO consultant(id, specialization) VALUES (3,'neurology');
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('John','Hanna','consultant','docpassword','7062617');
INSERT INTO consultant(id, specialization) VALUES (4,'dermatology');
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Michael','Obrien','intern','docpassword','6473465');
INSERT INTO intern(id) VALUES (5);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Eleanor','Kochheiser','intern','docpassword','7542078');
INSERT INTO intern(id) VALUES (6);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Robert','Babb','intern','docpassword','7711050');
INSERT INTO intern(id) VALUES (7);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Beth','Furr','resident','docpassword','7826303');
INSERT INTO resident(id) VALUES (8);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Pamela','Porter','resident','docpassword','7600688');
INSERT INTO resident(id) VALUES (9);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Geoffrey','Wooley','intern','docpassword','7594839');
INSERT INTO intern(id) VALUES (10);
/*------------------------------------------------------------*/

/*-------------------------Nurses------------------------------*/
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Irene','Franz','1951-9-14','856 Brown St Kingston','7622000','registered','nursepassword');
INSERT INTO registered(id) VALUES (1);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Rose','Mathis','2000-8-17','428 Brown St Harlem','7686539','enrolled','nursepassword');
INSERT INTO enrolled(id) VALUES (2);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Edith','Ward','1984-4-13','86 Fillo Ave Carton','6830782','registered','nursepassword');
INSERT INTO registered(id) VALUES (3);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Kerri','Wilkins','1957-10-2','184 Hilling Street Rocky','7386267','registered_midwife','nursepassword');
INSERT INTO registered_midwife(id) VALUES (4);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Timothy','Arenas','1967-3-26','117 Fenton St Kingston','6254869','enrolled','nursepassword');
INSERT INTO enrolled(id) VALUES (5);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Allison','Ellis','1959-6-8','880 Fenton St Harlem','7226472','registered_midwife','nursepassword');
INSERT INTO registered_midwife(id) VALUES (6);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Robert','Steele','1968-3-10','521 Deva Ave Kingston','7259548','registered','nursepassword');
INSERT INTO registered(id) VALUES (7);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Roy','Bunce','1968-7-19','858 Fillo Ave Portmore','7340183','enrolled','nursepassword');
INSERT INTO enrolled(id) VALUES (8);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Clarence','Dupas','1989-12-6','814 Hilling Street Fort Dunn','7321039','enrolled','nursepassword');
INSERT INTO enrolled(id) VALUES (9);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Minnie','Hartman','1965-7-26','271 Fillo Ave Kingston','7101718','enrolled','nursepassword');
INSERT INTO enrolled(id) VALUES (10);
/*------------------------------------------------------------*/

/*-------------------------Diseases------------------------------*/
INSERT INTO disease(disease_name) VALUES ('cholera');
INSERT INTO disease(disease_name) VALUES ('flu');
INSERT INTO disease(disease_name) VALUES ('coronary artery disease');
INSERT INTO disease(disease_name) VALUES ('diabetes A');
INSERT INTO disease(disease_name) VALUES ('diabetes B');
INSERT INTO disease(disease_name) VALUES ('gastroesophageal reflux disease');
INSERT INTO disease(disease_name) VALUES ('alzheimers disease');
INSERT INTO disease(disease_name) VALUES ('asthma');
INSERT INTO disease(disease_name) VALUES ('autism');
INSERT INTO disease(disease_name) VALUES ('brain cancer');
INSERT INTO disease(disease_name) VALUES ('bone cancer');
INSERT INTO disease(disease_name) VALUES ('breast cancer');
INSERT INTO disease(disease_name) VALUES ('celiac disease');
/*------------------------------------------------------------*/

/*-------------------------Medications------------------------------*/
INSERT INTO medication(name) VALUES ('glucophage');
INSERT INTO medication(name) VALUES ('hydrochlorothiazide');
INSERT INTO medication(name) VALUES ('azithromycin');
INSERT INTO medication(name) VALUES ('zocor');
INSERT INTO medication(name) VALUES ('hydrocodone');
/*------------------------------------------------------------*/

/*-------------------------Fam History------------------------------*/
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Richard','Stanley',11,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Brett','Fountain',13,7);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Christopher','Oszust',1,3);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Eddie','Leblanc',5,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('David','Pankey',6,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Kevin','Caldwell',10,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Lisa','Sweet',10,8);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Paul','Nair',5,1);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Stephen','Bartholomew',1,7);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Amanda','Seiwell',11,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Steven','Rosenfeld',1,8);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('James','Bard',5,3);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Anderson','Leonard',1,3);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Eddie','Thompson',2,2);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Dolores','Jones',2,6);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Deborah','Snell',1,2);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Michael','Wilson',2,7);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Johnny','Cox',11,8);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Nancy','Ward',10,3);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Carol','Castillo',3,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Raymond','Pele',7,7);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Lucinda','Thiele',6,8);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Steve','Bayly',1,2);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Raymond','Cropp',11,10);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Genia','Smith',4,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Kelsey','Dobbin',12,1);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Maria','Watkins',5,6);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Velma','Witcher',10,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Joseph','Greenup',2,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Benjamin','Landry',6,2);
/*------------------------------------------------------------*/

/*-------------------------Procedures------------------------------*/
INSERT INTO procedure(test, patient) VALUES ('intradermal test',5);
INSERT INTO procedure(test, patient) VALUES ('skin prick test',5);
INSERT INTO procedure(test, patient) VALUES ('blood test',1);
INSERT INTO procedure(test, patient) VALUES ('skin patch test',1);
INSERT INTO procedure(test, patient) VALUES ('tb skin test',7);
INSERT INTO procedure(test, patient) VALUES ('skin patch test',7);
INSERT INTO procedure(test, patient) VALUES ('skin prick test',4);
INSERT INTO procedure(test, patient) VALUES ('blood test',4);
INSERT INTO procedure(test, patient) VALUES ('skin prick test',3);
INSERT INTO procedure(test, patient) VALUES ('blood test',8);
/*------------------------------------------------------------*/

/*-------------------------Test Results------------------------------*/
INSERT INTO test_results(results, procedure) VALUES ('moderate concern',1);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',2);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',3);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',4);
INSERT INTO test_results(results, procedure) VALUES ('moderate concern',5);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',6);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',7);
INSERT INTO test_results(results, procedure) VALUES ('moderate concern',8);
INSERT INTO test_results(results, procedure) VALUES ('moderate concern',9);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',10);
/*------------------------------------------------------------*/

/*---------------------------Diagnoses------------------------------*/
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2017-3-15',1);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2012-7-6',2);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2011-1-18',3);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2008-11-5',4);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2001-5-12',5);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2013-4-26',6);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2008-4-25',7);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2007-8-28',8);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2007-10-15',9);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2002-5-27',10);
/*------------------------------------------------------------*/

/*---------------------------Treatments------------------------------*/
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (1,8,'2013-1-9');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (7,3,'2008-8-4');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (3,6,'2004-11-18');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (5,7,'2016-10-7');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (1,9,'2007-12-28');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (1,1,'2010-12-4');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (2,10,'2011-12-14');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (9,8,'2015-4-22');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (1,2,'2014-10-24');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (8,7,'2001-5-22');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (3,1,'2007-11-14');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (2,2,'2009-7-7');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (10,8,'2004-5-15');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (5,10,'2012-3-17');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (1,7,'2009-2-20');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (4,10,'2013-12-26');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (1,8,'2009-8-26');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (8,4,'2007-8-13');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (4,4,'2001-9-27');
INSERT INTO treatment(doctorID,patientID,treatment_date) VALUES (7,10,'2007-3-8');
/*------------------------------------------------------------*/

/*---------------------------Diagnosis Diseases------------------------------*/
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (3,1);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (3,2);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (12,3);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (2,4);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (3,5);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (1,6);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (5,7);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (5,8);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (1,9);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (3,10);
/*------------------------------------------------------------*/

/*---------------------------Allergies------------------------------*/
INSERT INTO allergy(patientID, medicationID) VALUES (8,5);
INSERT INTO allergy(patientID, medicationID) VALUES (1,1);
INSERT INTO allergy(patientID, medicationID) VALUES (9,4);
INSERT INTO allergy(patientID, medicationID) VALUES (5,2);
INSERT INTO allergy(patientID, medicationID) VALUES (2,5);
INSERT INTO allergy(patientID, medicationID) VALUES (7,5);
INSERT INTO allergy(patientID, medicationID) VALUES (1,5);
INSERT INTO allergy(patientID, medicationID) VALUES (6,5);
INSERT INTO allergy(patientID, medicationID) VALUES (7,5);
INSERT INTO allergy(patientID, medicationID) VALUES (7,4);
/*------------------------------------------------------------*/

/*---------------------------Scans------------------------------*/
INSERT INTO scan(scanImg) VALUES ('scan 1');
INSERT INTO scan(scanImg) VALUES ('scan 2');
INSERT INTO scan(scanImg) VALUES ('scan 3');
INSERT INTO scan(scanImg) VALUES ('scan 4');
INSERT INTO scan(scanImg) VALUES ('scan 5');
INSERT INTO scan(scanImg) VALUES ('scan 6');
INSERT INTO scan(scanImg) VALUES ('scan 7');
INSERT INTO scan(scanImg) VALUES ('scan 8');
INSERT INTO scan(scanImg) VALUES ('scan 9');
INSERT INTO scan(scanImg) VALUES ('scan 10');
/*------------------------------------------------------------*/

/*---------------------------Reult Scans------------------------------*/
INSERT INTO result_scan(resultID, scanID) VALUES (9,1);
INSERT INTO result_scan(resultID, scanID) VALUES (10,2);
INSERT INTO result_scan(resultID, scanID) VALUES (9,3);
INSERT INTO result_scan(resultID, scanID) VALUES (10,4);
INSERT INTO result_scan(resultID, scanID) VALUES (1,5);
INSERT INTO result_scan(resultID, scanID) VALUES (6,6);
INSERT INTO result_scan(resultID, scanID) VALUES (3,7);
INSERT INTO result_scan(resultID, scanID) VALUES (7,8);
INSERT INTO result_scan(resultID, scanID) VALUES (2,9);
INSERT INTO result_scan(resultID, scanID) VALUES (5,10);
/*------------------------------------------------------------*/

/*---------------------------Administerd Medication------------------------------*/
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (9,4,'2010-7-1');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (3,5,'2000-11-21');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (2,2,'2015-11-16');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (2,5,'2005-7-15');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (1,1,'2000-3-23');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (5,3,'2006-1-10');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (2,7,'2007-2-7');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (8,9,'2008-8-11');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (7,3,'2000-5-15');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (6,7,'2001-2-11');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (5,6,'2008-7-14');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (1,5,'2002-11-6');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (7,2,'2005-3-28');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (6,8,'2011-2-8');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (7,1,'2006-9-25');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (10,3,'2007-9-3');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (7,10,'2014-2-12');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (9,1,'2005-5-14');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (10,7,'2011-12-22');
INSERT INTO administer_medication(nurseID, patientID, administer_date) VALUES (7,4,'2014-6-21');
/*------------------------------------------------------------*/

