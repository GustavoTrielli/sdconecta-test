CREATE TABLE TB_USER (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255),
    name varchar(255) NOT NULL,
    surname varchar(255),
    mobile_phone varchar(255),
    admin boolean DEFAULT FALSE,
    authentication_status varchar(255)
);

CREATE TABLE TB_CRM (
    id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    crm varchar(45) NOT NULL,
    uf varchar(2) NOT NULL,
    specialty varchar(255),
    user_id int,
    FOREIGN KEY (user_id) REFERENCES TB_USER(id)
);