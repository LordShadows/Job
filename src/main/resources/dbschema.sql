DROP TABLE IF EXISTS DEPARTMENTS;
DROP TABLE IF EXISTS EMPLOYEES;

--DEPARTMENTS TABLE
CREATE TABLE IF NOT EXISTS DEPARTMENTS (
    IDDEPARTMENTS INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
    NAME_DEP VARCHAR(255) NOT NULL PRIMARY KEY,
);

--EMPLOYEES TABLE
CREATE TABLE IF NOT EXISTS EMPLOYEES (
      IDEMPLOYEES INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL,
      FIO VARCHAR(255) NOT NULL,
      DATE_OF_BIRTH DATE NOT NULL,
      SALARY INT NOT NULL,
      NAME_DEP VARCHAR(255) NOT NULL,
      CONSTRAINT FK_EMPLOYEES_DEPARTMENTS FOREIGN KEY(NAME_DEP) REFERENCES DEPARTMENTS(NAME_DEP));
;