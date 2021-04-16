SET MODE MySQL;

INSERT IGNORE  INTO Company (ID,STATUS, STATUS_MESSAGE)
VALUES (1,true, '');


-- date has to be defined like 6.1 not 06.01

INSERT IGNORE  INTO OPENNING_HOURS  (ID,DESCRIPTION,OPENNING_HOURS, CLOSING_HOURS,MATCHER, PRIORITY)
VALUES
(0,'General openning hours',10,20,'',3),
(1,'Pondelok',-1,-1,'1',2),
(2,'Utorok',-1,-1,'2',2),
(3,'Streda',-1,-1,'3',2),
(4,'Štvrtok',-1,-1,'4',2),
(5,'Piatok',-1,-1,'5',2),
(6,'Sobota',-1,-1,'6',2),
(7,'Nedeľa',10,11,'7',2);

INSERT IGNORE  INTO OPENNING_HOURS  (ID,DESCRIPTION,OPENNING_HOURS, CLOSING_HOURS,MATCHER, PRIORITY)
VALUES
(8,'Special',-1,-1,'6.1',1);

-- 9999 instead of this number put there your telephone number
insert into orders (is_paid, comment, name, street, telephone_number, order_text, id) values (false , '', 'test', 'test', 9999, 'test', 11111)
