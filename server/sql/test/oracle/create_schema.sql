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

CREATE TABLE	ida_currency_rates (
	operation		SMALLINT NOT NULL,
	currency_code	SMALLINT NOT NULL,
	price			NUMBER(10, 7) NOT NULL,
	amount			NUMBER(10, 7) NOT NULL,
	delta			NUMBER(10, 7) NOT NULL,
	cur_date 		NUMBER(20),
	
	CONSTRAINT	entity_currency_rates UNIQUE (operation, currency_code)
);

CREATE TABLE		ida_pay_cred (
  pay_cred_id		    NUMBER(20) NOT NULL,
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
  CONSTRAINT      pkida_pay_cred PRIMARY KEY (pay_cred_id)
);

CREATE TABLE IDA_PARTNER (
	PARTNER_ID    			NUMBER(20) NOT NULL,     
	ORDER_NUM      			NUMBER(20) NOT NULL,    
	ZIP_CODE                VARCHAR2(255), 
	CITY                    VARCHAR2(255), 
	SUBWAY_STATION          VARCHAR2(255), 
	NAME                    VARCHAR2(255), 
	ADDRESS                 VARCHAR2(255), 
	OPERATION_TIME          VARCHAR2(255), 
	LATITUDE                NUMBER(10,7),  
	LONGITUDE               NUMBER(10,7),  
	PHONE                   VARCHAR2(255), 
	SERVICES                VARCHAR2(255),
	short_services			VARCHAR(255) DEFAULT NULL,
	CONSTRAINT	pkida_partner PRIMARY KEY (PARTNER_ID) 
);

CREATE TABLE ida_card_products (
  card_name		VARCHAR(255) NOT NULL,
  CONSTRAINT	pkida_card_prod PRIMARY KEY (card_name)
);

create table IDA_PARTNER_CARD (
	partner_id int not null,
	card varchar(255) not null
);