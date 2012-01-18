SELECT 'Executing delete_schema.sql...';
\i delete_schema.sql;

SELECT 'Executing create_procedures.sql...';
\i create_procedures.sql;

SELECT 'Executing quartz/tables_mysql.sql...';
\i quartz/tables_postgres.sql;

SELECT 'Executing create_schema.sql...';
\i create_schema.sql;

SELECT 'Executing create_integration_schema.sql...';
\i create_integration_schema.sql;

SELECT 'Executing hcb_discounts.sql...';
\i hcb_discounts.sql;

SELECT 'Executing hcb_insert_discount_categories.sql...';
\i hcb_insert_discount_categories.sql;

SELECT 'Executing insert_contacts.sql...';
\i insert_contacts.sql;

SELECT 'Executing insert_hc_statuses.sql...';
\i insert_hc_statuses.sql;

SELECT 'Executing insert_last_update_time.sql...';
\i insert_last_update_time.sql;

SELECT 'ALL.SQL completed!';
