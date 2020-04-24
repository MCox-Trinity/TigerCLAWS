

CREATE TABLE course (
	id SERIAL PRIMARY KEY, 
    title VARCHAR(100) NOT NULL,
    credit_hour int4 Not NULL,
    academic_level int4 NOT NULL,
    course_number char(4) NOT NULL,
    -- department_id int4 NOT NULL REFERENCES deparment(id) ON DELETE CASCADE,
    department char(4) NOT NULL,
    capacity int NOT NULL,
    start_time TIME NOT NUll,
    end_time TIME NOT NUll,
    day_of_week varchar(20) NOT NULL,
    location varchar(20) NOT NULL,
    prof_first VARCHAR(20) NOT NULL,
    prof_last VARCHAR(20) NOT NULL,
    section VARCHAR(2) Not NULL,
    enrolled int Not Null
);

-----------------  For student and registration 

-- CREATE TABLE student (
-- 	id SERIAL PRIMARY KEY, 
-- 	username varchar(20) NOT NULL, 
-- 	password varchar(200) NOT NULL,
--     first_name varchar(20) NOT NULL,
--     middle_name varchar(20) NOT NULL,
--     last_name varchar(20) NOT NULL,
--     email varchar(20) NOT NULL,
--     deleted BOOLEAN NOT NULL
-- );


-- CREATE TABLE grade(
--     id SERIAL PRIMARY KEY,
--     letter_grade varchar(10) NOT NULL,
--     notes varchar(100),
-- );

-- CREATE TABLE registration (
-- 	id SERIAL PRIMARY KEY, 
--     student_id int4 Not NULL REFERENCES student(id) ON DELETE CASCADE,
--     courseSection_id int4 Not NULL REFERENCES courseSection(id) ON DELETE CASCADE,
--     semester_id int4 Not NULL REFERENCES semester(id) ON DELETE CASCADE,
--     grade_id int4 Not NULL REFERENCES grade(id) ON DELETE CASCADE,
-- );

-- CREATE TABLE status (
-- 	id SERIAL PRIMARY KEY, 
--     code varchar(20) NOT NULL,
--     description varchar(50) NOT NULL,
--     section_id int4 Not NULL REFERENCES section(id) ON DELETE CASCADE,
-- );

-----------------------------------------------





-- CREATE TABLE courseSection (
-- 	id SERIAL PRIMARY KEY,
-- 	course_id int4 Not NULL REFERENCES course(id) ON DELETE CASCADE,
--     section_id int4 Not NULL REFERENCES section(id) ON DELETE CASCADE,
-- );


-- CREATE TABLE professorSection (
-- 	id SERIAL PRIMARY KEY,
-- 	professor_id int4 Not NULL REFERENCES professor(id) ON DELETE CASCADE,
--     section_id int4 Not NULL REFERENCES section(id) ON DELETE CASCADE,
-- 	deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE professor (
-- 	id SERIAL PRIMARY KEY, 
--     first_name varchar(20) NOT NULL,
--     middle_name varchar(20) NOT NULL,
--     last_name varchar(20) NOT NULL,
--     email varchar(20) NOT NULL,
--     deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE deparment(
--     id SERIAL PRIMARY KEY,
--     description varchar(1000) NOT NULL,
--     acronym varchar(10) Not NULL,
--     deleted BOOLEAN NOT NULL
-- );



-- CREATE TABLE courseMajor (
-- 	id SERIAL PRIMARY KEY,
-- 	course_id int4 Not NULL REFERENCES course(id) ON DELETE CASCADE,
--     major_id int4 Not NULL REFERENCES major(id) ON DELETE CASCADE,
-- 	deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE courseMinor (
-- 	id SERIAL PRIMARY KEY,
-- 	course_id int4 Not NULL REFERENCES course(id) ON DELETE CASCADE,
--     minor_id int4 Not NULL REFERENCES minor(id) ON DELETE CASCADE,
-- 	deleted BOOLEAN NOT NULL
-- );


-- CREATE TABLE major(
--     id SERIAL PRIMARY KEY,
--     description varchar(500) NOT NULL,
--     deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE minor(
--     id SERIAL PRIMARY KEY,
--     description varchar(500) NOT NULL,
--     deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE studentMajor (
-- 	id SERIAL PRIMARY KEY,
-- 	student_id int4 Not NULL REFERENCES student(id) ON DELETE CASCADE,
--     major_id int4 Not NULL REFERENCES major(id) ON DELETE CASCADE,
-- 	deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE studentMinor (
-- 	id SERIAL PRIMARY KEY,
-- 	student_id int4 Not NULL REFERENCES student(id) ON DELETE CASCADE,
--     minor_id int4 Not NULL REFERENCES minor(id) ON DELETE CASCADE,
-- 	deleted BOOLEAN NOT NULL
-- );

-- CREATE TABLE semester(
--     id SERIAL PRIMARY KEY,
--     description varchar(10) NOT NULL,
--     deleted BOOLEAN NOT NULL
-- );