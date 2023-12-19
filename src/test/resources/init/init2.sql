CREATE TABLE user_table
(
    ldap_id    SERIAL PRIMARY KEY,
    ldap_login VARCHAR(255),
    name       VARCHAR(255),
    surname    VARCHAR(255)
);

INSERT INTO user_table (ldap_id, ldap_login, name, surname)
VALUES (1, 'sandrah', 'Sandra', 'Harris'),
       (2, 'mikeb', 'Mike', 'Brown'),
       (3, 'lucyp', 'Lucy', 'Parker'),
       (4, 'davidg', 'David', 'Green');
