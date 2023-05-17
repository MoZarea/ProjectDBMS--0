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




