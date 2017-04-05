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
DROP TABLE IF EXISTS treatment_medication CASCADE;
DROP TABLE IF EXISTS diagnosis_disease CASCADE;
DROP TABLE IF EXISTS scan CASCADE;
DROP TABLE IF EXISTS result_scan CASCADE;

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
CREATE TABLE treatment(id SERIAL PRIMARY KEY, treatment_date date, diagnosis int references diagnosis(id));
CREATE TABLE medication(id SERIAL PRIMARY KEY, name varchar(50));
CREATE TABLE allergy(id SERIAL PRIMARY KEY, patientID int references patient(id), medicationID int references medication(id));
CREATE TABLE diagnosis_disease(id SERIAL PRIMARY KEY, diseaseID int references disease(id), diagnosisID int references diagnosis(id));
CREATE TABLE treatment_medication(id SERIAL PRIMARY KEY, medication int references medication(id), treatment int references treatment(id));
CREATE TABLE scan(id SERIAL PRIMARY KEY, scanImg varchar(100));
CREATE TABLE result_scan(id SERIAL PRIMARY KEY, resultID int references test_results(id), scanID int references scan(id));
/*-----------------------------------------------------------------*/

/*-------------------------Secretaries----------------------------*/
INSERT INTO secretary(first_name, last_name, password) VALUES ('Rose','Neff','secpassword');
/*------------------------------------------------------------*/

/*-------------------------Patients----------------------------*/
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Larry','Matthews','5717853','1982-11-4','362 Maverly St Rocky');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Kelly','Musick','5656873','1973-6-15','473 Hilling Street Carton');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Roberta','Cockfield','3568290','1968-4-14','548 Deva Ave Stonehenge');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Benny','Harmon','3681000','1962-8-28','159 Hilling Street MoBay');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Cleo','Caporiccio','3991694','1965-5-27','213 Brown St MoBay');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Kevin','Arnold','5659790','1965-2-28','272 Maverly St Harlem');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Yong','Rowton','4149348','1954-11-22','638 Hilling Street Stonehenge');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Carol','Thomas','3625888','1956-9-16','480 Fillo Ave Kingston');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Cynthia','Goddard','5046304','1981-3-26','446 Fenton St Carton');
INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES ('Florence','Nicolls','4517748','1996-7-5','290 Qonti St Harlem');
/*------------------------------------------------------------*/

/*-------------------------Doctors------------------------------*/
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Gloria','Ainsworth','resident','docpassword','6367455');
INSERT INTO resident(id) VALUES (1);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Gregory','Mair','resident','docpassword','6485880');
INSERT INTO resident(id) VALUES (2);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Audrey','Mitchell','intern','docpassword','7762930');
INSERT INTO intern(id) VALUES (3);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Jim','Longo','intern','docpassword','6550299');
INSERT INTO intern(id) VALUES (4);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Irma','Shabel','intern','docpassword','6428881');
INSERT INTO intern(id) VALUES (5);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Mildred','Flores','consultant','docpassword','6681639');
INSERT INTO consultant(id, specialization) VALUES (6,'cardiology');
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Rose','Wood','consultant','docpassword','7642583');
INSERT INTO consultant(id, specialization) VALUES (7,'urology');
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Faith','Ernest','resident','docpassword','7511169');
INSERT INTO resident(id) VALUES (8);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Clarence','Wilkinson','resident','docpassword','6427580');
INSERT INTO resident(id) VALUES (9);
INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES ('Maryanna','Joyner','resident','docpassword','6224941');
INSERT INTO resident(id) VALUES (10);
/*------------------------------------------------------------*/

/*-------------------------Nurses------------------------------*/
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Robert','Blatt','1967-5-14','426 Qonti St Kingston','7217723','registered','nursepassword');
INSERT INTO registered(id) VALUES (1);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Madeline','Bender','1951-4-27','230 Qonti St MoBay','6529501','registered_midwife','nursepassword');
INSERT INTO registered_midwife(id) VALUES (2);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Kenneth','Powers','1956-11-25','820 Fillo Ave Fort Dunn','7787440','registered','nursepassword');
INSERT INTO registered(id) VALUES (3);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Lindsay','Grigsby','1972-4-14','56 Deva Ave Kingston','7804231','registered_midwife','nursepassword');
INSERT INTO registered_midwife(id) VALUES (4);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Annie','King','1957-4-11','891 Deva Ave Portmore','6711640','registered_midwife','nursepassword');
INSERT INTO registered_midwife(id) VALUES (5);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Linda','Mcclain','1961-6-8','381 Marine St Fort Dunn','7399139','registered','nursepassword');
INSERT INTO registered(id) VALUES (6);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Jewell','Munoz','1961-6-20','426 Deva Ave Fort Dunn','6241658','registered','nursepassword');
INSERT INTO registered(id) VALUES (7);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Phyllis','Baldwin','1972-11-10','669 Brown St Kingston','6252362','enrolled','nursepassword');
INSERT INTO enrolled(id) VALUES (8);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Alfonso','Sims','1955-1-9','982 Brown St Harlem','7377398','registered','nursepassword');
INSERT INTO registered(id) VALUES (9);
INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES ('Arlene','Giacchino','1961-5-23','610 Qonti St Harlem','7517117','registered','nursepassword');
INSERT INTO registered(id) VALUES (10);
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
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Charlie','Jaffe',8,6);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Juan','Batchelder',9,10);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('David','Hart',6,10);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Lorraine','Trujillo',8,7);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('David','Marshall',5,8);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Chester','Pilcher',2,6);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Matthew','Matthews',13,8);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Bertha','Patterson',2,9);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Heidi','Calhoun',3,2);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Grace','Frandsen',11,9);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('William','Quinones',5,6);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Scott','Scott',6,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Randi','Gonzales',2,9);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('James','Koonce',13,10);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Eugene','Huskin',8,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Elaine','Byrd',2,3);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('David','Kauffman',3,10);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Charley','Earnest',2,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Charles','Mcfadden',3,1);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Michael','Wagner',2,3);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Francisco','Reeves',2,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Ronald','Wiley',11,9);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Maria','Vega',4,1);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Jose','Mills',1,7);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Edwin','Lynch',9,5);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Alphonse','Johnson',6,2);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Audrey','Chadderton',8,10);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Wendy','Richardson',5,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Douglas','Paul',11,4);
INSERT INTO family_history(first_name,last_name,disease,patient) VALUES ('Scott','Mapes',3,8);
/*------------------------------------------------------------*/

/*-------------------------Procedures------------------------------*/
INSERT INTO procedure(test, patient) VALUES ('western blot',4);
INSERT INTO procedure(test, patient) VALUES ('western blot',1);
INSERT INTO procedure(test, patient) VALUES ('western blot',9);
INSERT INTO procedure(test, patient) VALUES ('intradermal test',4);
INSERT INTO procedure(test, patient) VALUES ('intradermal test',2);
INSERT INTO procedure(test, patient) VALUES ('tb skin test',10);
INSERT INTO procedure(test, patient) VALUES ('intradermal test',5);
INSERT INTO procedure(test, patient) VALUES ('intradermal test',9);
INSERT INTO procedure(test, patient) VALUES ('skin patch test',5);
INSERT INTO procedure(test, patient) VALUES ('tb skin test',2);
/*------------------------------------------------------------*/

/*-------------------------Test Results------------------------------*/
INSERT INTO test_results(results, procedure) VALUES ('mild concern',1);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',2);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',3);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',4);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',5);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',6);
INSERT INTO test_results(results, procedure) VALUES ('mild concern',7);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',8);
INSERT INTO test_results(results, procedure) VALUES ('serious concern',9);
INSERT INTO test_results(results, procedure) VALUES ('moderate concern',10);
/*------------------------------------------------------------*/

/*---------------------------Diagnoses------------------------------*/
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2016-6-5',1);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2001-2-3',2);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2000-4-18',3);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2012-4-23',4);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2015-7-20',5);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2002-11-12',6);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2003-11-13',7);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2001-4-27',8);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2010-5-20',9);
INSERT INTO diagnosis(diagnosis_date,resultID) VALUES ('2006-11-22',10);
/*------------------------------------------------------------*/

/*---------------------------Treatments------------------------------*/
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2017-6-5',1);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2002-2-3',2);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2001-4-18',3);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2013-4-23',4);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2016-7-20',5);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2003-11-12',6);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2004-11-13',7);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2002-4-27',8);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2011-5-20',9);
INSERT INTO treatment(treatment_date, diagnosis) VALUES ('2007-11-22',10);
/*------------------------------------------------------------*/

/*---------------------------Diagnosis Diseases------------------------------*/
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (7,1);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (13,2);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (11,3);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (4,4);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (6,5);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (10,6);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (7,7);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (5,8);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (7,9);
INSERT INTO diagnosis_disease(diseaseID, diagnosisID) VALUES (5,10);
/*------------------------------------------------------------*/

/*---------------------------Treatment Medication------------------------------*/
INSERT INTO treatment_medication(medication, treatment) VALUES (3,1);
INSERT INTO treatment_medication(medication, treatment) VALUES (2,2);
INSERT INTO treatment_medication(medication, treatment) VALUES (3,3);
INSERT INTO treatment_medication(medication, treatment) VALUES (3,4);
INSERT INTO treatment_medication(medication, treatment) VALUES (2,5);
INSERT INTO treatment_medication(medication, treatment) VALUES (3,6);
INSERT INTO treatment_medication(medication, treatment) VALUES (5,7);
INSERT INTO treatment_medication(medication, treatment) VALUES (2,8);
INSERT INTO treatment_medication(medication, treatment) VALUES (4,9);
INSERT INTO treatment_medication(medication, treatment) VALUES (4,10);
/*------------------------------------------------------------*/

/*---------------------------Allergies------------------------------*/
INSERT INTO allergy(patientID, medicationID) VALUES (9,5);
INSERT INTO allergy(patientID, medicationID) VALUES (2,3);
INSERT INTO allergy(patientID, medicationID) VALUES (2,2);
INSERT INTO allergy(patientID, medicationID) VALUES (6,5);
INSERT INTO allergy(patientID, medicationID) VALUES (1,2);
INSERT INTO allergy(patientID, medicationID) VALUES (8,1);
INSERT INTO allergy(patientID, medicationID) VALUES (2,1);
INSERT INTO allergy(patientID, medicationID) VALUES (8,3);
INSERT INTO allergy(patientID, medicationID) VALUES (6,3);
INSERT INTO allergy(patientID, medicationID) VALUES (10,2);
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
INSERT INTO result_scan(resultID, scanID) VALUES (2,1);
INSERT INTO result_scan(resultID, scanID) VALUES (4,2);
INSERT INTO result_scan(resultID, scanID) VALUES (4,3);
INSERT INTO result_scan(resultID, scanID) VALUES (3,4);
INSERT INTO result_scan(resultID, scanID) VALUES (6,5);
INSERT INTO result_scan(resultID, scanID) VALUES (8,6);
INSERT INTO result_scan(resultID, scanID) VALUES (7,7);
INSERT INTO result_scan(resultID, scanID) VALUES (9,8);
INSERT INTO result_scan(resultID, scanID) VALUES (9,9);
INSERT INTO result_scan(resultID, scanID) VALUES (9,10);
/*------------------------------------------------------------*/

