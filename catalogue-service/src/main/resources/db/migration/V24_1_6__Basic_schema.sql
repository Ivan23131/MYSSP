drop table t_tickets;
CREATE TABLE t_tickets (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL,
    c_status VARCHAR(255),
    c_row INT,
    c_place INT,
    c_price INT,
    FOREIGN KEY (event_id) REFERENCES t_events(id) ON DELETE CASCADE
);