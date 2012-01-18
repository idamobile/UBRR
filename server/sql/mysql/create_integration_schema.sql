--
-- Table structure for table idb_hc_offices
--

CREATE TABLE		idb_hc_offices (
  office_id		smallint NOT NULL,
  order_num		int NOT NULL,
  zip_code		varchar(255) DEFAULT NULL,
  city		varchar(255) DEFAULT NULL,
  subway_station	varchar(255) DEFAULT NULL,
  name		varchar(255) DEFAULT NULL,
  office_type		varchar(16) DEFAULT NULL,
  address		varchar(255) DEFAULT NULL,
  operation_time	varchar(255) DEFAULT NULL,
  latitude		decimal(10, 7) DEFAULT NULL,
  longitude		decimal(10, 7) DEFAULT NULL,
  phone		varchar(255) DEFAULT NULL,
  manager		varchar(255) DEFAULT NULL,
  affiliate		varchar(255) DEFAULT NULL,
  driving_directions	text DEFAULT NULL,
  has_us_dollars	enum('yes', 'no') DEFAULT 'no',
  has_western_union	enum('yes', 'no') DEFAULT 'no',
  has_deposits	enum('yes', 'no') DEFAULT 'no',
  has_atm		enum('yes', 'no') DEFAULT 'no',
  PRIMARY KEY (office_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table idb_hc_atms
--

CREATE TABLE		idb_hc_atms (
  atm_id				smallint NOT NULL,
  order_num				int NOT NULL,
  city					varchar(255) DEFAULT NULL,
  subway_station		varchar(255) DEFAULT NULL,
  address				varchar(255) DEFAULT NULL,
  operation_time		varchar(255) DEFAULT NULL,
  latitude				decimal(10, 7) DEFAULT NULL,
  longitude				decimal(10, 7) DEFAULT NULL,
  driving_directions	text DEFAULT NULL,
  has_rur				enum('yes', 'no') DEFAULT 'no',
  has_usd				enum('yes', 'no') DEFAULT 'no',
  has_eur				enum('yes', 'no') DEFAULT 'no',
  supports_mobile		enum('yes', 'no') DEFAULT 'no',
  supports_balance		enum('yes', 'no') DEFAULT 'no',
  supports_internet		enum('yes', 'no') DEFAULT 'no',
  supports_utilities	enum('yes', 'no') DEFAULT 'no',
  accepts_cash			enum('yes', 'no') DEFAULT 'no',
  PRIMARY KEY (atm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

