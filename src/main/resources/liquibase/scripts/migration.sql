-- liquibase formatted sql
-- changeset nmazin:1
create index students_name_idx on student (name);

-- changeset nmazin:2
create index faculty_nc_idx on faculty (name, color)