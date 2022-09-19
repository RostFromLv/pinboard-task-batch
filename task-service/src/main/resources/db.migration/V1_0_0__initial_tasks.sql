create table if not exists tasks
(
    id                SERIAL PRIMARY KEY,
    title             varchar(63)  NOT NULL,
    text              varchar(255) NOT NULL,
    status            varchar(15)  NOT NULL,
    user_id           integer      NOT NULL,
    notification_text varchar(128) NOT NULL
)