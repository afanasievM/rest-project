ALTER TABLE students
    ADD CONSTRAINT student_unique UNIQUE (firstname, lastname, birthday);
ALTER TABLE teachers
    ADD CONSTRAINT teacher_unique UNIQUE (firstname, lastname, birthday);


