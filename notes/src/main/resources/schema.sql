DROP TABLE if EXISTS note_lab;
CREATE TABLE note_lab (
  id bigint auto_increment PRIMARY KEY,
  title VARCHAR (255) ,
  content VARCHAR (255),
  date_creation TIMESTAMP ,
  date_edit TIMESTAMP ,
  owner VARCHAR (55),
  userChecked INT (1)
);
