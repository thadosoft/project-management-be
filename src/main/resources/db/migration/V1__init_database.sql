create table if not exists users
(
    id           char(36),
    role         varchar(50)  not null,
    name         varchar(255) not null,
    email        varchar(255) not null unique,
    username     varchar(15)  not null unique,
    password     varchar(255) not null,
    phone_number varchar(10)  not null unique,

    primary key (id)
);

CREATE INDEX idx_users ON users (name, username, email);

create table if not exists tokens
(
    id         char(36),
    user_id    char(36)     not null,
    token      varchar(255) not null,
    token_type varchar(10)  not null,
    expired    boolean      not null,
    revoked    boolean      not null,

    primary key (id),
    foreign key (user_id) references users (id)
);

create table if not exists projects
(
    id           char(36),
    user_id      char(36)     not null,
    name         varchar(255) not null unique,
    description  varchar(255) null,
    entry_by     char(36)     null,
    entry_date   datetime default current_timestamp,
    updated_by   char(36)     null,
    updated_date datetime default current_timestamp on update current_timestamp,

    primary key (id),
    foreign key (user_id) references users (id)
);

CREATE INDEX idx_projects ON projects (name, user_id);

create table if not exists tasks
(
    id           char(36),
    project_id   char(36)    not null,
    status       varchar(50) not null,
    task_order   int         not null check (task_order >= 1),
    entry_by     char(36)    null,
    entry_date   datetime default current_timestamp,
    updated_by   char(36)    null,
    updated_date datetime default current_timestamp on update current_timestamp,

    primary key (id),
    foreign key (project_id) references projects (id),
    constraint unique_project_status unique (project_id, status)
);

create table if not exists assignments
(
    id               char(36),
    assigner_id      char(36)     not null,
    receiver_id      char(36)     ,
    task_id          char(36)     not null,
    title            varchar(100) not null,
    description      text         null,
    assignment_order int          not null check (assignment_order >= 1),
    entry_by         char(36)     null,
    entry_date       datetime default current_timestamp,
    updated_by       char(36)     null,
    updated_date     datetime default current_timestamp on update current_timestamp,

    primary key (id),
    foreign key (assigner_id) references users (id),
    foreign key (receiver_id) references users (id),
    foreign key (task_id) references tasks (id)
);

create table if not exists medias
(
    id            char(36),
    assignment_id char(36)     not null,
    name          varchar(100) not null,
    type          VARCHAR(255) ,
    path          varchar(255) not null,
    size          bigint       not null,
    entry_by      char(36)     null,
    entry_date    datetime default current_timestamp,
    updated_by    char(36)     null,
    updated_date  datetime default current_timestamp on update current_timestamp,

    primary key (id),
    foreign key (assignment_id) references assignments (id),
    constraint unique_assignment_name unique (assignment_id, name)
);

CREATE INDEX idx_medias ON medias (path, type)


