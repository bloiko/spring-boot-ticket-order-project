insert into security_role(id, role_name) VALUES (1, 'ROLE_USER'),(2, 'ROLE_ADMIN');
insert into security_user(id, password, username) VALUES (1, '$2a$12$oEzECkHSqWlZXLatMx4cVO7hapbkitgbcwaRDMMdBfSyEsqFsnaxC', 'bohdan');
insert into user_role(user_id, role_id) values (1, 1),(1, 2);