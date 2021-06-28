create table phone (id bigint generated by default as identity (start with 1), citycode varchar(255), contrycode varchar(255), number varchar(255), user_id bigint not null, primary key (id))
create table user (id bigint generated by default as identity (start with 1), created timestamp, email varchar(255), last_login timestamp, modified timestamp, name varchar(255), password varchar(255), token varchar(255), primary key (id))
alter table phone add constraint FKb0niws2cd0doybhib6srpb5hh foreign key (user_id) references user
