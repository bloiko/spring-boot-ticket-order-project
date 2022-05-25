-- auto-generated definition
create table hibernate_sequence
(
    next_val bigint null
)
    engine = MyISAM;



-- auto-generated definition
create table security_role
(
    id        bigint auto_increment
        primary key,
    role_name varchar(255) null
)
    engine = MyISAM;



-- auto-generated definition
create table security_user
(
    id       bigint auto_increment
        primary key,
    password varchar(255) null,
    username varchar(255) null
)
    engine = MyISAM;




-- auto-generated definition
create table ticket
(
    ticket_id   bigint       not null
        primary key,
    trip_date   datetime     null,
    destination varchar(255) null,
    price       bigint       null,
    source      varchar(255) null
)
    engine = MyISAM;




-- auto-generated definition
create table ticket_order
(
    order_id  bigint not null
        primary key,
    ticket_id bigint null,
    user_id   bigint not null
)
    engine = MyISAM;

create index FK4aqsxo646hu5estj1j1r24wp
    on ticket_order (user_id);

create index FKcfe0grvkheal69kfd27n941ml
    on ticket_order (ticket_id);




-- auto-generated definition
create table user_role
(
    user_id bigint not null,
    role_id bigint not null
)
    engine = MyISAM;

create index FKag2tat8o2o7yvedewuh0tosbq
    on user_role (user_id);

create index FKaovu9xgrvfngaab129ho0e6s1
    on user_role (role_id);

