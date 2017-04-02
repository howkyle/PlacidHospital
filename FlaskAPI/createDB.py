from app import db, connection


# connection = db.engine


connection.execute("CREATE TABLE doctor(id SERIAL PRIMARY KEY ,first_name varchar(50),last_name varchar(50), password varchar(50),doctor_number varchar(50));")
sql  = "INSERT INTO doctor(first_name, last_name,password,doctor_number) VALUES ('James', 'Brown','password','555-5555');"
connection.execute(sql)