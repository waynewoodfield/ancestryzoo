create table zoo (
  zoo_id integer not null constraint zoo_pk PRIMARY KEY,
  name varchar(500)
);
create sequence zoo_seq start with 1 increment by 1;

create table species (
  species_id integer not null constraint species_pk PRIMARY KEY,
  name varchar(500)
);
create sequence species_seq start with 1 increment by 1;

create table animal (
  animal_id integer not null constraint animal_pk PRIMARY KEY,
  name varchar(500),
  species_id integer not null constraint animal_species_fk REFERENCES species(species_id),
  zoo_id integer not null constraint animal_zoo_fk REFERENCES zoo(zoo_id)
);
create sequence animal_seq start with 1 increment by 1;

create table feeding (
  feeding_id integer not null constraint feeding_pk PRIMARY KEY,
  animal_id integer not null constraint feeding_animal_fk REFERENCES animal(animal_id),
  feeding_date date not null,
  feeding_time timestamp not null,
  quantity numeric(10,2) not null
);
create sequence feeding_seq start with 1 increment by 1;

create table shipment (
  shipment_id integer not null constraint shipment_pk PRIMARY KEY,
  zoo_id integer not null constraint shipment_zoo_fk REFERENCES zoo(zoo_id),
  arrived timestamp not null
);
create sequence shipment_seq start with 1 increment by 1;

create table feed_shipment (
  species_id integer not null constraint feed_shipment_species_fk REFERENCES species(species_id),
  shipment_id integer not null constraint feed_shipment_shipment_id REFERENCES shipment(shipment_id)
);

insert into zoo (zoo_id, name) values (1, 'San Diego Zoo');
insert into zoo (zoo_id, name) values (2, 'Hogle Zoo');

insert into species (species_id, name) values (1, 'Panda');
insert into species (species_id, name) values (2, 'Giraffe');
insert into species (species_id, name) values (3, 'Penguin');

insert into animal (animal_id, name, species_id, zoo_id) values (1, 'Plumpy', 1, 1);
insert into animal (animal_id, name, species_id, zoo_id) values (2, 'Velma', 1, 1);
insert into animal (animal_id, name, species_id, zoo_id) values (3, 'Bamboo', 1, 2);
insert into animal (animal_id, name, species_id, zoo_id) values (4, 'Shoots', 1, 2);
insert into animal (animal_id, name, species_id, zoo_id) values (5, 'George', 2, 1);
insert into animal (animal_id, name, species_id, zoo_id) values (6, 'Spot', 2, 1);
insert into animal (animal_id, name, species_id, zoo_id) values (7, 'Duke', 2, 2);
insert into animal (animal_id, name, species_id, zoo_id) values (8, 'Dutchess', 2, 2);
insert into animal (animal_id, name, species_id, zoo_id) values (9, 'Icy', 3, 1);
insert into animal (animal_id, name, species_id, zoo_id) values (10, 'Flipper', 3, 1);
insert into animal (animal_id, name, species_id, zoo_id) values (11, 'Tux', 3, 2);
insert into animal (animal_id, name, species_id, zoo_id) values (12, 'Tuxette', 3, 2);
