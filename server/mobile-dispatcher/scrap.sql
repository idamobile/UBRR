select * from IDA_PARTNER where partner_id in (SELECT partner_id FROM ida_partner_card WHERE card in ('Card 66')) and city = 'Казань' and subway_station = 'Казань' order by order_num;

select * from ida_news;

select * from IDA_BANNERS;
select * from IDA_CONTACTS;
select * from IDA_LAST_UPDATES where entity_name like 'locations%';

update IDA_BANNERS set order_number = 0 where id =2175;

ALTER TABLE IDA_BANNERS drop CONSTRAINT order_number_unique -- UNIQUE (order_number);

insert into IDA_BANNERS(id, order_number, image_id, text, title,) values(val1,val2);

select * from IDA_imageS
where image_id = '1326923611263';

select card_name from ida_card_products;
select * from IDA_CARD_PRODUCTS;
select * from IDA_PARTNER_CARD;

select count(*) from IDA_PARTNER order by order_num;
select * from IDA_PARTNER order by order_num;

select unique city, subway_station from IDA_PARTNER where city is not null and subway_station is not null order by city, subway_station

select unique short_services from ida_partner;

insert into IDA_PARTNER_CARD (partner_id, card) values (1265, 'Card 66');

select * from ida_properties
where name like 'idaserver%';

update ida_properties set value = '600' where id = 10;
commit;

INSERT INTO ida_properties(id, name, value)
VALUES(11, 'idaserver.locations.pageSize', '600');

INSERT INTO ida_properties(id, name, value) VALUES(16, 'idaserver.partners.pageSize', '15');

alter table ida_partners rename to ida_partner;

select * from ida_properties where id = 9;
update IDA_PROPERTIES set value = '/home/homecredit-dev/tomcat/apache-tomcat-7.0.21/webapps/web-console/WEB-INF/application/data/attachments/' where id = 9;

ALTER TABLE ida_currency_rates ADD (cur_date NUMBER(20));
update IDA_CURRENCY_RATES set cur_date = 1326923611265 where operation = 2;

select * from IDA_CURRENCY_RATES;

CREATE INDEX partners_lat_idx ON IDA_PARTNER (latitude);
CREATE INDEX partners_lng_idx ON IDA_PARTNER (longitude);
commit;

select p.partner_id, card from ida_partner p , ida_partner_card pc where p.partner_id = pc.partner_id and p.partner_id < 15 order by p.partner_id, pc.card;
select partner_id, card, order_num from ida_partner p LEFT OUTER JOIN ida_partner_card pc on p.partner_id = pc.partner_id where p.partner_id < 15 order by p.partner_id, pc.card;

select partner_id from ida_partner where partner_id < 15;

delete from ida_partner_card where partner_id = 1;
