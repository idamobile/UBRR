DELETE FROM ida_last_updates;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 1, 'contacts',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 2, 'banners',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 3, 'locations.offices',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 4, 'locations.atms',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 5, 'statuses',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 6, 'productTypes',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 7, 'payment.categories',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 8, 'beneficiaries',  extract(epoch from date_trunc('milliseconds', current_timestamp))*1000;