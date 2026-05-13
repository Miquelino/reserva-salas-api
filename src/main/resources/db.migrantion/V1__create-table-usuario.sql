create table usuario(
                        id bigint not null auto_increment,
                        nome varchar(100) not null unique,
                        email varchar(100) not null unique,
                        idade int not null,
                        cpf varchar(14) not null unique,
                        primary key(id)
);