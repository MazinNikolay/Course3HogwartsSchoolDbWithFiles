create table cars (
id integer primary key,
barnd text not null,
model text not null,
price integer not null)

create table people (
id integer primary key,
name text not null,
age integer not null,
driver bool default false,
car_id integer references cars (id))



