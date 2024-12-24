-- Insert sample data into roles
INSERT INTO roles (name)
VALUES ('Admin'),
       ('User'),
       ('Manager');

-- Insert sample data into users
INSERT INTO users (role_id, name, email, username, password, phone_number)
VALUES (1, 'Admin User', 'admin@example.com', 'admin', 'adminpass', '1234567890'),
       (2, 'Regular User', 'user@example.com', 'user1', 'userpass', '1234567891'),
       (3, 'Project Manager', 'manager@example.com', 'manager1', 'managerpass', '1234567892');

-- Insert sample data into tokens
INSERT INTO tokens (user_id, token, token_type, expired, revoked)
VALUES (1, 'token_admin', 'BEARER', 0, 0),
       (2, 'token_user1', 'BEARER', 0, 0),
       (3, 'token_manager', 'BEARER', 0, 0);

-- Insert sample data into projects
INSERT INTO projects (user_id, name)
VALUES (3, 'Project Alpha'),
       (3, 'Project Beta');

-- Insert sample data into tasks
INSERT INTO tasks (project_id, status)
VALUES (1, 'Open'),
       (1, 'In Progress'),
       (2, 'Completed');

-- Insert sample data into assignments
INSERT INTO assignments (assigner_id, receiver_id, task_id, title, description)
VALUES (3, 2, 1, 'Initial Setup', 'Set up the project infrastructure.'),
       (3, 2, 2, 'Feature Development', 'Develop the main feature of the app.');

-- Insert sample data into medias
INSERT INTO medias (assignment_id, type, path)
VALUES (1, 'IMAGE', '/uploads/images/setup_diagram.png'),
       (2, 'VIDEO', '/uploads/videos/feature_demo.mp4');
