CREATE TABLE classrooms (
    id     VARCHAR(36) NOT NULL PRIMARY KEY,
    number INTEGER     NOT NULL
);
CREATE TABLE subjects (
    id   VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(36) NOT NULL
);
CREATE TABLE groups (
    id   VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(36) NOT NULL
);
CREATE TABLE students (
    id         VARCHAR(36)           NOT NULL PRIMARY KEY,
    firstname  VARCHAR(36)           NOT NULL,
    lastname   VARCHAR(36)           NOT NULL,
    birthday   TIMESTAMP             NOT NULL,
    group_id   VARCHAR(36) REFERENCES groups(id) ON DELETE SET NULL ON UPDATE CASCADE,
    course     INTEGER,
    is_captain BOOLEAN DEFAULT FALSE NOT NULL
);
CREATE TABLE teachers (
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    firstname  VARCHAR(36) NOT NULL,
    lastname   VARCHAR(36) NOT NULL,
    birthday   TIMESTAMP   NOT NULL,
    degree     VARCHAR(36),
    salary     INTEGER,
    first_date TIMESTAMP   NOT NULL,
    rank       VARCHAR(36),
    title      VARCHAR(36)
);
CREATE TABLE lessons (
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    subject    VARCHAR(36) NOT NULL REFERENCES subjects(id) ON DELETE CASCADE ON UPDATE CASCADE,
    classroom  VARCHAR(36) REFERENCES classrooms(id) ON DELETE SET NULL ON UPDATE CASCADE,
    number     INTEGER     NOT NULL,
    start_time TIME        NOT NULL,
    end_time   TIME        NOT NULL,
    teacher    VARCHAR(36) REFERENCES teachers(id) ON DELETE SET NULL ON UPDATE CASCADE
);
CREATE TABLE lessons_groups (
    lesson_id VARCHAR(36) NOT NULL REFERENCES lessons(id) ON DELETE SET NULL ON UPDATE CASCADE,
    group_id  VARCHAR(36) NOT NULL REFERENCES groups(id) ON DELETE SET NULL ON UPDATE CASCADE,
    UNIQUE (lesson_id, group_id)
);
CREATE TABLE day_schedule (
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    day_number INTEGER     NOT NULL
);
CREATE TABLE days_lessons (
    day_id    VARCHAR(36) NOT NULL REFERENCES day_schedule(id) ON DELETE SET NULL ON UPDATE CASCADE,
    lesson_id VARCHAR(36) NOT NULL REFERENCES lessons(id) ON DELETE SET NULL ON UPDATE CASCADE,
    UNIQUE (lesson_id, day_id)
);
CREATE TABLE schedule (
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    start_time TIMESTAMP   NOT NULL,
    end_time   TIMESTAMP   NOT NULL
);
CREATE TABLE weeks (
    id  VARCHAR(36) NOT NULL PRIMARY KEY,
    odd BOOLEAN     NOT NULL
);
CREATE TABLE schedule_weeks (
    schedule_id VARCHAR(36) NOT NULL REFERENCES schedule(id),
    week_id     VARCHAR(36) NOT NULL REFERENCES weeks(id),
    UNIQUE (schedule_id, week_id)
);
CREATE TABLE weeks_days (
    week_id VARCHAR(36) NOT NULL REFERENCES weeks(id) ON DELETE SET NULL ON UPDATE CASCADE,
    day_id  VARCHAR(36) NOT NULL REFERENCES day_schedule(id) ON DELETE SET NULL ON UPDATE CASCADE,
    UNIQUE (week_id, day_id)
);

