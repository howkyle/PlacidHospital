from app import db, connection


# connection = db.engine


connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), category varchar(20), specialization varchar(30), password varchar(50),doctor_number varchar(50));")
sql  = "INSERT INTO doctor(first_name, last_name, category, specializatoin, password,doctor_number) VALUES ('James', 'Brown', 'consultant', '', 'password','555-5555');"
connection.execute(sql)

connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), category varchar(20), specialization varchar(30), password varchar(50),doctor_number varchar(50));")
sql  = "INSERT INTO doctor(first_name, last_name, category, specializatoin, password,doctor_number) VALUES ('Michael', 'Frant', 'resident', '', 'password','112-5234');"
connection.execute(sql)

connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), category varchar(20), specialization varchar(30), password varchar(50),doctor_number varchar(50));")
sql  = "INSERT INTO doctor(first_name, last_name, category, specializatoin, password,doctor_number) VALUES ('Carla', 'Brown', 'intern', '', 'password','445-5245');"
connection.execute(sql)

#Considering adding a foreign key for doctor ID in patient table
connection.execute("CREATE TABLE patient(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), patient_number varchar(50));")
sql = "INSERT INTO patient(first_name, last_name, patient_number) VALUES ('John', 'Larrington', '444-2424');"
connection.execute(sql)

connection.execute("CREATE TABLE nurse(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), dob date, nurse_number varchar(50), category varchar(20), password varchar(50));")
sql = "INSERT INTO nurse(first_name, last_name, dob, category, password VALUES ('Mary', 'Johnson', '1990-04-05','223-4242','enrolled','nursepassword');"
connection.execute(sql)

connection.execute("CREATE TABLE nurse(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), dob date, nurse_number varchar(50), category varchar(20), password varchar(50));")
sql = "INSERT INTO nurse(first_name, last_name, dob, category, password VALUES ('Kim', 'Lee', '1987-02-01','453-4511','registered','nursepassword');"
connection.execute(sql)

connection.execute("CREATE TABLE nurse(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), dob date, nurse_number varchar(50), category varchar(20), password varchar(50));")
sql = "INSERT INTO nurse(first_name, last_name, dob, category, password VALUES ('Liza', 'Harry', '1991-01-05','256-4342','registered_midwife','nursepassword');"
connection.execute(sql)

connection.execute("CREATE TABLE disease(id SERIAL PRIMARY KEY, disease_name varchar(40), type varchar (20));")
sql = "INSERT INTO disease(disease_name, type) VALUES ('Cholera','Water-borne');"
connection.execute(sql)

#Need foreign key for disease and patient id
connection.execute("CREATE TABLE family_history(id SERIAL PRIMARY KEY, first_name varchar(50), last_name varchar(50));")
sql = "INSERT INTO family_history(first_name, last_name) VALUES ('Kik', 'Anthony');"
connection.execute(sql)

#Need foreign key for patient id and doctor id
connection.execute("CREATE TABLE procedure(id SERIAL PRIMARY KEY, test varchar(50));")
sql = "INSERT INTO procedure(test) VALUES ('blood test');"
connection.execute(sql)

#Need foreign key for procedure id
connection.execute("CREATE TABLE test_results(id SERIAL PRIMARY KEY, symptoms varchar(50));")
sql = "INSERT INTO test_results(symptoms) VALUES ('vomiting');"
connection.execute(sql)

#Need foreign key for test_results id
connection.execute("CREATE TABLE scans(id SERIAL PRIMARY KEY, scanImg varchar(50));")
sql = "INSERT INTO scans(scanImg) VALUES ('image');"
connection.execute(sql)

#Need foreign key for disease id and test_results id
connection.execute("CREATE TABLE diagnosis(id SERIAL PRIMARY KEY, cause_of_disease varchar(50), diagnosis_date date);")
sql = "INSERT INTO diagnosis(cause_of_disease, diagnosis_date) VALUES ('contaminated water', '2017-03-20');"
connection.execute(sql)

#Need foreign key for diagnosis id
connection.execute("CREATE TABLE treatment(id SERIAL PRIMARY KEY, treatment_date date);")
sql = "INSERT INTO treatment(treatment_date) VALUES ('2017-03-25');"
connection.execute(sql)

connection.execute("CREATE TABLE medication(id SERIAL PRIMARY KEY, name varchar(50), dosage varchar (30));")
sql = "INSERT INTO medication(name, dosage) VALUES ('panadol', '20 tables');"
connection.execute(sql)

connection.execute("CREATE TABLE allergy(id SERIAL PRIMARY KEY, allergy_name varchar(50));")
sql = "INSERT INTO allergy(allergy_name) VALUES ('none');"
connection.execute(sql)