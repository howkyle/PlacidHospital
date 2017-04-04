from app import db, connection

'''
# connection = db.engine

# create doctor table
connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), password varchar(50),tel_number varchar(20));")

# create nurse table
sql = "CREATE TABLE nurse(id serial primary key, first_name varchar(50), last_name varchar(50),address varchar(100),tel_number varchar(20));"
connection.execute(sql)

# create secretary table

# connection = db.engine

#connection.execute("DROP DATABASE [IF EXISTS] hospital;")
#connection.execute("CREATE DATABASE hospital;")
#connection.execute("USE hospital;")'''

connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), category varchar (30), specialization varchar(30), password varchar(50),tel_number varchar(50));")
connection.execute("CREATE TABLE patient(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), tel_number varchar(50), dob date, address varchar(30));")
connection.execute("CREATE TABLE nurse(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), dob date, address varchar(30), tel_number varchar(50), category varchar(20), password varchar(50));")
connection.execute("CREATE TABLE disease(id SERIAL PRIMARY KEY, disease_name varchar(40), type varchar (20));")
connection.execute("CREATE TABLE family_history(id SERIAL PRIMARY KEY, first_name varchar(50), last_name varchar(50), disease int references disease(id), patient int references patient(id));")
connection.execute("CREATE TABLE procedure(id SERIAL PRIMARY KEY, test varchar(50), patient int references patient(id), doctor int references doctor(id));")
connection.execute("CREATE TABLE test_results(id SERIAL PRIMARY KEY, symptoms varchar(50), procedure int references procedure(id));")
connection.execute("CREATE TABLE diagnosis(id SERIAL PRIMARY KEY, diagnosis_date date, disease int references disease(id), results int references test_results(id));")
connection.execute("CREATE TABLE treatment(id SERIAL PRIMARY KEY, treatment_date date, diagnosis int references diagnosis(id));")
connection.execute("CREATE TABLE medication(id SERIAL PRIMARY KEY, name varchar(50), dosage varchar (30));")
connection.execute("CREATE TABLE allergy(id SERIAL PRIMARY KEY, allergy_name varchar(50), medication int references medication(id));")

'''
# inserting values into doctor
sql  = "INSERT INTO doctor(first_name, last_name, category, specialization, password,doctor_number) VALUES ('James', 'Brown', 'consultant', 'password','555-5555');"
connection.execute(sql)

sql  = "INSERT INTO doctor(first_name, last_name, category, specialization, password,doctor_number) VALUES ('Michael', 'Frant', 'resident', 'password','112-5234');"
connection.execute(sql)

sql  = "INSERT INTO doctor(first_name, last_name, category, specialization, password,doctor_number) VALUES ('Carla', 'Brown', 'intern', 'password','445-5245');"
connection.execute(sql)

#Considering adding a foreign key for doctor ID in patient table
sql = "INSERT INTO patient(first_name, last_name, patient_number) VALUES ('John', 'Larrington', '444-2424');"
connection.execute(sql)

sql = "INSERT INTO nurse(first_name, last_name, dob, category, password VALUES ('Mary', 'Johnson', '1990-04-05','223-4242','enrolled','nursepassword');"
connection.execute(sql)

sql = "INSERT INTO nurse(first_name, last_name, dob, category, password VALUES ('Kim', 'Lee', '1987-02-01','453-4511','registered','nursepassword');"
connection.execute(sql)

sql = "INSERT INTO nurse(first_name, last_name, dob, category, password VALUES ('Liza', 'Harry', '1991-01-05','256-4342','registered_midwife','nursepassword');"
connection.execute(sql)

sql = "INSERT INTO disease(disease_name, type) VALUES ('Cholera','Water-borne');"
connection.execute(sql)

#Need foreign key for disease and patient id
sql = "INSERT INTO family_history(first_name, last_name) VALUES ('Kik', 'Anthony');"
connection.execute(sql)

#Need foreign key for patient id and doctor id
sql = "INSERT INTO procedure(test) VALUES ('blood test');"
connection.execute(sql)

#Need foreign key for procedure id
sql = "INSERT INTO test_results(symptoms) VALUES ('vomiting');"
connection.execute(sql)

#Need foreign key for disease id and test_results id
sql = "INSERT INTO diagnosis(cause_of_disease, diagnosis_date) VALUES ('contaminated water', '2017-03-20');"
connection.execute(sql)

#Need foreign key for diagnosis id
sql = "INSERT INTO treatment(treatment_date) VALUES ('2017-03-25');"
connection.execute(sql)

sql = "INSERT INTO medication(name, dosage) VALUES ('panadol', '20 tables');"
connection.execute(sql)

sql = "INSERT INTO allergy(allergy_name) VALUES ('none');"
connection.execute(sql)
'''