INSERT INTO ida_email_conf(id, email_type, recipients, subject, xslt_path) 
VALUES (1, 0 /* FEEDBACK */, 'feedback@server.com', 'Feedback', null);

INSERT INTO ida_email_conf(id, email_type, recipients, subject, xslt_path) 
VALUES (2, 4 /*GET_CREDIT*/, 'get_credit@server.com', 'Get Credit', null);

commit;
