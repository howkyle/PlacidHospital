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
	if request.method =="GET":
		doc = json.load(request.data)
		result = connection.execute("Select password from doctor where id="+str(doc.id)+";")
		for row in result:
			password = row[0]
		
		if password == doc.password:
			return "{'result':'login success'}"
		else:
			return "{'result':'invalid password'}"





