DROP TABLE IF EXISTS book;

CREATE TABLE IF NOT EXISTS book(
    id INT(11) AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(1000),
    price decimal(5,2));

INSERT INTO book (title, author, price)
VALUES ('1984', 'George Orwell', 5.00),
('Ready Player One', 'Ernest Cline', 5.00),
('Game of Thrones', 'George R.R. Martin', 35.00),
('Shadow and Shrek', 'Mark the Tapir', 6.23);