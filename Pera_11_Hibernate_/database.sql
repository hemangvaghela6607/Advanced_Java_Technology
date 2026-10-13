CREATE DATABASE IF NOT EXISTS college_db;

USE college_db;

DROP TABLE IF EXISTS student;

CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT NOT NULL
);

INSERT INTO student (id, name, email, age) VALUES
(1, 'Rahul', 'rahul@gmail.com', 20),
(2, 'Priya', 'priya@gmail.com', 21),
(3, 'Amit', 'amit@gmail.com', 20);
