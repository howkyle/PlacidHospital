import random
import names

nursePassword = 'nursepassword'
docPassword = 'docpassword'

streets = ['Maverly St', 'Brown St', 'Marine St', 'Fenton St', 'Fillo Ave', 'Qonti St', 'Deva Ave', 'Hilling Street']
cities = ['Kingston', 'MoBay', 'Rocky', 'Fort Dunn', 'Carton', 'Portmore', 'Stonehenge', 'Katon', 'Harlem']

def createPatients():
	file.write("/*-------------------------Patients----------------------------*/\n")

	for i in range(10):
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
	categories = ['consultant', 'residnet', 'intern']
	specializations = ['cardiology', 'dermatology', 'neurology', 'orthodontics', 'paediatrics', 'neonatology', 'radiotherapy', 'urology']

	file.write("/*-------------------------Doctors------------------------------*/\n")

	for i in range(10):
		f_name = names.get_first_name()
		l_name = names.get_last_name()

		cell = random.randint(6152525, 7935685)

		categoryID = random.randint(0, len(categories) - 1)

		category = categories[categoryID]

		if category == 'consultant':
			specID = random.randint(0, len(specializations) - 1)
			specialization = specializations[specID]
		else:
			specialization = ''


		insert = "'" + f_name + "','" + l_name + "','" + category + "','" + specialization + "','" +  docPassword + "','" + str(cell) + "'" 

		file.write("INSERT INTO doctor(first_name, last_name, category, specialization, password, tel_number) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")

def createNurses():
	categories = ['enrolled', 'registered', 'registered_midwife']

	file.write("/*-------------------------Nurses------------------------------*/\n")

	for i in range(10):
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

		file.write("INSERT INTO nurse(first_name, last_name, dob, address, tel_number, category, password) VALUES (" + insert + ");\n")

	file.write("/*------------------------------------------------------------*/\n\n")


if __name__ == "__main__":
	file = open("insert.sql", "w+")

	createPatients()

	createDoctors()

	createNurses()

	file.close()
