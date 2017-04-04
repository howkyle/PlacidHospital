import random
import names

streets = ['Maverly St', 'Brown St', 'Marine St', 'Fenton St', 'Fillo Ave', 'Qonti St', 'Deva Ave']
cities = ['Kingston', 'MoBay', 'Rocky', 'Fort Dunn', 'Carton', 'Portmore', 'Stonehenge', 'Katon']


def createPatients():
	file = open("insert_patients.sql", "w+")

	for i in range(500000):
		f_name = names.get_first_name()
		l_name = names.get_first_name()

		year = random.randint(1950, 2000)
		month = random.randint(1, 12)
		day = random.randint(1, 28)
		dob = str(year) + "-" + str(month) + "-" + str(day)

		cell = random.randint(2552525, 9535685)

		lotNum = random.randint(1, 999)
		streetID = random.randint(0,len(streets) - 1)
		cityID = random.randint(0, len(cities) - 1)
		address = str(lotNum) + " " + streets[streetID] + " " + cities[cityID]

		insert = "'" + f_name + "','" + l_name + "','" +  str(cell) + "','" +  dob + "','" +  address + "'" 

		file.write("INSERT INTO patient(first_name, last_name, patient_number, dob, address) VALUES (" + insert + ");\n")


	file.close()


if __name__ == "__main__":
	#createPatients()
