DROP TABLE IF EXISTS loan_data;

CREATE TABLE loan_data (
                           id INT PRIMARY KEY,
                           amount INT NOT NULL,
                           period INT NOT NULL
);

INSERT INTO loan_data (id, amount, period)
VALUES (1, 3000, 48);

INSERT INTO loan_data (id, amount, period)
VALUES (2, 5000, 36);

INSERT INTO loan_data (id, amount, period)
VALUES (3, 10000, 24);

INSERT INTO loan_data (id, amount, period)
VALUES (4, 15000, 12);

