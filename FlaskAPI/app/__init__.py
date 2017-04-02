from flask import Flask
from flask_sqlalchemy import SQLAlchemy 



DATABASE_URI="postgresql://placid:password@localhost/placid"


SECRET_KEY="I hope no one finds out this key, it would be dreadful"

app = Flask(__name__)
app.config['SECRET_KEY']=SECRET_KEY
app.config['SQLALCHEMY_DATABASE_URI'] = DATABASE_URI
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True # added just to suppress a warning

db = SQLAlchemy(app)
connection = db.engine

app.config.from_object(__name__)
from app import views

