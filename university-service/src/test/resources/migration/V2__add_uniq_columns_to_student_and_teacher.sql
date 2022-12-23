ALTER TABLE students
    ADD CONSTRAINT person_unique UNIQUE (firstname, lastname, birthday);
ALTER TABLE teachers
    ADD CONSTRAINT person_unique UNIQUE (firstname, lastname, birthday);


