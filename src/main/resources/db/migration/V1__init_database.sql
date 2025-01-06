create table if not exists roles
(
    id            char(36),
    name          varchar(50) not null unique,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id)
);

create table if not exists users
(
    id            char(36),
    role_id       char(36)     not null,
    name          varchar(50)  not null,
    email         varchar(50)  not null unique,
    username      varchar(50)  not null unique,
    password      varchar(255) not null,
    phone_number  varchar(50)  not null unique,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (role_id) references roles (id)
);

CREATE INDEX idx_users ON users (name, username, email);

create table if not exists tokens
(
    id            char(36),
    user_id       char(36)        not null,
    token         varchar(255)    not null,
    token_type    enum ('BEARER') not null,
    expired       boolean         not null,
    revoked       boolean         not null,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (user_id) references users (id)
);

create table if not exists projects
(
    id            char(36),
    user_id       char(36) not null,
    name          varchar(255),
    description   varchar(255),
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (user_id) references users (id)
);

CREATE INDEX idx_projects ON projects (name, user_id);

create table if not exists tasks
(
    id            char(36),
    project_id    char(36)    not null,
    status        varchar(50) not null,
    task_order    int         not null,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (project_id) references projects (id),
    constraint unique_project_status unique (project_id, status),
    constraint unique_project_order unique (project_id, task_order)
);

create table if not exists assignments
(
    id               char(36),
    assigner_id      char(36)     not null,
    receiver_id      char(36)     not null,
    task_id          char(36)     not null,
    title            varchar(100) not null,
    description      varchar(255) not null,
    status           varchar(50)  not null,
    assignment_order int          not null,
    modified_date    datetime default current_timestamp on update current_timestamp,
    created_date     datetime default current_timestamp,

    primary key (id),
    foreign key (assigner_id) references users (id),
    foreign key (receiver_id) references users (id),
    constraint unique_task_order unique (task_id, assignment_order)
);

create table if not exists medias
(
    id            char(36),
    assignment_id char(36)                not null,
    type          enum ('IMAGE', 'VIDEO') not null,
    path          varchar(255)            not null,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (assignment_id) references assignments (id)
);

CREATE INDEX idx_medias ON medias (path, type)


