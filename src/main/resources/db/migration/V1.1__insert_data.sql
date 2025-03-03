-- Insert into users
INSERT INTO users (id, role, name, email, username, password, phone_number)
VALUES ('a47108bd-e6cc-413c-8394-9304d24b152b', 'ADMIN', 'Alice Admin', 'alice.admin@example.com', 'alice_admin', '$2a$10$0BJvu.L1HBG6bJcUpnlI5eV8IYgx5I2XSmu4wcHqJl.NaWoTkQyp6', '1234567890'),
       ('8843b7b1-2b84-4dcd-9ded-5359e5e1bcc5', 'USER', 'Bob Manager', 'bob.manager@example.com', 'bob_manager', '$2a$10$0BJvu.L1HBG6bJcUpnlI5eV8IYgx5I2XSmu4wcHqJl.NaWoTkQyp6', '1234567891'),
       ('c01369c5-8d10-4bf0-98bb-20ee7f71a8e0', 'USER', 'Charlie User', 'charlie.user@example.com', 'charlie_user', '$2a$10$0BJvu.L1HBG6bJcUpnlI5eV8IYgx5I2XSmu4wcHqJl.NaWoTkQyp6', '1234567892');

-- Insert into projects
INSERT INTO projects (id, user_id, name, description)
VALUES ('721588e9-6f2b-4766-a122-ef1034bf1c7e', 'a47108bd-e6cc-413c-8394-9304d24b152b', 'Project Alpha', 'Description of Project Alpha'),
       ('d927b674-ae8f-4f4e-bde1-1754041fc2b0', '8843b7b1-2b84-4dcd-9ded-5359e5e1bcc5', 'Project Beta', 'Description of Project Beta');

-- Insert into tasks
INSERT INTO tasks (id, project_id, status, task_order)
VALUES ('804575a5-4b0d-4509-b8f2-a7d81d6a2262', '721588e9-6f2b-4766-a122-ef1034bf1c7e', 'Pending', 1),
       ('86f60016-f7b1-4ca4-829c-d3059c3e3c65', '721588e9-6f2b-4766-a122-ef1034bf1c7e', 'In Progress', 2),
       ('0c38c1c9-df2b-43e0-8594-13870f5b6d6b', '721588e9-6f2b-4766-a122-ef1034bf1c7e', 'Completed', 3),
       ('2e276209-2e19-44d5-8410-204d6b79b52d', '721588e9-6f2b-4766-a122-ef1034bf1c7e', 'Test', 4),
       ('39e6e7e1-4d63-49fa-bb2b-d8896b0ac3c5', 'd927b674-ae8f-4f4e-bde1-1754041fc2b0', 'Pending', 1),
       ('536b091b-50d3-47fa-96d1-d5abaf06b33a', 'd927b674-ae8f-4f4e-bde1-1754041fc2b0', 'Survey', 2),
       ('5b2925ed-e3e8-46c3-8260-550e42c08db9', 'd927b674-ae8f-4f4e-bde1-1754041fc2b0', 'In Progress', 3),
       ('6dffb728-ef58-4e40-911c-ef3f2acfb4e5', 'd927b674-ae8f-4f4e-bde1-1754041fc2b0', 'Completed', 4),
       ('7882b62b-8c31-4350-a506-f6d3cf12ec73', 'd927b674-ae8f-4f4e-bde1-1754041fc2b0', 'Test', 5);

-- Insert into assignments
INSERT INTO assignments (id, assigner_id, receiver_id, task_id, title, description, assignment_order)
VALUES ('a2c68113-3205-45b6-b2d2-1c62286512c9', 'a47108bd-e6cc-413c-8394-9304d24b152b', '8843b7b1-2b84-4dcd-9ded-5359e5e1bcc5', '804575a5-4b0d-4509-b8f2-a7d81d6a2262', 'Initial Setup', 'Setup the environment for Project A', 1),
       ('fc5b94da-184d-4917-96f1-d022cbcb09d2', 'a47108bd-e6cc-413c-8394-9304d24b152b', 'c01369c5-8d10-4bf0-98bb-20ee7f71a8e0', '86f60016-f7b1-4ca4-829c-d3059c3e3c65', 'Complete Module', 'Complete module implementation', 1),
       ('dd478660-6b2a-4c68-a6c7-645e5e7b724c', '8843b7b1-2b84-4dcd-9ded-5359e5e1bcc5', 'c01369c5-8d10-4bf0-98bb-20ee7f71a8e0', '0c38c1c9-df2b-43e0-8594-13870f5b6d6b', 'Review Code', 'Review the submitted code', 1),
       ('37b4a6a8-b8e3-4a0f-8e43-1c5fb5ec8e8a', '8843b7b1-2b84-4dcd-9ded-5359e5e1bcc5', 'a47108bd-e6cc-413c-8394-9304d24b152b', '2e276209-2e19-44d5-8410-204d6b79b52d', 'Update Documentation', 'Update the documentation for the project', 1);

-- Insert into medias
INSERT INTO medias (id, assignment_id, type, path, name, size)
VALUES ('f874e1d6-c3b7-41a3-8e51-4a94d77dc834', 'a2c68113-3205-45b6-b2d2-1c62286512c9', 'IMAGE', '/images/setup.png', 'qua', 12345),
       ('3ddc3e65-3c99-4c6b-a4cd-3808d0bdfe3b', 'fc5b94da-184d-4917-96f1-d022cbcb09d2', 'VIDEO', '/videos/feature.mp4', 'la`', 54321),
       ('b1a28b13-7696-43f3-88f8-87118dcac689', 'dd478660-6b2a-4c68-a6c7-645e5e7b724c', 'IMAGE', '/images/review.png', 'chan', 67890);
