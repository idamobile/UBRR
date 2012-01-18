
--
-- Table structure for table `idb_hc_offices`
--

CREATE TABLE		idb_hc_offices (
  office_id		NUMBER(20) NOT NULL,
  order_num		NUMBER(20) NOT NULL,
  zip_code		varchar(255) DEFAULT NULL,
  city		varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  name	varchar(255) DEFAULT NULL,
  office_type		varchar(16) DEFAULT NULL,
  address		varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude		NUMBER(10, 7) DEFAULT NULL,
  longitude		NUMBER(10, 7) DEFAULT NULL,
  phone		varchar(255) DEFAULT NULL,
  manager		varchar(255) DEFAULT NULL,
  affiliate		varchar(255) DEFAULT NULL,
  driving_directions	CLOB DEFAULT NULL,
  has_us_dollars	varchar(20),
  has_western_union varchar(20),
  has_deposits varchar(20),
  has_atm varchar(20),
  CONSTRAINT pkidb_hc_offices PRIMARY KEY (office_id)
);

--
-- Table structure for table `idb_hc_atms`
--

CREATE TABLE		idb_hc_atms (
  atm_id		smallint NOT NULL,
  order_num		int NOT NULL,
  city		varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  address		varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude		NUMBER(10, 7) DEFAULT NULL,
  longitude		NUMBER(10, 7) DEFAULT NULL,
  driving_directions varchar(255),
  has_rur varchar(20),
  has_usd varchar(20),
  has_eur varchar(20),
  supports_mobile varchar(20),
  supports_balance varchar(20),
  supports_utilities varchar(20),
  accepts_cash varchar(20),
  CONSTRAINT      pkidb_hc_atms PRIMARY KEY (atm_id)
);

--
-- Table structure for table IDB_HC_PARTNERS
--

CREATE TABLE	IDB_HC_PARTNERS (
	PARTNER_ID			NUMBER(20) NOT NULL,
	TITLE				VARCHAR(255) DEFAULT NULL,
	SUBTITLE			VARCHAR(512) DEFAULT NULL,
	DISCOUNT			VARCHAR(1024) DEFAULT NULL,
	DESCRIPTION			VARCHAR(4000) DEFAULT NULL,
	PHONE_NUMBER		VARCHAR(255) DEFAULT NULL,
	URL					VARCHAR(255) DEFAULT NULL,
	CATEGORY			VARCHAR(255) DEFAULT NULL,
	DISCOUNT1			VARCHAR(512) DEFAULT NULL,
	DESCRIPTION1		VARCHAR(4000) DEFAULT NULL,
	IS_ENTERPRISE		VARCHAR(3) DEFAULT 'no',
	MANAGER_NAME		VARCHAR(255) DEFAULT NULL,
	MGR_POSITION		VARCHAR(255) DEFAULT NULL,
	MGR_PHONE			VARCHAR(255) DEFAULT NULL,
	MGR_EMAIL			VARCHAR(255) DEFAULT NULL,
	LOGO_URL			VARCHAR(255) DEFAULT NULL,
	CONSTRAINT	pkidb_hc_partners PRIMARY KEY (PARTNER_ID)
);

--
-- Table structure for table IDB_HC_SHOPS
--

CREATE TABLE	IDB_HC_SHOPS (
	SHOP_ID				NUMBER(20) NOT NULL,
	CITY				VARCHAR(255) DEFAULT NULL,
	NAME				VARCHAR(255) DEFAULT NULL,
	LOCATION			VARCHAR(255) DEFAULT NULL,
	ADDRESS				VARCHAR(2024) DEFAULT NULL,
	PHONE_NUMBER		VARCHAR(255) DEFAULT NULL,
	DISCOUNT			VARCHAR(512) DEFAULT NULL,
	DESCRIPTION			VARCHAR(4000) DEFAULT NULL,
	CATEGORY			VARCHAR(255) DEFAULT NULL,
	PARTNER_ID			NUMBER(20) NOT NULL,
	PARTNER_NAME		VARCHAR(255) DEFAULT NULL,
	MERCH_NAME			VARCHAR(255) DEFAULT NULL,
	MERCH_ID			VARCHAR(255) DEFAULT NULL,
	CONSTRAINT	pkidb_hc_shops PRIMARY KEY (SHOP_ID)
);
