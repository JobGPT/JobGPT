CREATE DATABASE JOBGPT;
USE JOBGPT;

CREATE TABLE Company (
    companyID INT PRIMARY KEY,
    companyName VARCHAR(100),
    areaSido VARCHAR(20),
    size INT,
    industryCode INT,
    FOREIGN KEY (industryCode) REFERENCES Industry(code)
);

CREATE TABLE Industry (
    code INT PRIMARY KEY,
    industryName VARCHAR(255)
);

CREATE TABLE companyInfo (
    companyID INT,
    compInfo STRING,
    fieldInfo STRING,
    FOREIGN KEY (companyID) REFERENCES Company(companyID)
);

CREATE TABLE Industry (
    code INT PRIMARY KEY,
    industry_name VARCHAR(255)
);

CREATE TABLE IndustryFigure (
    code INT PRIMARY KEY,
    industry_figure VARCHAR(255),
    industry_code INT,
    FOREIGN KEY (industry_code) REFERENCES Industry(code)
);

CREATE TABLE IndustryDetail (
    code INT PRIMARY KEY,
    industry_deatil VARCHAR(255),
    figure_code INT,
    FOREIGN KEY (figure_code) REFERENCES IndustryFigure(code)
);


