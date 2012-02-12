CREATE SEQUENCE hibernate_sequence
 START WITH     1000
 INCREMENT BY   1
 NOCACHE
 NOCYCLE;
 
--
-- Table structure for table `ida_banners`
--

CREATE TABLE ida_banners (
  id            NUMBER(20) NOT NULL,
  order_number  NUMBER(20) DEFAULT NULL,
  image_id      VARCHAR(255) DEFAULT NULL,
  text          VARCHAR(255) DEFAULT NULL,
  title         VARCHAR(255) DEFAULT NULL,
  url           VARCHAR(255) DEFAULT NULL,
  CONSTRAINT    pkida_banners primary key (id)
);

--
-- Table structure for table `ida_contacts`
--

CREATE TABLE ida_contacts (
  id            NUMBER(20) NOT NULL,
  order_number  NUMBER(20) DEFAULT NULL,
  contact_id    VARCHAR(255) DEFAULT NULL,
  large_value   CLOB,
  contact_name  VARCHAR(255) DEFAULT NULL,
  contact_type  VARCHAR(255) DEFAULT NULL,
  value         VARCHAR(255) DEFAULT NULL,
  CONSTRAINT    pkida_contacts primary key (id),
  CONSTRAINT    entity_contact_id UNIQUE (contact_id)  
);

--
-- Table structure for table `ida_images`
--

CREATE TABLE ida_images (
  id          NUMBER(20) NOT NULL,
  image       VARCHAR(255) DEFAULT NULL,
  image_id    VARCHAR(255) DEFAULT NULL,
  platform    VARCHAR(255) DEFAULT NULL,
  resolution  VARCHAR(255) DEFAULT NULL,
  CONSTRAINT  pkida_images PRIMARY KEY (id),
  CONSTRAINT  entity_image UNIQUE (image_id, platform, resolution)
);

CREATE OR REPLACE TRIGGER ida_image_id BEFORE INSERT ON ida_images FOR EACH ROW
BEGIN
	IF (:new.ID IS NULL) THEN
		SELECT hibernate_sequence.nextval INTO :new.ID FROM dual;
	END IF;
END;
/


--
-- Table structure for table `ida_integration_properties`
--
/*
CREATE TABLE ida_integration_properties (
  id              NUMBER(20) NOT NULL,
  field_name      VARCHAR(255) DEFAULT NULL,
  integration_id  VARCHAR(255) DEFAULT NULL,
  value           VARCHAR(255) DEFAULT NULL,
  CONSTRAINT      pkida_ip PRIMARY KEY (id),
  CONSTRAINT      entity_ip UNIQUE(integration_id,field_name)
);
*/
--
-- Table structure for table `ida_last_updates`
--

CREATE TABLE ida_last_updates (
  id                NUMBER(20) NOT NULL,
  entity_name       VARCHAR(255) DEFAULT NULL,
  last_update_time  NUMBER(20) DEFAULT NULL,
  CONSTRAINT        pkida_lu PRIMARY KEY (id),
  CONSTRAINT        entity_lu UNIQUE (entity_name)
);

--
-- Table structure for table `ida_properties`
--

CREATE TABLE ida_properties (
  id          NUMBER(20) NOT NULL,
  name        VARCHAR(255) DEFAULT NULL,
  value       VARCHAR(255) DEFAULT NULL,
  CONSTRAINT  pkida_props PRIMARY KEY (id)
);

--
-- Table structure for table `ida_users`
--
/*
CREATE TABLE ida_users (
  id          NUMBER(20) NOT NULL,
  password    VARCHAR(255) DEFAULT NULL,
  username    VARCHAR(255) DEFAULT NULL,
  CONSTRAINT  pkida_users PRIMARY KEY (id)
);
*/
--
-- Table structure for table `ida_job_statuses`
--
/*
CREATE TABLE ida_job_statuses (
  job_id              VARCHAR(255) NOT NULL,
  status              VARCHAR(64) DEFAULT NULL,
  last_update_time    NUMBER(20) DEFAULT NULL,
  CONSTRAINT          pkida_js PRIMARY KEY (job_id)
);
*/
--
-- Table structure for table `ida_offices`
--
/*
CREATE TABLE		ida_offices (
  office_id		    NUMBER(20) NOT NULL,
  order_num		    NUMBER(20) NOT NULL,
  zip_code		    VARCHAR(255) DEFAULT NULL,
  city    		    VARCHAR(255) DEFAULT NULL,
  subway_station	    VARCHAR(255) DEFAULT NULL,
  name		            VARCHAR(255) DEFAULT NULL,
  address	  	    VARCHAR(255) DEFAULT NULL,
  operation_time	    VARCHAR(255) DEFAULT NULL,
  latitude		    NUMBER(10, 7) DEFAULT NULL,
  longitude	      	    NUMBER(10, 7) DEFAULT NULL,
  phone		            VARCHAR(255) DEFAULT NULL,
  services		    VARCHAR(255) DEFAULT NULL,
  CONSTRAINT      pkida_offices PRIMARY KEY (office_id)
);
*/
--
-- Table structure for table `ida_atms`
--
/*
CREATE TABLE      ida_atms (
  atm_id	           NUMBER(20) NOT NULL,
  order_num 		   NUMBER(20) NOT NULL,
  city            	   VARCHAR(255) DEFAULT NULL,
  subway_station  	   VARCHAR(255) DEFAULT NULL,
  name		           VARCHAR(255) DEFAULT NULL,
  address         	   VARCHAR(255) DEFAULT NULL,
  operation_time  	   VARCHAR(255) DEFAULT NULL,
  latitude        	   NUMBER(10, 7) DEFAULT NULL,
  longitude       	   NUMBER(10, 7) DEFAULT NULL,
  services		   VARCHAR(255) DEFAULT NULL,
  CONSTRAINT      pkida_atms  PRIMARY KEY (atm_id)
);
*/
--
-- Table structure for table `ida_email_conf`
--

CREATE TABLE  ida_email_conf (
  id          NUMBER(20) NOT NULL,
  email_type  NUMBER(11) DEFAULT NULL,
  recipients  VARCHAR(255) DEFAULT NULL,
  subject     VARCHAR(255) DEFAULT NULL,
  xslt_path   VARCHAR(255) DEFAULT NULL,
  CONSTRAINT  pkida_econf PRIMARY KEY (id),
  CONSTRAINT  entity_email UNIQUE (email_type)
);

--
-- Table structure for table `ida_tos_integrations`
--
/*
DROP TABLE	ida_tos_integrations;

CREATE TABLE	ida_tos_integrations (
	id				NUMBER(20) NOT NULL,
	integration		VARCHAR(255) NOT NULL,
	url				VARCHAR(255) DEFAULT NULL,
	cron_schedule	VARCHAR(255) DEFAULT NULL,
	CONSTRAINT	pkida_tos_int	PRIMARY KEY (id),
	CONSTRAINT	entity_tos_int	UNIQUE (integration)
);
*/
--
-- Table structure for table `ida_currency_rates`
--
/*
DROP TABLE	ida_currency_rates;

CREATE TABLE	ida_currency_rates (
	operation		SMALLINT NOT NULL,
	currency_code	SMALLINT NOT NULL,
	price			NUMBER(10, 7) NOT NULL,
	amount			NUMBER(10, 7) NOT NULL,
	delta			NUMBER(10, 7) NOT NULL,
	
	CONSTRAINT	entity_currency_rates UNIQUE (operation, currency_code)
);
*/
--
-- Table structure for table `ida_currency_rates`
--
/*
DROP TABLE	ida_news;

CREATE TABLE	ida_news (
	news_id			VARCHAR(255) NOT NULL,
	title			VARCHAR(255) NOT NULL,
	preview			VARCHAR(2000) DEFAULT NULL,
	html_body		VARCHAR(4000) DEFAULT NULL,
	url				VARCHAR(1024) DEFAULT NULL,
	category		VARCHAR(255) DEFAULT NULL,
	creation_date	NUMBER(20) DEFAULT NULL,
	
	CONSTRAINT	pk_ida_news PRIMARY KEY (news_id)
);
*/
