from app import db, connection


# connection = db.engine

# create doctor table
connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), password varchar(50),tel_number varchar(20));")

# create nurse table
sql = "CREATE TABLE nurse(id serial primary key, first_name varchar(50), last_name varchar(50),address varchar(100),tel_number varchar(20));"
connection.execute(sql)

# create secretary table

# inserting values into doctor
sql  = "INSERT INTO doctor(first_name, last_name,password,tel_number) VALUES ('James', 'Brown','password','555-5555');"
connection.execute(sql)