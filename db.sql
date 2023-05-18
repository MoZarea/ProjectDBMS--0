create database project;
use project;
CREATE TABLE auth (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  pass VARCHAR(255) NOT NULL
);
INSERT INTO auth (username, pass) VALUES ('abdullah', '123');
SELECT * FROM auth;
CREATE TABLE employer (
  id INT AUTO_INCREMENT PRIMARY KEY,
  fname VARCHAR(50) NOT NULL,
  lname VARCHAR(50) NOT NULL,
  tel JSON,
  description TEXT,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE price (
  id INT AUTO_INCREMENT PRIMARY KEY,
  fulltime DECIMAL(10, 2) NOT NULL,
  halftime DECIMAL(10, 2) NOT NULL,
  overtime DECIMAL(10, 2) NOT NULL,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO price (fulltime, halftime, overtime) VALUES (10.00, 5.00, 15.00);
INSERT INTO price (fulltime, halftime, overtime) VALUES (12.50, 6.25, 18.75);
INSERT INTO price (fulltime, halftime, overtime) VALUES (8.75, 4.38, 13.13);

CREATE TABLE worker (
  id INT AUTO_INCREMENT PRIMARY KEY,
  fname VARCHAR(50) NOT NULL,
  lname VARCHAR(50) NOT NULL,
  tel JSON,
  iban VARCHAR(50),
  description TEXT,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE job (
  id INT AUTO_INCREMENT PRIMARY KEY,
  employer_id INT,
  price_id INT,
  title VARCHAR(100) NOT NULL,
  description TEXT,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (employer_id) REFERENCES employer(id),
  FOREIGN KEY (price_id) REFERENCES price(id)
);
CREATE TABLE paytype (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE worktype (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(50) NOT NULL,
  no INT NOT NULL,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE invoice (
  id INT AUTO_INCREMENT PRIMARY KEY,
  job_id INT,
  amount DECIMAL(10, 2) NOT NULL,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (job_id) REFERENCES job(id)
);
CREATE TABLE payment (
  id INT AUTO_INCREMENT PRIMARY KEY,
  worker_id INT,
  job_id INT,
  paytype_id INT,
  amount DECIMAL(10, 2) NOT NULL,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (worker_id) REFERENCES worker(id),
  FOREIGN KEY (job_id) REFERENCES job(id),
  FOREIGN KEY (paytype_id) REFERENCES paytype(id)
);
CREATE TABLE workgroup (
  id INT AUTO_INCREMENT PRIMARY KEY,
  job_id INT,
  worktype_id INT,
  workCount INT,
  description TEXT,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (job_id) REFERENCES job(id),
  FOREIGN KEY (worktype_id) REFERENCES worktype(id)
);

CREATE TABLE work (
  id INT AUTO_INCREMENT PRIMARY KEY,
  job_id INT,
  worker_id INT,
  worktype_id INT,
  workgroup_id INT,
  description TEXT,
  date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (job_id) REFERENCES job(id),
  FOREIGN KEY (worker_id) REFERENCES worker(id),
  FOREIGN KEY (worktype_id) REFERENCES worktype(id),
  FOREIGN KEY (workgroup_id) REFERENCES workgroup(id)
);
-- Query to retrieve all employers from the "employer" table
SELECT * FROM employer;
-- Query to insert an employer into the "employer" table
INSERT INTO employer (id, fname, lname, tel, description, date) VALUES (1, 'John', 'Doe', '1234567890', 'Description', '2023-05-17 10:00:00');
-- Query to retrieve the latest employer from the "employer" table
SELECT * FROM employer ORDER BY id DESC LIMIT 1;
-- Query to update an employer in the "employer" table
UPDATE employer SET fname='Jane', lname='Doe', tel='9876543210', description='Updated description', date='2023-05-18 10:00:00' WHERE id=1;
-- Query to delete an employer from the "employer" table
DELETE FROM employer WHERE id=1;

-- Query to retrieve invoices based on employer ID
SELECT * FROM invoice WHERE job_id IN (SELECT id FROM job WHERE employer_id = <employer_id>);

-- Query to retrieve all invoices
SELECT * FROM invoice;

-- Query to retrieve invoices based on a specific column and ID
SELECT * FROM invoice WHERE <column_name> = <id>;

-- Query to insert a new invoice
INSERT INTO invoice (job_id, amount) VALUES (<job_id>, <amount>);

-- Query to retrieve the latest invoice
SELECT * FROM invoice ORDER BY id DESC LIMIT 1;

-- Query to update an existing invoice
UPDATE invoice SET job_id = <job_id>, amount = <amount> WHERE id = <invoice_id>;

-- Query to delete an invoice
DELETE FROM invoice WHERE id = <invoice_id>;





