--
-- Table structure for table ida_banners
--

CREATE TABLE ida_banners (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  order_number bigint(20) DEFAULT NULL,
  image_id varchar(255) DEFAULT NULL,
  text varchar(255) DEFAULT NULL,
  title varchar(255) DEFAULT NULL,
  url varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_contacts
--

CREATE TABLE ida_contacts (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  order_number bigint(20) DEFAULT NULL,
  contact_id varchar(255) DEFAULT NULL,
  large_value longtext,
  contact_name varchar(255) DEFAULT NULL,
  contact_type varchar(255) DEFAULT NULL,
  value varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY contact_id (contact_id)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_hc_beneficiaries
--

CREATE TABLE ida_hc_beneficiaries (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  beneficiary_id varchar(255) DEFAULT NULL,
  image_id varchar(255) DEFAULT NULL,
  region varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_hc_payment_categories
--

CREATE TABLE ida_hc_payment_categories (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  category varchar(255) DEFAULT NULL,
  image_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY category (category)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_hc_product_types
--

CREATE TABLE ida_hc_product_types (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  image_id varchar(255) DEFAULT NULL,
  internal_name varchar(255) DEFAULT NULL,
  visible_name_en varchar(255) DEFAULT NULL,
  visible_name_ru varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_hc_statuses
--

CREATE TABLE ida_hc_statuses (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  product_kind int(11) DEFAULT NULL,
  status_key varchar(255) DEFAULT NULL,
  status_value_en varchar(255) DEFAULT NULL,
  status_value_ru varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY product_kind (product_kind,status_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_images
--

CREATE TABLE ida_images (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  image varchar(255) DEFAULT NULL,
  image_id varchar(255) DEFAULT NULL,
  platform varchar(255) DEFAULT NULL,
  resolution varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY image_id (image_id,platform,resolution)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_integration_properties
--

CREATE TABLE ida_integration_properties (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  field_name varchar(255) DEFAULT NULL,
  integration_id varchar(255) DEFAULT NULL,
  value varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY integration_id (integration_id,field_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_last_updates
--

CREATE TABLE ida_last_updates (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  entity_name varchar(255) DEFAULT NULL,
  last_update_time bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY entity_name (entity_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_properties
--

CREATE TABLE ida_properties (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  value varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_users
--

CREATE TABLE ida_users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  password varchar(255) DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_job_statuses
--

CREATE TABLE ida_job_statuses (
  job_id varchar(255) NOT NULL,
  status varchar(64) DEFAULT NULL,
  last_update_time bigint(20) DEFAULT NULL,
  PRIMARY KEY (job_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_offices
--

CREATE TABLE		ida_offices (
  office_id		smallint NOT NULL,
  order_num		int NOT NULL,
  zip_code		varchar(255) DEFAULT NULL,
  city		varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  name		varchar(255) DEFAULT NULL,
  address		varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude		decimal(10, 7) DEFAULT NULL,
  longitude		decimal(10, 7) DEFAULT NULL,
  phone		varchar(255) DEFAULT NULL,
  PRIMARY KEY (office_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_atms
--

CREATE TABLE		ida_atms (
  atm_id		smallint NOT NULL,
  order_num		int NOT NULL,
  city		varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  address		varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude		decimal(10, 7) DEFAULT NULL,
  longitude		decimal(10, 7) DEFAULT NULL,
  PRIMARY KEY (atm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table ida_email_conf
--

CREATE TABLE ida_email_conf (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  email_type int(11) DEFAULT NULL,
  recipients varchar(255) DEFAULT NULL,
  subject varchar(255) DEFAULT NULL,
  xslt_path varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email_type (email_type)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
