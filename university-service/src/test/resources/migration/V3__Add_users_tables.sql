CREATE TABLE users
(
    username  VARCHAR(36) NOT NULL PRIMARY KEY,
    password  VARCHAR(36) NOT NULL,
    person_id VARCHAR(36),
    role      VARCHAR(36)
);


