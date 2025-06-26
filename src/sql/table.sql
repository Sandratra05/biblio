DROP DATABASE IF EXISTS spring_dept;

CREATE DATABASE spring_dept;
USE spring_dept;

CREATE TABLE IF NOT EXISTS departement (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);