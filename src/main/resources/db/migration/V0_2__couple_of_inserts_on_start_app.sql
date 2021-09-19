insert into packs (name) values('started pack');

insert into blocks (id_pack, name, type_code)
(select id, '1st started block', 1 from packs limit 1);
insert into blocks (id_pack, name, type_code)
(select id, '2nd started block', 2 from packs limit 1);

insert into text_blocks (id, text)
(select id, 'started text.' from blocks where type_code = 1 limit 1);

insert into local_date_blocks (id, first_date, second_date)
(select id, '1969-01-01', '1969-12-31' from blocks where type_code = 2 limit 1);