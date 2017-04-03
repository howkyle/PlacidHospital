from app import app,db, connection
from flask import render_template, request, redirect, url_for, flash, jsonify
# from forms import *
# from models import*
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
			return "{'result':'login success','id':"+str(doc["id"])+"}"
		else:
			return "{'result':'invalid password'}"

@app.route("/nurse_login", methods=["POST"])
def nurseLogin():
	if request.method =="POST":
		nurse = request.json
		result = connection.execute("Select password from nurse where id="+str(nurse["id"])+";")
		for row in result:
			password = row[0]
		
		if password == nurse["password"]:
			return "{'result':'login success','id':"+str(nurse["id"])+"}"
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
			return "{'result':'login success','id':"+str(sec["id"])+"}"
		else:
			return "{'result':'invalid password'}"

app.route("/register_patient", methods =["POST"])
def registerPatient():
	if request.method == "POST":
		time_added = datetime.datetime.now()
		patient = request.jsonify
		sql = "insert into patient(first_name,last_name,address, dob, phone_number, date_added) values("+str(patient['first_name'])+","+str(patient['last_name'])+","+str(patient['address'])+","+str(patient['dob'])+","+str(patient['phone_number'])+","+str(patient['doctor_id'])+","+str(time_added)+");"
		try:
			connection.execute(sql)
		except:
			return "{'result':'unable to register patient'}"
		else:
			sql = "select * from patient where date_added ="+str(time_added)+";"
			result = connection.execute(sql)
			if result:
				for row in result:
					pid = row[0]
					print pid

				return {"first_name":str(patient['first_name']),"last_name":str(patient['last_name']),"address":str(patient['address']),"dob":str(patient['dob']),"phone_number":str(patient['phone_number']),"doctor_id":str(patient['doctor_id'])}







