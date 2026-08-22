INSERT IGNORE INTO roles (id, authority) VALUES (1,'ADMIN');
INSERT IGNORE INTO roles (id, authority) VALUES (2,'USER');
INSERT IGNORE INTO users (id,username,password,name,surname,email,active) VALUES (1, 'tokioschool','$2a$10$4eDwRSgEFc1T6efuuDIRHeMuvdFP/SjvboqmwnP1yOTp6GPKRWCUC','Admin','Sistema','admin@tokioschool.com',true);
INSERT IGNORE INTO tb_users_roles (user_id, role_id)
VALUES (1, 1);