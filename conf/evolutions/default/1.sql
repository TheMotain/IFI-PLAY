# --- First database schema

# --- !Ups

create table company (
  id                        bigint not null,
  name                      varchar(255),
  constraint pk_company primary key (id))
;

create table computer (
  id                        bigint not null,
  name                      varchar(255),
  introduced                timestamp,
  discontinued              timestamp,
  company_id                bigint,
  constraint pk_computer primary key (id))
;

create table us (
  id                        bigint not null,
  name                      varchar(255),
  description				text,
  constraint pk_us primary key (id))
;

create table task (
  id                        bigint not null,
  name                      varchar(255),
  description				text,
  us_id						bigint,
  constraint pk_task primary key (id))
;

create sequence company_seq start with 1000;

create sequence computer_seq start with 1000;

create sequence us_seq start with 1000;

alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
create index ix_computer_company_1 on computer (company_id);
alter table task add constraint fk_us_task_1 foreign key (us_id) references company (id) on delete restrict on update restrict;
create index ix_task_us_1 on task (us_id);


# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists company;

drop table if exists computer;

drop table if exists us;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists company_seq;

drop sequence if exists computer_seq;

drop sequence if exists us_seq;

