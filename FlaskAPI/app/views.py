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
		result = connection.execute("Select password from doctor where id="+str(doc["id"])+";")
		for row in result:
			password = row[0]
		
		if password == doc["password"]:
			print "login successful"
			return "{'result':'login success','id':"+str(doc["id"])+"}"
		else:
			print "bad pass"
			return "{'result':'invalid password'}"

@app.route("/nurse_login", methods=["POST"])
def nurseLogin():
	if request.method =="POST":
		nurse = request.json
		result = connection.execute("Select password from nurse where id="+str(doc["id"])+",'first_name':"+str(doc["first_name"])+",'last_name':"+str(doc["last_name"])+";")
		for row in result:
			password = row[0]
		
		if password == nurse["password"]:
			return "{'result':'login success','id':"+str(nurse["id"])+",'first_name':"+str(nurse["first_name"])+",'last_name':"+str(nurse["last_name"])+"}"
		else:
			return "{'result':'invalid password'}"


@app.route("/secretary_login", methods = ["POST"])
def secretaryLogin():
	if request.method =="POST":
		sec = request.json
		result = connection.execute("Select password from secretary where id="+str(sec["id"])+";")
		for row in result:
			password = row[0]
		
		if password == sec["password"]:
			return "{'result':'login success','id':"+str(sec["id"])+",'first_name':"+str(sec["first_name"])+",'last_name':"+str(sec["last_name"])+"}"
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
			pDict["address"] = patient[3]
			pDict["dob"] = str(patient[4])
			pDict["tel_number"] = patient[5]

			patients.append(pDict)
			x = x+1
			print x
			print count
			if (count>0) and (x == count):
					print x
					break
				

		return json.dumps(patients, ensure_ascii=False)






