-- Insert into roles
INSERT INTO roles (name)
VALUES ('Admin'),
       ('Manager'),
       ('User');

-- Insert into users
INSERT INTO users (role_id, name, email, username, password, phone_number)
VALUES (1, 'Alice Admin', 'alice.admin@example.com', 'alice_admin', '$2a$10$0BJvu.L1HBG6bJcUpnlI5eV8IYgx5I2XSmu4wcHqJl.NaWoTkQyp6', '1234567890'),
       (2, 'Bob Manager', 'bob.manager@example.com', 'bob_manager', '$2a$10$0BJvu.L1HBG6bJcUpnlI5eV8IYgx5I2XSmu4wcHqJl.NaWoTkQyp6', '1234567891'),
       (3, 'Charlie User', 'charlie.user@example.com', 'charlie_user', '$2a$10$0BJvu.L1HBG6bJcUpnlI5eV8IYgx5I2XSmu4wcHqJl.NaWoTkQyp6', '1234567892');

-- Insert into projects
INSERT INTO projects (user_id, name, description)
VALUES (1, 'Project Alpha', 'Description of Project Alpha'),
       (2, 'Project Beta', 'Description of Project Beta');

-- Insert into tasks
INSERT INTO tasks (project_id, status, task_order)
VALUES (1, 'Pending', 1),
       (1, 'In Progress', 2),
       (1, 'Completed', 3),
       (1, 'Test', 4),
       (2, 'Pending', 1),
       (2, 'Survey', 2),
       (2, 'In Progress', 3),
       (2, 'Completed', 4),
       (2, 'Test', 5);

-- Insert into assignments
INSERT INTO assignments (assigner_id, receiver_id, task_id, title, description, status, assignment_order)
VALUES (1, 2, 1, 'Initial Setup', 'Setup the environment for Project A', 'Pending', 1),
       (1, 3, 2, 'Complete Module', 'Complete module implementation', 'In Progress', 1),
       (2, 3, 3, 'Review Code', 'Review the submitted code', 'Completed', 1),
       (2, 1, 4, 'Update Documentation', 'Update the documentation for the project', 'Test', 1),
       (3, 2, 1, 'Bug Fix', 'Fix reported bugs in module', 'Pending', 2),
       (1, 3, 2, 'Design API', 'Design the RESTful API for the project', 'In Progress', 2),
       (2, 3, 3, 'Write Unit Tests', 'Add unit tests for all modules', 'Completed', 2),
       (3, 2, 1, 'Integrate Frontend', 'Integrate the frontend with the backend', 'Pending', 3),
       (1, 2, 2, 'Optimize Code', 'Optimize code for better performance', 'In Progress', 3),
       (2, 3, 1, 'Test Deployment', 'Test the deployment process', 'Pending', 5),
       (3, 1, 5, 'Fix CSS Issues', 'Resolve CSS issues in the UI', 'Pending', 1),
       (1, 3, 6, 'Database Migration', 'Migrate database to the new schema', 'Survey', 1),
       (1, 3, 7, 'Database Migration', 'Migrate database to the latest schema', 'In Progress', 1),
       (1, 3, 8, 'Database Migration', 'Migrate database to the old schema', 'Completed', 1),
       (1, 3, 9, 'Database Migration', 'Migrate database to the test schema', 'Test', 1),
       (1, 3, 7, 'Database Migration', 'Migrate database to the example schema', 'In Progress', 2),
       (1, 3, 8, 'Database Migration', 'Migrate database to the product schema', 'Completed', 2),
       (2, 1, 6, 'Client Presentation', 'Prepare slides for client presentation', 'Survey', 2);

-- Insert into medias
INSERT INTO medias (assignment_id, type, path)
VALUES (1, 'IMAGE', '/images/setup.png'),
       (2, 'VIDEO', '/videos/feature.mp4'),
       (3, 'IMAGE', '/images/review.png');
