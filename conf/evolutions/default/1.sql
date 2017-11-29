# --- First database schema

# --- !Ups

create table task (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  description				text,
  status					bigint default 1,
  constraint pk_task primary key (id))
;

create sequence task_seq start with 1000;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists task;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists task_seq;

