CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    dob DATE,
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    account_no BIGINT UNIQUE,
    password VARCHAR(100),
    pin INT,
    balance DOUBLE DEFAULT 0.0
);

CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_no VARCHAR(20),
    type VARCHAR(20),
    amount DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

-- Insert Admin
INSERT INTO admin (username, password) VALUES ('admin', 'Admin@123');
truncate table admin;

-- Insert 10 customers
INSERT INTO customers (name, dob, email, phone, address, account_no, password, pin, balance) VALUES
('Aman Gupta', '2000-05-21', 'aman@example.com', '9998887771', 'Delhi', '100001', 'pass1234', 1234, 5000.0),
('Priya Sharma', '1999-08-15', 'priya@example.com', '9998887772', 'Mumbai', '100002', 'pass1234', 1234, 7000.0),
('Rohit Mehta', '2001-11-10', 'rohit@example.com', '9998887773', 'Kolkata', '100003', 'pass1234', 1234, 3000.0),
('Sneha Das', '2002-03-25', 'sneha@example.com', '9998887774', 'Chennai', '100004', 'pass1234', 1234, 6500.0),
('Raj Verma', '1998-06-05', 'raj@example.com', '9998887775', 'Bangalore', '100005', 'pass1234', 1234, 4000.0),
('Kriti Jain', '2001-01-14', 'kriti@example.com', '9998887776', 'Pune', '100006', 'pass1234', 1234, 5500.0),
('Mohit Rathi', '1997-12-30', 'mohit@example.com', '9998887777', 'Hyderabad', '100007', 'pass1234', 1234, 6000.0),
('Divya Roy', '2000-09-19', 'divya@example.com', '9998887778', 'Ahmedabad', '100008', 'pass1234', 1234, 8000.0),
('Ankit Sen', '2003-04-22', 'ankit@example.com', '9998887779', 'Jaipur', '100009', 'pass1234', 1234, 4200.0),
('Neha Nair', '1996-07-13', 'neha@example.com', '9998887780', 'Lucknow', '100010', 'pass1234', 1234, 5100.0);

-- Insert 5 sample transactions per account
INSERT INTO transactions (account_no, type, amount) VALUES
('100001', 'Deposit', 2000), ('100001', 'Withdraw', 500), ('100001', 'Deposit', 1000), ('100001', 'Withdraw', 200), ('100001', 'Deposit', 700),
('100002', 'Deposit', 3000), ('100002', 'Withdraw', 700), ('100002', 'Deposit', 800), ('100002', 'Withdraw', 400), ('100002', 'Deposit', 600),
('100003', 'Withdraw', 300), ('100003', 'Deposit', 1000), ('100003', 'Deposit', 900), ('100003', 'Withdraw', 100), ('100003', 'Deposit', 200),
('100004', 'Deposit', 2500), ('100004', 'Withdraw', 300), ('100004', 'Deposit', 500), ('100004', 'Withdraw', 100), ('100004', 'Deposit', 200),
('100005', 'Deposit', 400), ('100005', 'Withdraw', 500), ('100005', 'Deposit', 900), ('100005', 'Deposit', 1100), ('100005', 'Withdraw', 200);

truncate table customers;
truncate table transactions;
 Drop table customers;
 Select * from customers;