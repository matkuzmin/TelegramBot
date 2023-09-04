-- liquibase formatted sql

-- changeset bot:1
CREATE table NotificationTask(
    id bigint primary key ,
    chatId bigint not null ,
    textMessage text not null ,
    timeMessage timestamp
)

