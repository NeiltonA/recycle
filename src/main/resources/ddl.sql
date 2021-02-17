create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
create table address (id_address bigint not null auto_increment, city varchar(255), complement varchar(255), neighborhood varchar(255), number varchar(255), state varchar(255), street varchar(255), zip_code varchar(255), id_user bigint not null, primary key (id_address)) engine=InnoDB
create table cooperative (id_cooperative bigint not null auto_increment, cpf_responsible varchar(255), company_name varchar(255), responsible_name varchar(255), id_user bigint not null, primary key (id_cooperative)) engine=InnoDB
create table donation (id_donation bigint not null auto_increment, amount bigint, availability_days varchar(255), availability_period varchar(255), code varchar(255), date_cancellation datetime, date_confirmation datetime, date_register datetime, date_delivery datetime, donor_user_name varchar(255), status varchar(255), storage varchar(255), update_date datetime, id_address bigint, id_cooperative bigint, id_giver bigint, primary key (id_donation)) engine=InnoDB
create table giver (id_giver bigint not null auto_increment, code varchar(255), id_user bigint not null, primary key (id_giver)) engine=InnoDB
create table rate (id_rate bigint not null auto_increment, comment varchar(255), note bigint, id_cooperative bigint, id_giver bigint, primary key (id_rate)) engine=InnoDB
create table roles (id bigint not null auto_increment, name varchar(60), primary key (id)) engine=InnoDB
create table user_roles (user_id bigint not null, role_id bigint not null, primary key (user_id, role_id)) engine=InnoDB
create table users (id_user bigint not null auto_increment, active bit, cell_phone varchar(255), cpf_cnpj varchar(255), email varchar(255), flow_indicator varchar(255), name varchar(255), password varchar(255), primary key (id_user)) engine=InnoDB
alter table roles add constraint UK_nb4h0p6txrmfc0xbrd1kglp9t unique (name)
alter table users add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table address add constraint FKg660itnc13uyo7s04yrqmcn3r foreign key (id_user) references users (id_user)
alter table cooperative add constraint FK5knh2douxn7wvwb063heit9rw foreign key (id_user) references users (id_user)
alter table donation add constraint FKswueb9cobf4ndj5s0acgsepom foreign key (id_address) references address (id_address)
alter table donation add constraint FKgeywnw3d0r0o0rw1avakbwb7t foreign key (id_cooperative) references cooperative (id_cooperative)
alter table donation add constraint FKnflp8h9p8u4172abse6knx6ks foreign key (id_giver) references giver (id_giver)
alter table giver add constraint FKt914w2k3rb3bj0gnx837ohqb6 foreign key (id_user) references users (id_user)
alter table rate add constraint FKbxrldb0r7q1k14e67bg8f05ad foreign key (id_cooperative) references cooperative (id_cooperative)
alter table rate add constraint FK8a7filqeg34raqyxi4qud5lmh foreign key (id_giver) references giver (id_giver)
alter table user_roles add constraint FKh8ciramu9cc9q3qcqiv4ue8a6 foreign key (role_id) references roles (id)
alter table user_roles add constraint FKhfh9dx7w3ubf1co1vdev94g3f foreign key (user_id) references users (id_user)
