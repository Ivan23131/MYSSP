ALTER TABLE t_events
ADD COLUMN c_organizer INT;

alter table t_events
add constraint fk_user
foreign key (c_organizer) references t_user(id);

alter table t_tickets
drop column order_id;


ALTER TABLE t_tickets
ADD COLUMN c_user INT;

alter table t_tickets
add constraint fk_user
foreign key (c_user) references t_user(id);