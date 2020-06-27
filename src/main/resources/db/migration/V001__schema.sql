
create table if not exists branches
(
    id integer not null
        constraint branches_pk
            primary key,
    title varchar,
    lon numeric,
    lat numeric,
    address varchar
);

comment on column branches.id is 'Номер офиса';

comment on column branches.title is 'Удобное имя для поиска';

comment on column branches.lon is 'Долгота';

comment on column branches.lat is 'Широта';

comment on column branches.address is 'Адрес';

create table if not exists queue_log
(
    data date,
    start_time_of_wait time,
    end_time_of_wait time,
    end_time_of_service time,
    branches_id integer
        constraint queue_log_fk
            references branches,
    id serial not null
        constraint queue_log_pk
            primary key
);

comment on column queue_log.data is 'Дата взятия талона';

comment on column queue_log.start_time_of_wait is 'Время начала ожидания';

comment on column queue_log.end_time_of_wait is 'Время окончания ожидания';

comment on column queue_log.end_time_of_service is 'Время окончания обслуживания';

comment on column queue_log.branches_id is 'Код отделения';