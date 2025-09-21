ALTER TABLE orders
DROP
COLUMN transaction_id;

ALTER TABLE tickets
    ALTER COLUMN order_id DROP NOT NULL;