import random
import names

nursePassword = 'nursepassword'
docPassword = 'docpassword'

streets = ['Maverly St', 'Brown St', 'Marine St', 'Fenton St', 'Fillo Ave', 'Qonti St', 'Deva Ave', 'Hilling Street']
cities = ['Kingston', 'MoBay', 'Rocky', 'Fort Dunn', 'Carton', 'Portmore', 'Stonehenge', 'Katon', 'Harlem']

numSecretaties = 1;
numPatients = 10;
numNurses = 10;
numDoctors = 10;
famHistorySize = 30;
numProcedures = 10;
numAllergies = 10;

diseases = ['cholera', 'flu', 'coronary artery disease', 'diabetes A', 'diabetes B', 'gastroesophageal reflux disease', 'alzheimers disease', 'asthma', 'autism', 'brain cancer', 'bone cancer', 'breast cancer', 'celiac disease']

procedures = ['skin prick test', 'skin patch test', 'blood test', 'intradermal test', 'western blot', 'tb skin test']

results = ['no issue', 'mild concern', 'moderate concern', 'serious concern']

medications = ['glucophage', 'hydrochlorothiazide', 'azithromycin', 'zocor', 'hydrocodone']

def createTables():
	file.write("/*--------------------------Create Tables-----------------------------*/\n")

	insert = "DROP TABLE IF EXISTS doctor CASCADE;\n"
	insert += "DROP TABLE IF EXISTS  consultant CASCADE;\n"
	insert += "DROP TABLE IF EXISTS intern CASCADE;\n"
	insert += "DROP TABLE IF EXISTS resident CASCADE;\n"
	insert += "DROP TABLE IF EXISTS patient CASCADE;\n"
	insert += "DROP TABLE IF EXISTS nurse CASCADE;\n"
	insert += "DROP TABLE IF EXISTS registered CASCADE;\n"
	insert += "DROP TABLE IF EXISTS registered_midwife CASCADE;\n"
	insert += "DROP TABLE IF EXISTS enrolled CASCADE;\n"
	insert += "DROP TABLE IF EXISTS disease CASCADE;\n"
	insert += "DROP TABLE IF EXISTS family_history CASCADE;\n"
	insert += "DROP TABLE IF EXISTS procedure CASCADE;\n"
	insert += "DROP TABLE IF EXISTS test_results CASCADE;\n"
	insert += "DROP TABLE IF EXISTS diagnosis CASCADE;\n"
	insert += "DROP TABLE IF EXISTS treatment CASCADE;\n"
	insert += "DROP TABLE IF EXISTS medication CASCADE;\n"
	insert += "DROP TABLE IF EXISTS allergy CASCADE;\n"
	insert += "DROP TABLE IF EXISTS secretary CASCADE;\n"
	insert += "DROP TABLE IF EXISTS treatment_medication CASCADE;\n"
	insert += "\n"

	insert += "CREATE TABLE secretary(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50),password varchar(30));\n"
	insert += "CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), category varchar (30), password varchar(50),tel_number varchar(50));\n"
	insert += "CREATE TABLE consultant(id SERIAL PRIMARY KEY, doctorID int references doctor(id), specialization varchar(30));\n"
	insert += "CREATE TABLE intern(id SERIAL PRIMARY KEY, doctorID int references doctor(id));\n"
	insert += "CREATE TABLE resident(id SERIAL PRIMARY KEY, doctorID int references doctor(id));\n"

	insert += "CREATE TABLE patient(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), tel_number varchar(50), dob date, address varchar(30));\n"

	insert += "CREATE TABLE nurse(id SERIAL PRIMARY KEY, first_name varchar(50),last_name varchar(50), dob date, address varchar(30), tel_number varchar(50), category varchar(20), password varchar(50));\n"
	insert += "CREATE TABLE registered(id SERIAL PRIMARY KEY, nurseID int references nurse(id));\n"
	insert += "CREATE TABLE registered_midwife(id SERIAL PRIMARY KEY, nurseID int references nurse(id));\n"
	insert += "CREATE TABLE enrolled(id SERIAL PRIMARY KEY, nurseID int references nurse(id));\n"

	insert += "CREATE TABLE disease(id SERIAL PRIMARY KEY, disease_name varchar(40));\n"
	insert += "CREATE TABLE family_history(id SERIAL PRIMARY KEY, first_name varchar(50), last_name varchar(50), disease int references disease(id), patient int references patient(id));\n"
	insert += "CREATE TABLE procedure(id SERIAL PRIMARY KEY, test varchar(50), patient int references patient(id));\n"
	insert += "CREATE TABLE test_results(id SERIAL PRIMARY KEY, symptoms varchar(50), procedure int references procedure(id));\n"
	insert += "CREATE TABLE diagnosis(id SERIAL PRIMARY KEY, diagnosis_date date, disease int references disease(id), results int references test_results(id));\n"
	insert += "CREATE TABLE treatment(id SERIAL PRIMARY KEY, treatment_date date, diagnosis int references diagnosis(id));\n"
	insert += "CREATE TABLE medication(id SERIAL PRIMARY KEY, name varchar(50));\n"
	insert += "CREATE TABLE allergy(id SERIAL PRIMARY KEY, patientID int references patient(id), medicationID int references medication(id));\n"
	insert += "CREATE TABLE treatment_medication(id SERIAL PRIMARY KEY, medication int references medication(id), treatment int references treatment(id));\n"
	
	#insert += "CREATE TABLE allergy(id SERIAL PRIMARY KEY, allergy_name varchar(50));\n"

	file.write(insert)

	file.write("/*-----------------------------------------------------------------*/\n\n")

def createSecretaries():
	file.write("/*-------------------------Secretaries----------------------------*/\n")

	for i in range(numSecretaties):
		f_name = names.get_first_name()
		l_name = names.get_last_name()

		password = 'secpassword'

		insert = "'" + f_name + "','" + l_name + "','" +  password + "'"

		file.write("INSERT INTO secretary(first_name, last_name, password) VALUES (" + insert + ");\n")


	file.write("/*------------------------------------------------------------*/\n\n")

def createPatients():
	file.write("/*-------------------------Patients----------------------------*/\n")

	for i in range(numPatients):
		f_name = names.get_first_name()
		l_name = names.get_last_name()

		year = random.randint(1950, 2000)
		month = random.randint(1, 12)
		day = random.randint(1, 28)
		dob = str(year) + "-" + str(month) + "-" + str(day)

		cell = random.randint(2152525, 5935685)

		lotNum = random.randint(1, 999)
		streetID = random.randint(0,len(streets) - 1)
		cityID = random.randint(0, len(cities) - 1)
		address = str(lotNum) + " " + streets[streetID] + " " + cities[cityID]

		insert = "'" + f_name + "','" + l_name + "','" +  str(cell) + "','" +  dob + "','" +  address + "'" 

		file.write("INSERT INTO patient(first_name, last_name, tel_number, dob, address) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createDoctors():
	categories = ['consultant', 'resident', 'intern']
	specializations = ['cardiology', 'dermatology', 'neurology', 'orthodontics', 'paediatrics', 'neonatology', 'radiotherapy', 'urology']

	file.write("/*-------------------------Doctors------------------------------*/\n")

	for i in range(numDoctors):
		f_name = names.get_first_name()
		l_name = names.get_last_name()

		cell = random.randint(6152525, 7935685)

		categoryID = random.randint(0, len(categories) - 1)

		category = categories[categoryID]

		if category == 'consultant':
			specID = random.randint(0, len(specializations) - 1)
			specialization = specializations[specID]

		insert = "'" + f_name + "','" + l_name + "','" + category + "','" +  docPassword + "','" + str(cell) + "'"

		file.write("INSERT INTO doctor(first_name, last_name, category, password, tel_number) VALUES (" + insert + ");\n")
		

		if category == 'consultant': 
			ISAInsert = str(i + 1) + ",'" + specialization + "'"
			file.write("INSERT INTO " + category + "(id, specialization) VALUES (" + ISAInsert + ");\n")
		else:
			ISAInsert = str(i + 1) 
			file.write("INSERT INTO " + category + "(id) VALUES (" + ISAInsert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createNurses():
	categories = ['enrolled', 'registered', 'registered_midwife']

	file.write("/*-------------------------Nurses------------------------------*/\n")

	for i in range(numNurses):
		f_name = names.get_first_name()
		l_name = names.get_last_name()

		year = random.randint(1950, 2000)
		month = random.randint(1, 12)
		day = random.randint(1, 28)
		dob = str(year) + "-" + str(month) + "-" + str(day)

		lotNum = random.randint(1, 999)
		streetID = random.randint(0,len(streets) - 1)
		cityID = random.randint(0, len(cities) - 1)
		address = str(lotNum) + " " + streets[streetID] + " " + cities[cityID]

		cell = random.randint(6152525, 7935685)

		categoryID = random.randint(0, len(categories) - 1)

		category = categories[categoryID]

		insert = "'" + f_name + "','" + l_name + "','" +  dob + "','" +  address + "','" +  str(cell) + "','" +  category + "','" +  nursePassword + "'" 
		ISAInsert = str(i + 1)

		file.write("INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES (" + insert + ");\n")
		file.write("INSERT INTO " + category + "(id) VALUES (" + ISAInsert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createDiseases():
	file.write("/*-------------------------Diseases------------------------------*/\n")

	for i in range(len(diseases)):
		file.write("INSERT INTO disease(disease_name) VALUES ('" + diseases[i] + "');\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createMedications():
	file.write("/*-------------------------Medications------------------------------*/\n")

	for i in range(len(medications)):
		file.write("INSERT INTO medication(name) VALUES ('" + medications[i] + "');\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createFamilyHistory():
	file.write("/*-------------------------Fam History------------------------------*/\n")

	for i in range(famHistorySize):
		f_name = names.get_first_name()
		l_name = names.get_last_name()

		disease = random.randint(1, len(diseases))

		patient = random.randint(1, numPatients)

		insert = "'" + f_name + "','" + l_name + "'," + str(disease) + "," + str(patient)

		file.write("INSERT INTO family_history(first_name,last_name,disease,patient) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createProcedures():
	procedureIDs = []

	file.write("/*-------------------------Procedures------------------------------*/\n")

	for i in range(numProcedures):
		procedureID = random.randint(0, len(procedures) - 1)
		procedureIDs.append(procedureID)

		patientID = random.randint(1, numPatients)

		insert = "'" + procedures[procedureID] + "'," + str(patientID)

		file.write("INSERT INTO procedure(test, patient) VALUES (" + insert + ");\n")


	file.write("/*------------------------------------------------------------*/\n\n")

def createTestResults():
	file.write("/*-------------------------Test Results------------------------------*/\n")

	for i in range(numProcedures):
		resultIndex = random.randint(0, len(results) - 1)

		insert = "'" + results[resultIndex] + "'," + str(i + 1)

		file.write("INSERT INTO test_results(results, procedure) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createDiagnoses():
	file.write("/*---------------------------Diagnoses------------------------------*/\n")

	treatmentDates = []

	for i in range(numProcedures):
		year = random.randint(2000, 2017)
		month = random.randint(1, 12)
		day = random.randint(1, 28)
		date = str(year) + "-" + str(month) + "-" + str(day)

		treatmentDates.append(str(year + 1) + "-" + str(month) + "-" + str(day))

		diseaseIndex = random.randint(0, len(diseases) - 1)

		insert = "'" + date + "','" + diseases[diseaseIndex] + "'," + str(i+1)

		file.write("INSERT INTO diagnosis(diagnosis_date, disease, results) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

	createTreatments(treatmentDates)

def createTreatments(dates):
	file.write("/*---------------------------Treatments------------------------------*/\n")

	for i in range(len(dates)):
		

		insert = "'" + dates[i] + "'," + str(i + 1)

		file.write("INSERT INTO treatment(treatment_date, diagnosis) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createTreatmentMedications():
	file.write("/*---------------------------Treatment Medication------------------------------*/\n")

	for i in range(numProcedures):
		medicineIndex = random.randint(1, len(medications))

		insert = str(medicineIndex) + "," + str(i + 1)

		file.write("INSERT INTO treatment_medication(medication, treatment) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createAllergies():
	file.write("/*---------------------------Allergies------------------------------*/\n")

	for i in range(numAllergies):
		patientIndex = random.randint(1, numPatients)

		medicineIndex = random.randint(1, len(medications))

		insert = str(patientIndex) + "," + str(medicineIndex)

		file.write("INSERT INTO allergy(patientID, medicationID) VALUES (" + insert + ");\n")


	file.write("/*------------------------------------------------------------*/\n\n")



if __name__ == "__main__":
	file = open("insert.sql", "w+")

	createTables()

	createSecretaries()

	createPatients()

	createDoctors()

	createNurses()

	createDiseases()

	createMedications()

	createFamilyHistory()

	createProcedures()

	createTestResults()

	createDiagnoses()

	createTreatmentMedications()

	createAllergies()

	file.close()
