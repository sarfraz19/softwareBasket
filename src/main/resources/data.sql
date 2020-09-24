insert into alien values (101,'Navin');
insert into software_available values (1,1,'software1');
insert into software_details values (1,'0',false,1,'1');
insert into software_details values (2,'20',true,1,'2');
insert into software_available values (2,2,'software2');
insert into software_details values (3,'0',false,2,'1');
insert into software_details values (4,'20',true,2,'2');
DROP SEQUENCE PRODUCT_ID_SEQ;

CREATE SEQUENCE PRODUCT_ID_SEQ
  MINVALUE 1
  MAXVALUE 9999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 100;

