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
			print "bad pass"
			return "{'result':'invalid password'}"

@app.route("/nurse_login", methods=["POST"])
def nurseLogin():
	if request.method =="POST":
		nurse = request.json
		result = connection.execute("Select password from nurse where id="+str(nurse["id"])+";")
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

		sql = "select * from (((patient inner join procedure on patient.id = procedure.patient) inner join test_results on procedure.id = test_results.procedure) inner join diagnosis on test_results.id = diagnosis.resultid) where  diagnosis_date between \'"+ str(start_date)+"\' and \'"+str(end_date)+"\';"
		result = connection.execute(sql)

		for patient in result:
			print patient[1]

		return "lol"


	




