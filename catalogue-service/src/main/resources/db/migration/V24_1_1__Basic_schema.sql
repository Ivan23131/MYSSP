CREATE TABLE t_events
(
    id  serial primary key,
    c_title   varchar(50) not null check (length(trim(c_title)) >= 3),
    c_place varchar(1000) not null,
    c_type varchar(100) not null,
    c_date date not null,
    c_amount_of_tickets integer not null check (c_amount_of_tickets >= 0)
);

CREATE TABLE t_tickets
(
    id  serial primary key,
    event_id integer not null,
    c_ticket_number varchar(50) not null,
    c_status varchar(50) not null,
    c_price numeric(10, 2) not null,
    foreign key (event_id) references t_events(id)
);