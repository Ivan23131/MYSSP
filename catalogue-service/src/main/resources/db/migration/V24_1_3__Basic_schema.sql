ALTER TABLE t_events
ALTER COLUMN c_date TYPE timestamp
USING c_date::timestamp;