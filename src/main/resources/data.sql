INSERT INTO USERS (name, cellphone, email, password, credits) VALUES
('Archer','11111111111','archer@email.com','$2a$10$3T58Rskdl7Qb19iD3cbkCO7sagiusdrDXFK2keMVbv2dCt3va59fi',0),
('Saber','22222222222','saber@email.com','$2a$10$3T58Rskdl7Qb19iD3cbkCO7sagiusdrDXFK2keMVbv2dCt3va59fi',0),
('Rider','33333333333','rider@email.com','$2a$10$3T58Rskdl7Qb19iD3cbkCO7sagiusdrDXFK2keMVbv2dCt3va59fi',0),
('Caster','44444444444','caster@email.com','$2a$10$3T58Rskdl7Qb19iD3cbkCO7sagiusdrDXFK2keMVbv2dCt3va59fi',0);

INSERT INTO POST (title,message,author_id,dtype,date) VALUES
('Good news!','Hey guys, I bought a new flute!',1,'Post','2021-05-11T13:54:19.2243162'),
('Spread the word!','Have you ever listened to Duda Beat? I love her work!',4,'Post','2021-05-11T13:54:19.2243162');

INSERT INTO COMMENT (message, author_id, post_id, date) VALUES
('Thats great!',2,1,'2021-05-11T13:54:19.2243162'),
('I know right? Shes sooo good!',3,2,'2021-05-11T13:54:19.2243162');