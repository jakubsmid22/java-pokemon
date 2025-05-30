create database pokemons;

use pokemons;

create table trainer(
	id int primary key auto_increment,
    name varchar(55) not null
);

create table pokemon(
	id int primary key auto_increment,
    name varchar(55) not null,
    trainer_id int null,
    foreign key(trainer_id) references trainer(id) on delete set null
);

insert into trainer(name) values
('Ash Ketchum'),
('Bea'),
('Akari');

insert into pokemon(name, trainer_id) values
('Pikachu', 1),
('Staryu', 2),
('Onix', 1);

insert into pokemon(name) values
('Bulbasaur'),
('Psyduck'),
('Geodude');