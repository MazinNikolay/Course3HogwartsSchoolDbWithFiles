alter table student add constraint age_constraint check (age>16)
alter table student add constraint name_unique_constraint unique (name)
alter table student alter column name set not null
alter table faculty add constraint name_color_constraint unique (color, name)
alter table student alter column age set default 20
