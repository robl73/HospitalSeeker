INSERT INTO role (id, type) VALUES (1, 'ADMIN');
INSERT INTO role (id, type) VALUES (2, 'PATIENT');
INSERT INTO role (id, type) VALUES (3, 'MANAGER');
INSERT INTO role (id, type) VALUES (4, 'DOCTOR');
INSERT INTO admintokenconfig (id, token, value) VALUES (1, 'RESET_PASSWORD_TOKEN_EXPIRATION', 24);
INSERT INTO admintokenconfig (id, token, value) VALUES (2, 'VERIFICATION_TOKEN_EXPIRATION', 24);
INSERT INTO admintokenconfig (id, token, value) VALUES (3, 'REMEMBER_ME_TOKEN_EXPIRATION', 24);