# Trabalho final DM110

•	BD Script:

create table host(

    id integer not null,

    ip varchar(50) not null,

    status bit null default 0,

    constraint pk_host primary key (id)

);



•	java:/jms/queue/dm110queue
