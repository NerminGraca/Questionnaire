# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answer (
  id                            serial not null,
  question_id                   integer,
  user_questionnaire_id         integer,
  text_answer                   TEXT,
  is_yes                        boolean,
  constraint pk_answer primary key (id)
);

create table api_keys (
  id                            serial not null,
  access_token                  varchar(255),
  created_at                    timestamp,
  expires_at                    timestamp,
  updated_at                    timestamp,
  user_id                       integer,
  constraint uq_api_keys_user_id unique (user_id),
  constraint pk_api_keys primary key (id)
);

create table offered_answer (
  id                            serial not null,
  question_id                   integer not null,
  answer                        varchar(255),
  constraint pk_offered_answer primary key (id)
);

create table password_resets (
  id                            serial not null,
  email                         varchar(255),
  token                         varchar(255),
  expires_at                    timestamp,
  constraint pk_password_resets primary key (id)
);

create table question (
  id                            serial not null,
  questionnaire_id              integer not null,
  index                         integer,
  text                          TEXT,
  type                          integer,
  constraint ck_question_type check (type in (0,1,2,3)),
  constraint pk_question primary key (id)
);

create table questionnaire (
  id                            serial not null,
  title                         varchar(255),
  constraint pk_questionnaire primary key (id)
);

create table selected_offered_answer (
  id                            serial not null,
  question_id                   integer not null,
  offered_answer_id             integer,
  constraint pk_selected_offered_answer primary key (id)
);

create table users (
  id                            serial not null,
  firstname                     varchar(255),
  lastname                      varchar(255),
  email                         varchar(255),
  password_digest               varchar(255),
  is_admin                      boolean,
  facebook_id                   varchar(255),
  constraint pk_users primary key (id)
);

create table user_questionnaire (
  id                            serial not null,
  user_id                       integer,
  questionnaire_id              integer,
  constraint pk_user_questionnaire primary key (id)
);

alter table answer add constraint fk_answer_question_id foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_answer_question_id on answer (question_id);

alter table answer add constraint fk_answer_user_questionnaire_id foreign key (user_questionnaire_id) references user_questionnaire (id) on delete restrict on update restrict;
create index ix_answer_user_questionnaire_id on answer (user_questionnaire_id);

alter table api_keys add constraint fk_api_keys_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

alter table offered_answer add constraint fk_offered_answer_question_id foreign key (question_id) references question (id) on delete restrict on update restrict;
create index ix_offered_answer_question_id on offered_answer (question_id);

alter table question add constraint fk_question_questionnaire_id foreign key (questionnaire_id) references questionnaire (id) on delete restrict on update restrict;
create index ix_question_questionnaire_id on question (questionnaire_id);

alter table selected_offered_answer add constraint fk_selected_offered_answer_question_id foreign key (question_id) references answer (id) on delete restrict on update restrict;
create index ix_selected_offered_answer_question_id on selected_offered_answer (question_id);

alter table selected_offered_answer add constraint fk_selected_offered_answer_offered_answer_id foreign key (offered_answer_id) references offered_answer (id) on delete restrict on update restrict;
create index ix_selected_offered_answer_offered_answer_id on selected_offered_answer (offered_answer_id);

alter table user_questionnaire add constraint fk_user_questionnaire_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_user_questionnaire_user_id on user_questionnaire (user_id);

alter table user_questionnaire add constraint fk_user_questionnaire_questionnaire_id foreign key (questionnaire_id) references questionnaire (id) on delete restrict on update restrict;
create index ix_user_questionnaire_questionnaire_id on user_questionnaire (questionnaire_id);


# --- !Downs

alter table if exists answer drop constraint if exists fk_answer_question_id;
drop index if exists ix_answer_question_id;

alter table if exists answer drop constraint if exists fk_answer_user_questionnaire_id;
drop index if exists ix_answer_user_questionnaire_id;

alter table if exists api_keys drop constraint if exists fk_api_keys_user_id;

alter table if exists offered_answer drop constraint if exists fk_offered_answer_question_id;
drop index if exists ix_offered_answer_question_id;

alter table if exists question drop constraint if exists fk_question_questionnaire_id;
drop index if exists ix_question_questionnaire_id;

alter table if exists selected_offered_answer drop constraint if exists fk_selected_offered_answer_question_id;
drop index if exists ix_selected_offered_answer_question_id;

alter table if exists selected_offered_answer drop constraint if exists fk_selected_offered_answer_offered_answer_id;
drop index if exists ix_selected_offered_answer_offered_answer_id;

alter table if exists user_questionnaire drop constraint if exists fk_user_questionnaire_user_id;
drop index if exists ix_user_questionnaire_user_id;

alter table if exists user_questionnaire drop constraint if exists fk_user_questionnaire_questionnaire_id;
drop index if exists ix_user_questionnaire_questionnaire_id;

drop table if exists answer cascade;

drop table if exists api_keys cascade;

drop table if exists offered_answer cascade;

drop table if exists password_resets cascade;

drop table if exists question cascade;

drop table if exists questionnaire cascade;

drop table if exists selected_offered_answer cascade;

drop table if exists users cascade;

drop table if exists user_questionnaire cascade;

