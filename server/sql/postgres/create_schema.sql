CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 4
  CACHE 1;
ALTER TABLE hibernate_sequence OWNER TO hc_mobile;

--
-- Table structure for table ida_banners
--

CREATE TABLE ida_banners (
  id 				BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  order_number 		BIGINT DEFAULT NULL,
  image_id			varchar(255) DEFAULT NULL,
  text				varchar(255) DEFAULT NULL,
  title				varchar(255) DEFAULT NULL,
  url				varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);

--
-- Table structure for table ida_contacts
--

CREATE TABLE ida_contacts (
  id 				BIGINT NOT NULL,
  order_number		BIGINT,
  contact_id		varchar(255) DEFAULT NULL,
  large_value		varchar(1024),
  contact_name		varchar(255) DEFAULT NULL,
  contact_type		varchar(255) DEFAULT NULL,
  "value"			varchar(255) DEFAULT NULL,
  CONSTRAINT ida_contacts_pkey PRIMARY KEY (id),
  CONSTRAINT ida_contacts_contact_id_key UNIQUE (contact_id)
) ;


--
-- Table structure for table ida_hc_beneficiaries
--

CREATE TABLE ida_hc_beneficiaries (
  id 				BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  beneficiary_id 	varchar(255) DEFAULT NULL,
  image_id 			varchar(255) DEFAULT NULL,
  region 			varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

--
-- Table structure for table ida_hc_payment_categories
--

CREATE TABLE ida_hc_payment_categories (
  id 		BIGINT NOT NULL ,
  category	varchar(255) DEFAULT NULL,
  image_id	varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (category)
) ;

--
-- Table structure for table ida_hc_product_types
--

CREATE TABLE ida_hc_product_types (
  id 				BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  image_id 			varchar(255) DEFAULT NULL,
  internal_name varchar(255) DEFAULT NULL,
  visible_name_en 	varchar(255) DEFAULT NULL,
  visible_name_ru 	varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

--
-- Table structure for table ida_hc_statuses
--

CREATE TABLE ida_hc_statuses (
  id				BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  product_kind		SMALLINT DEFAULT NULL,
  status_key		varchar(255) DEFAULT NULL,
  status_value_en	varchar(255) DEFAULT NULL,
  status_value_ru	varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (product_kind,status_key)
) ;

--
-- Table structure for table ida_images
--

CREATE TABLE ida_images (
  id 			BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  image 		varchar(255) DEFAULT NULL,
  image_id		varchar(255) DEFAULT NULL,
  platform		varchar(255) DEFAULT NULL,
  resolution	varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (image_id,platform,resolution)
) ;

--
-- Table structure for table ida_integration_properties
--

CREATE TABLE ida_integration_properties (
  id				BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  field_name		varchar(255) DEFAULT NULL,
  integration_id 	varchar(255) DEFAULT NULL,
  value 			varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (integration_id, field_name)
) ;

--
-- Table structure for table ida_last_updates
--

CREATE TABLE ida_last_updates (
  id				BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  entity_name		varchar(255) DEFAULT NULL,
  last_update_time	BIGINT DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (entity_name)
) ;

--
-- Table structure for table ida_properties
--

CREATE TABLE ida_properties (
  id			BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  name			varchar(255) DEFAULT NULL,
  value 		varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

--
-- Table structure for table ida_users
--

CREATE TABLE ida_users (
  id 		BIGINT NOT NULL DEFAULT NEXTVAL('hibernate_sequence'),
  password	varchar(255) DEFAULT NULL,
  username	varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;

--
-- Table structure for table ida_job_statuses
--

CREATE TABLE ida_job_statuses (
  job_id			varchar(255) NOT NULL,
  status			varchar(64) DEFAULT NULL,
  last_update_time	BIGINT DEFAULT NULL,
  PRIMARY KEY (job_id)
) ;

--
-- Table structure for table ida_offices
--

CREATE TABLE		ida_offices (
  office_id			SMALLINT NOT NULL,
  order_num			INTEGER NOT NULL,
  zip_code			varchar(255) DEFAULT NULL,
  city				varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  name				varchar(255) DEFAULT NULL,
  address			varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude			decimal(10, 7) DEFAULT NULL,
  longitude			decimal(10, 7) DEFAULT NULL,
  phone				varchar(255) DEFAULT NULL,
  PRIMARY KEY (office_id)
) ;

--
-- Table structure for table ida_atms
--

CREATE TABLE		ida_atms (
  atm_id			SMALLINT NOT NULL,
  order_num			INTEGER NOT NULL,
  city				varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  address			varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude			decimal(10, 7) DEFAULT NULL,
  longitude			decimal(10, 7) DEFAULT NULL,
  PRIMARY KEY (atm_id)
) ;

--
-- Table structure for table ida_email_conf
--

CREATE TABLE ida_email_conf (
  id 			INTEGER NOT NULL ,
  email_type	SMALLINT DEFAULT NULL,
  recipients	varchar(255) DEFAULT NULL,
  subject		varchar(255) DEFAULT NULL,
  xslt_path		varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE (email_type)
) ;
 