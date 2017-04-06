from app import app,db, connection
from flask import render_template, request, redirect, url_for, flash, jsonify
import json, datetime


@app.route("/")
def home():
	return render_template("home.html")

@app.route("/doctor_login", methods=["GET","POST"])
def doctorLogin():
	if request.method =="POST":
		doc = request.json
		print doc
		result = connection.execute("Select password, first_name, last_name from doctor where id="+str(doc["id"])+";")
		for row in result:
			password = row[0]
			first_name  = row[1]
			last_name = row[2]
		
		if password == doc["password"]:
			print "login successful"
			return "{'result':'login success','id':"+str(doc["id"])+",'first_name':'"+first_name+"','last_name':'"+last_name+"'}"
		else:
			return "{'result':'invalid password'}"

@app.route("/nurse_login", methods=["POST"])
def nurseLogin():
	if request.method =="POST":
		nurse = request.json
		result = connection.execute("Select password, first_name, last_name from nurse where id="+str(nurse["id"])+";")
		for row in result:
			password = row[0]
			first_name  = row[1]
			last_name = row[2]
		
		if password == nurse["password"]:
			return "{'result':'login success','id':"+str(nurse["id"])+",'first_name':'"+first_name+"','last_name':'"+last_name+"'}"
		else:
			return "{'result':'invalid password'}"


@app.route("/secretary_login", methods = ["POST"])
def secretaryLogin():
	if request.method =="POST":
		sec = request.json
		result = connection.execute("Select password,first_name, last_name from secretary where id="+str(sec["id"])+";")
		for row in result:
			password = row[0]
			first_name  = row[1]
			last_name = row[2]
		
		if password == sec["password"]:
			return "{'result':'login success','id':\'"+str(sec["id"])+"\','first_name':\'"+first_name+"\','last_name':\'"+last_name+"\'}"
		else:
			return "{'result':'invalid password'}"


@app.route("/register_patient", methods =["POST"])
def registerPatient():
	if request.method == "POST":
		patient = request.json
		sql = 'insert into patient(first_name,last_name,address,dob,tel_number) values(\''+str(patient["first_name"])+'\',\''+str(patient["last_name"])+'\',\''+str(patient["address"])+'\',\''+str(patient["dob"])+'\',\''+str(patient["tel_number"])+'\');'
		try:
			connection.execute(sql)
		except:
			return "{'result':'unable to register patient'}"
		else:
			return "{ 'status' : 'success','first_name':"+"'"+str(patient["first_name"])+"','last_name':"+"'"+str(patient["last_name"])+"','address':"+"'"+str(patient["address"])+"','dob':"+"'"+str(patient["dob"])+"','phone_number':"+"'"+str(patient["tel_number"])+"'}"



@app.route("/view_all_patients/<count>", methods =["GET"])
def viewPatients(count):
	if request.method == "GET":
		sql = "select * from patient;"
		result = connection.execute(sql)

		patients = []

		x = 0
		count = int(count)

		for patient in result:
			pDict = {}
			pDict["id"] = patient[0]
			pDict["first_name"] = patient[1]
			pDict["last_name"] = patient[2]
			pDict["tel_number"] = patient[3]
			pDict["dob"] = str(patient[4])
			pDict["address"] = patient[5]

			patients.append(pDict)
			x = x+1
			if (count>0) and (x == count):
					break
				
		return json.dumps(patients, ensure_ascii=False)



@app.route("/update_patient/<pid>", methods =["POST"])
def updatePatient(pid):
	if request.method == "POST":
		patient = request.json
		pid = int(pid)
		sql = "update patient set first_name =\'"+str(patient["first_name"])+"\', last_name = \'"+str(patient["last_name"])+"\', tel_number=\'"+str(patient["tel_number"])+"\',dob =\'"+str(patient["dob"])+"\',address=\'"+str(patient["address"])+"\' where id ="+str(pid)+";"
		
		try:
			print sql
			connection.execute(sql)
		except:
			return "{'status':'invalid'}"
		else:
			return "{'status':'success'}"

@app.route("/get_allergies/<pid>", methods = ["GET"])
def getAllergies(pid):

	if request.method == "GET":
		# pid = int(pid)
		sql = "select name from (medication inner join allergy on medication.id = allergy.medicationid) where patientid ="+str(pid)+";"
		
		try:
			allergies = []
			result = connection.execute(sql)
			print result
			for allergy in result:
				print allergy
				allergies.append(allergy[0])

		except:
			return "{'status':'invalid'}"
		else:
			return json.dumps(allergies, ensure_ascii=False)


@app.route("/get_patient_diagnosis", methods =["POST"])
def getDiagnoses():
	if request.method == "POST":
		data = request.json
		diagnosis = data["diagnosis"]
		start_date = data["start_date"]
		end_date = data["end_date"]

		sql = "select patient.id, first_name, last_name,tel_number,dob,address from (((patient inner join procedure on patient.id = procedure.patient) inner join test_results on procedure.id = test_results.procedure) inner join diagnosis on test_results.id = diagnosis.resultid) where  diagnosis_date between \'"+ str(start_date)+"\' and \'"+str(end_date)+"\' and diagnosis.conclusion =\'"+str(diagnosis)+"\';"
		result = connection.execute(sql)

		patients = []

		for patient in result:
			pDict = {}
			pDict["id"] = patient[0]
			pDict["first_name"] = patient[1]
			pDict["last_name"] = patient[2]
			pDict["tel_number"] = patient[3]
			pDict["dob"] = str(patient[4])
			pDict["address"] = patient[5]

			patients.append(pDict)

		return json.dumps(patients, ensure_ascii=False)
		

@app.route("/get_test_results/<pid>", methods = ["GET"])
def getTestResults(pid):
	if request.method == "GET":
		sql = "select results from test_results inner join procedure on test_results.procedure = procedure.id where procedure.patient ="+str(pid)+";"
		try :
			result = connection.execute(sql)
			test_results = []
			for row in result:
				test_results.append(row[0])

		except:
			return "{'status':'failure'}"
		else:
			return json.dumps(test_results, ensure_ascii=False)


@app.route("/get_medical_data/<pid>", methods = ["GET"])
def getMedicalData(pid):
	if request.method == "GET":
		sql = "select test, results, conclusion, medication.name from (((((medication inner join treatment_medication on medication.id = treatment_medication.medication) inner join treatment on treatment_medication.treatment = treatment.id) inner join diagnosis on treatment.diagnosisid = diagnosis.id) inner join test_results on diagnosis.resultid = test_results.id) inner join procedure on test_results.procedure = procedure.id) where procedure.patient = "+str(pid)+";"
		
		try:
			result = connection.execute(sql)
			medDict = {}
			med_data = []
			count = 0
			for row in result:
				count = count+1
				print count
				medDict["procedure"] = row[0]
				medDict["result"] = row[1]
				medDict["diagnosis"] = row[2]
				medDict["treatment"] = row[3]
				med_data.append(medDict)
		except:
			return "{'status':'failure'}"
		else:
			print len(med_data)
			return json.dumps(med_data, ensure_ascii=False)

@app.route("/add_medical_data/<pid>", methods=["POST"])
def addMedicalData(pid):
	if request.method == "POST":
		data = request.json 
		procedure = data["procedure"]
		test_result = data["result"]
		diagnosis = data["diagnosis"]
		treatment = data["treatment"]
		doctorid = data["doctor_id"]

		count = 0

		try:
			sql = "insert into procedure(test, patient) values(\'"+str(procedure)+"\',"+str(pid)+");"
			connection.execute(sql)

			sql = "select id from procedure order by id desc limit 1;"
			result = connection.execute(sql)
			for row in result:
				procedure_id = row[0]

			sql = "insert into test_results(results, procedure) values(\'"+str(test_result)+"\',"+str(procedure_id)+");"
			connection.execute(sql)

			sql = "select id from test_results order by id desc limit 1;"
			result = connection.execute(sql)
			for row in result:
				result_id = row[0]

			cur_date = '2005-5-4'
			sql = "insert into diagnosis(conclusion, diagnosis_date, resultid) values(\'"+str(diagnosis)+"\',\'"+str(cur_date)+"\',"+str(result_id)+");"
			connection.execute(sql)

			sql = "select id from diagnosis order by id desc limit 1;"
			result = connection.execute(sql)
			for row in result:
				diagnosis_id = row[0]

			sql = "insert into treatment(doctorid, patientid,treatment_date, diagnosisid) values("+str(doctorid)+","+str(pid)+",\'"+str(cur_date)+"\',"+str(diagnosis_id)+");"
			connection.execute(sql)
			sql = "select id from treatment order by id desc limit 1;"
			result = connection.execute(sql)
			for row in result:
				treatment_id = row[0]
				print treatment_id
				print treatment

			sql = "select id from medication where name =\'"+str(treatment)+"\';"
			result = connection.execute(sql)
			for row in result:
				med_id = row[0]

		
			sql = "insert into treatment_medication(medication, treatment) values("+str(med_id)+","+str(treatment_id)+");"
			connection.execute(sql)

			count= count + 1
			print count

		except:
			return "{'status':'failure'}"
		else:
			return "{'status':'success'}"






@app.route("/get_interns", methods =["GET"])
def getInterns():
	if request.method == "GET":
		sql = "select first_name, last_name from doctor where category = 'intern';"

		try:
			interns =[]
			result = connection.execute(sql)
			for doc in result:
				name = doc[0]+" "+doc[1]
				interns.append(name)
		except:
			return "{'status':'failure'}"
		else:
			return json.dumps(interns, ensure_ascii=False)


@app.route("/get_vital_signs/<pid>", methods = ["GET"])
def getVitalSigns(pid):
	if request.method =="GET":
		sql = "select sign from vital_signs where patientid ="+str(pid)+";"
		try:
			result = connection.execute(sql)
			signs = []

			for row in result:
				signs.append(row[0])
		except:
			return "{'status':'failure'}"
		else:
			return json.dumps(signs, ensure_ascii=False)

@app.route("/add_vital_sign/<pid>", methods =["POST"])
def addVitalSign(pid):
	if request.method == "POST":
		pass

		data = request.json
		nurse_id = data["nurse_id"]
		sign = data["vital"]

		sql = "insert into vital_signs(nurseID, sign, patientid) values ("+str(nurse_id)+",\'"+str(sign)+"\',"+str(pid)+";"

		try :
			connection.execute(sql)
		except:
			return "{'status':'failure'}"
		else:
			return "{'status':'success'}"



@app.route("/most_allergic", methods = ["GET"])
def getMostAllergic():
	if request.method == "GET":
		sql = "select "



@app.route("/nurse_admin_patient", methods = ["POST"])
def getNurseAdmin():
	if request.method == "POST":
		data  = request.json

		pid  = data["patient"]
		date = data["date"]

		sql = "select first_name, last_name from nurse inner join administer_medication on nurse.id = administer_medication.nurseid where administer_medication.patientid ="+str(pid)+" and administer_date = \'"+str(date)+"\';"

		try:
			names = []
			result = connection.execute(sql)
			for row in result:
				name = str(row[0])+ " "+str(row[1])
				names.append(names)

		except:
			return "{'status':'failure'}"
		else:
			return json.dumps(names, ensure_ascii=False)







# select test, results, conclusion, medication.name from ((((medication inner join treatment_medication on medication.id = treatment_medication.medication) inner join treatment on treatment_medication.treatment = treatment.id) inner join 


