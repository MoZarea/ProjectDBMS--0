create database project;
use project;
CREATE TABLE auth (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  pass VARCHAR(255) NOT NULL
);
INSERT INTO auth (username, pass) VALUES ('abdullah', '123');
SELECT * FROM auth;
