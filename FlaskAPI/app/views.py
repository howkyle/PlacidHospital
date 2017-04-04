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
			print "login successful"
			return "{'result':'login success','id':"+str(doc["id"])+"}"
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

@app.route("/register_patient", methods =["POST"])
def registerPatient():
	if request.method == "POST":
		print "got heeeerererere\n\n\n\n HEHEREHRERERER"
		time_added = datetime.datetime.now()
		patient = request.json
		sql = 'insert into patient(first_name,last_name,address,dob,tel_number) values(\''+str(patient["first_name"])+'\',\''+str(patient["last_name"])+'\',\''+str(patient["address"])+'\',\''+str(patient["dob"])+'\',\''+str(patient["tel_number"])+'\');'
		try:
			connection.execute(sql)
		except:
			return "{'result':'unable to register patient'}"
		else:
			print "registration successful"
			return "{ 'status' : 'success','first_name':"+"'"+str(patient["first_name"])+"','last_name':"+"'"+str(patient["last_name"])+"','address':"+"'"+str(patient["address"])+"','dob':"+"'"+str(patient["dob"])+"','phone_number':"+"'"+str(patient["tel_number"])+"'}"







