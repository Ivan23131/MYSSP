CREATE TABLE t_orders (
    id SERIAL PRIMARY KEY,
    c_status VARCHAR(255)
);

ALTER TABLE t_tickets
ADD COLUMN order_id INT;

ALTER TABLE t_tickets
ADD CONSTRAINT fk_tickets_order_id
FOREIGN KEY (order_id) REFERENCES t_orders(id) ON DELETE CASCADE;