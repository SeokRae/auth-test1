CREATE TABLE TB_MEMBER
(
    id       bigint      NOT NULL,
    username varchar(20) NOT NULL,
    email    varchar(20) NOT NULL,
    password varchar(80) NOT NULL,
    role     varchar(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT unq_email_id UNIQUE (email)
);