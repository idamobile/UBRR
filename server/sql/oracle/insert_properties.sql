DELETE FROM ida_properties;

-- Host, port, schema properties
INSERT INTO ida_properties(id, name, value)
VALUES(0, 'server.hostname', '127.0.0.1');

INSERT INTO ida_properties(id, name, value)
VALUES(1, 'server.port', '8080');

INSERT INTO ida_properties(id, name, value)
VALUES(2, 'server.http_schema', 'http');

-- Email properties
INSERT INTO ida_properties(id, name, value)
VALUES(3, 'mail.smtp.host', 'smtp.server.com');

INSERT INTO ida_properties(id, name, value)
VALUES(4, 'mail.smtp.port', '25');

INSERT INTO ida_properties(id, name, value)
VALUES(5, 'mail.smtp.user', 'login');

INSERT INTO ida_properties(id, name, value)
VALUES(6, 'mail.smtp.pass', 'password');

INSERT INTO ida_properties(id, name, value)
VALUES(7, 'mail.smtp.sender.email', 'sender@server.com');

INSERT INTO ida_properties(id, name, value)
VALUES(8, 'mail.smtp.sender.name', 'NO-REPLY');

INSERT INTO ida_properties(id, name, value)
VALUES(9, 'mail.smtp.use.ssl', 'false');

-- Attachments
INSERT INTO ida_properties(id, name, value)
VALUES(10, 'ida.attachments.path', '/home/idauser/tomcat/webapps/web-console/WEB-INF/application/data/attachments/');

-- Page sizes
INSERT INTO ida_properties(id, name, value)
VALUES(11, 'idaserver.locations.pageSize', '500');

INSERT INTO ida_properties(id, name, value)
VALUES(12, 'idaserver.banners.pageSize', '5');
