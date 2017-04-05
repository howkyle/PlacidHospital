/*select  from ((select id,first_name,last_name from doctor where category = 'intern') as interns join treatment on treatment.doctorID = interns.id;
*/
/*drop view if exists Interns;
create view Interns as select id,first_name,last_name from doctor where category = 'intern';
select max(Interns.id) from Interns join treatment on treatment.doctorID = Interns.id;*/
/*select max(count(select first_name, last_name from Interns join treatment on treatment.doctorID = Interns.id);*/

/*drop procedure if exists getBestIntern;
DELIMITER //
create procedure getBestIntern()
begin
drop view if exists Interns;
create view Interns as select id,first_name,last_name from doctor where category = 'intern';
select max(Interns.id) from Interns join treatment on treatment.doctorID = Interns.id;
end //
DELIMITER ;

CALL getBestIntern();*/

/*
drop procedure if exists getNurses;
DELIMITER //
create procedure getNurses(IN )
begin
drop view if exists Interns;
create view Interns as select id,first_name,last_name from doctor where category = 'intern';
select max(Interns.id) from Interns join treatment on treatment.doctorID = Interns.id;
end //
DELIMITER ;

CALL getNurses();*/
/*select first_name,last_name from doctor join intern on doctor.id = intern.doctorID;*/

drop view if exists Interns;
create view Interns as select id,first_name,last_name from doctor where category = 'intern';
select max(Interns.id) from Interns join treatment on treatment.doctorID = Interns.id;