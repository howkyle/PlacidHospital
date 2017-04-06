from app import app,db, connection
from flask import render_template, request, redirect, url_for, flash, jsonify
# from forms import *
# from models import*
import json


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







