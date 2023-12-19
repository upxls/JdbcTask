CREATE TABLE users
(
    user_id    SERIAL PRIMARY KEY,
    login      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

INSERT INTO users (user_id, login, first_name, last_name)
VALUES (1, 'jdoe', 'John', 'Doe'),
       (2, 'asmith', 'Alice', 'Smith'),
       (3, 'btaylor', 'Bob', 'Taylor'),
       (4, 'cking', 'Carol', 'King');
