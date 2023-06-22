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

CREATE TABLE companyInfo (
    companyID INT,
    compInfo JSON,
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


<<<<<<< HEAD
LOAD DATA INFILE './UpperIndustryCodes.txt'
INTO TABLE Industry
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD DATA INFILE './IndustryCodes.txt'
INTO TABLE IndustryFigure
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

LOAD DATA INFILE './KeywordCodes.txt'
INTO TABLE IndustryDetail
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;
=======
-- LOAD DATA INFILE './UpperIndustryCodes.txt'
-- INTO TABLE Industry
-- FIELDS TERMINATED BY '\t'
-- LINES TERMINATED BY '\n'
-- IGNORE 1 ROWS;

-- LOAD DATA INFILE './IndustryCodes.txt'
-- INTO TABLE IndustryFigure
-- FIELDS TERMINATED BY '\t'
-- LINES TERMINATED BY '\n'
-- IGNORE 1 ROWS;

-- LOAD DATA INFILE './KeywordCodes.txt'
-- INTO TABLE IndustryDetail
-- FIELDS TERMINATED BY '\t'
-- LINES TERMINATED BY '\n'
-- IGNORE 1 ROWS;
>>>>>>> f5b8443d4b9ad97f2534829d937704b862f93482
