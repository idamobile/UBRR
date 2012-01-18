DELETE FROM ida_last_updates;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 1, 'contacts',  current_millis() FROM dual;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 2, 'banners',  current_millis() FROM dual;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 3, 'locations.offices',  current_millis() FROM dual;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 4, 'locations.atms',  current_millis() FROM dual;

INSERT INTO ida_last_updates (id, entity_name, last_update_time) 
SELECT 5, 'locations.credit.points',  current_millis() FROM dual;
