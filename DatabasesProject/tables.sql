DROP DATABASE IF EXISTS sampledb;
CREATE DATABASE IF NOT EXISTS sampledb;
USE sampledb;

CREATE TABLE IF NOT EXISTS paper(
	paperid INTEGER,
	title VARCHAR(50),
	abstract VARCHAR(250),
	pdf VARCHAR(100),
	PRIMARY KEY (paperid)
);

CREATE TABLE IF NOT EXISTS author(
	email VARCHAR(100),
	name VARCHAR(50),
	affiliation VARCHAR(100),
	PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS written(
	paperid INTEGER,
	email VARCHAR(100),
	significance INTEGER,
	FOREIGN KEY (paperid) REFERENCES paper(paperid),
	FOREIGN KEY (email) REFERENCES author(email)
);

CREATE TABLE IF NOT EXISTS pcmember(
  memberid INTEGER NOT NULL AUTO_INCREMENT
	email VARCHAR(100),
	name VARCHAR(20),
	PRIMARY KEY (memberid)
);

CREATE TABLE IF NOT EXISTS review(
	reportid INTEGER,
	sdate DATE,
	comm VARCHAR(250),
	recommendation CHAR(1),
	paperid INTEGER NOT NULL UNIQUE,
	memberid INTEGER NOT NULL,
	FOREIGN KEY (paperid) REFERENCES paper(paperid),
	FOREIGN KEY (memberid) REFERENCES pcmember(memberid)
);

DELIMITER $$
CREATE TRIGGER max5papers
	BEFORE INSERT ON review
	FOR EACH ROW
BEGIN
	IF new.email IN (
    SELECT * FROM pcmember P
    WHERE 5 = (
		SELECT COUNT(*)
        FROM review R
        WHERE R.email = P.email
        )
	) THEN
		SIGNAL SQLSTATE'45000';
	END IF;
END$$


/*CREATE ASSERTION 3pc
CHECK NOT EXISTS(
	SELECT * FROM paper P
	WHERE 3 <>(
		SELECT COUNT (*)
		FROM review R
		WHERE R.paperid = P.paperid
	)
)*/

/*CREATE ASSERTION max5papers
CHECK NOT NOT EXISTS(
	SELECT * FROM pcmember P
	WHERE 5 < (
		SELECT *
		FROM review R
		WHERE R.email = P.email
	)
)*/