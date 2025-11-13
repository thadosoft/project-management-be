CREATE TABLE if not exists event_participants (
                                    event_id BIGINT NOT NULL,
                                    employee_id BIGINT NOT NULL,
                                    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                    PRIMARY KEY (event_id, employee_id),

                                    CONSTRAINT fk_event_participants_event
                                        FOREIGN KEY (event_id)
                                            REFERENCES events(id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE,

                                    CONSTRAINT fk_event_participants_employee
                                        FOREIGN KEY (employee_id)
                                            REFERENCES employee(id)
                                            ON DELETE CASCADE
                                            ON UPDATE CASCADE
);
