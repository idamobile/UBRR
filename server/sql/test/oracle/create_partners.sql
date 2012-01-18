CREATE TABLE IDA_PARTNERS(
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
	CONSTRAINT	pkida_partners PRIMARY KEY (PARTNER_ID) 
)
