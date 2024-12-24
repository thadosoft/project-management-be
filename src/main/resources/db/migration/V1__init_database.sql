create table if not exists roles
(
    id            bigint auto_increment,
    name          varchar(50) not null unique,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id)
);

create table if not exists users
(
    id            bigint auto_increment,
    role_id       bigint       not null,
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
    id            bigint auto_increment,
    user_id       bigint          not null,
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
    id            bigint auto_increment,
    user_id       bigint not null,
    name          varchar(255),
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (user_id) references users (id)
);

CREATE INDEX idx_projects ON projects (name, user_id);

create table if not exists tasks
(
    id            bigint auto_increment,
    project_id    bigint       not null,
    status        varchar(50) not null,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (project_id) references projects (id)
);

create table if not exists assignments
(
    id            bigint auto_increment,
    assigner_id   bigint       not null,
    receiver_id   bigint       not null,
    task_id       bigint       not null,
    title         varchar(100) not null,
    description   varchar(255) not null,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (assigner_id) references users (id),
    foreign key (receiver_id) references users (id)
);

create table if not exists medias
(
    id            bigint auto_increment,
    assignment_id bigint                  not null,
    type          enum ('IMAGE', 'VIDEO') not null,
    path          varchar(255)            not null,
    modified_date datetime default current_timestamp on update current_timestamp,
    created_date  datetime default current_timestamp,

    primary key (id),
    foreign key (assignment_id) references assignments (id)
);

CREATE INDEX idx_medias ON medias (path, type)


