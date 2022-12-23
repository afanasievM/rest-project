ALTER TABLE transactions
    ADD COLUMN currency VARCHAR(3) NOT NULL;
ALTER TABLE transactions
    ALTER COLUMN value TYPE DECIMAL(10, 2);