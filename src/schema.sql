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

