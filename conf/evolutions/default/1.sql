# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table users (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  middle_name               varchar(255),
  phone_number              varchar(255),
  address                   varchar(255),
  accepted                  BOOLEAN DEFAULT FALSE NOT NULL,
  deleted                   BOOLEAN DEFAULT FALSE NOT NULL,
  constraint uq_users_email unique (email),
  constraint pk_users primary key (id))
;

create table roles (
  id                        bigint not null,
  name                      varchar(255),
  constraint uq_roles_name unique (name),
  constraint pk_roles primary key (id))
;

create table permissions (
  id                        bigint not null,
  permission_value          varchar(255),
  constraint pk_permissions primary key (id))
;

create table walls (
  id                        bigint not null,
  user_id                   bigint,
  wall_id                   integer,
  name                      varchar(255),
  constraint pk_walls primary key (id))
;


create table users_roles (
  users_id                       bigint not null,
  roles_id                       bigint not null,
  constraint pk_users_roles primary key (users_id, roles_id))
;

create table users_permissions (
  users_id                       bigint not null,
  permissions_id                 bigint not null,
  constraint pk_users_permissions primary key (users_id, permissions_id))
;
create sequence users_seq;

create sequence roles_seq;

create sequence permissions_seq;

create sequence walls_seq;

alter table walls add constraint fk_walls_user_1 foreign key (user_id) references users (id);
create index ix_walls_user_1 on walls (user_id);



alter table users_roles add constraint fk_users_roles_users_01 foreign key (users_id) references users (id);

alter table users_roles add constraint fk_users_roles_roles_02 foreign key (roles_id) references roles (id);

alter table users_permissions add constraint fk_users_permissions_users_01 foreign key (users_id) references users (id);

alter table users_permissions add constraint fk_users_permissions_permissi_02 foreign key (permissions_id) references permissions (id);

# --- !Downs

drop table if exists users cascade;

drop table if exists users_roles cascade;

drop table if exists users_permissions cascade;

drop table if exists roles cascade;

drop table if exists permissions cascade;

drop table if exists walls cascade;

drop sequence if exists users_seq;

drop sequence if exists roles_seq;

drop sequence if exists permissions_seq;

drop sequence if exists walls_seq;

