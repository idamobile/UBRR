SELECT 'Executing delete_schema.sql...' AS ' ';
source delete_schema.sql;

SELECT 'Executing quartz/tables_mysql.sql...' AS ' ';
source quartz/tables_mysql.sql;

SELECT 'Executing create_schema.sql...' AS ' ';
source create_schema.sql;

SELECT 'Executing create_integration_schema.sql...' AS ' ';
source create_integration_schema.sql;

SELECT 'Executing insert_contacts.sql...' AS ' ';
source insert_contacts.sql;

SELECT 'Executing insert_hc_statuses.sql...' AS ' ';
source insert_hc_statuses.sql;

SELECT 'ALL.SQL completed!' AS ' ';
