CREATE TABLE transactions
(
    id                    VARCHAR(36) NOT NULL PRIMARY KEY,
    person_id             VARCHAR(36) NOT NULL,
    transaction_time      TIMESTAMP   NOT NULL,
    transaction_direction VARCHAR(6)  NOT NULL,
    value                 INTEGER     NOT NULL,
    iban                  VARCHAR(29) NOT NULL
);


