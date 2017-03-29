from app import app,db
from flask import render_template, request, redirect, url_for, flash, jsonify
# from forms import *
# from models import*
import json


@app.route("/")
def home():
	return render_template("home.html")



