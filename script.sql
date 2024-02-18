CREATE TABLE customers (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  credit_limit INTEGER NOT NULL,
  balance INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE transactions(
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    value INTEGER NOT NULL,
    type CHAR(1) NOT NULL,
    description VARCHAR(10) NOT NULL,
    accomplished_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cliente_id FOREIGN KEY (customer_id) REFERENCES customers(id)
);
-- CREATE UNIQUE INDEX idx_clientes_id ON clientes USING btree (id);
CREATE INDEX idx_transactions_customer_id ON transactions USING btree (customer_id);

INSERT INTO customers (name, credit_limit)
VALUES
  ('o barato sai caro', 1000 * 100),
  ('zan corp ltda', 800 * 100),
  ('les cruders', 10000 * 100),
  ('padaria joia de cocaia', 100000 * 100),
  ('kid mais', 5000 * 100);

