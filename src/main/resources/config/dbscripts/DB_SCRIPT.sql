
# Change this name when running the script for a different tenant
use amin;

DROP TABLE IF EXISTS `deal`;
CREATE TABLE `deal` (
  `id` char(36) CHARACTER SET ascii NOT NULL,
  `tenant_id` char(36) CHARACTER SET ascii NOT NULL,
  `property_id` char(36) CHARACTER SET ascii NOT NULL,  
  `buyer_id` bigint NOT NULL, 
  `broker_id` bigint NOT NULL,   
  `nda_id` char(36) CHARACTER SET ascii,
  `status_id` int NOT NULL,   
  `status_name` varchar(50) NOT NULL,     
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `id` char(36) CHARACTER SET ascii NOT NULL,
  `tenant_id` char(36) CHARACTER SET ascii NOT NULL,
  `metadata` json NOT NULL, 
  `status` varchar(50) NOT NULL,     
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
    `id` CHAR(36)CHARACTER SET ASCII NOT NULL,
    `server_name` VARCHAR(50) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);


ALTER TABLE `property` ADD FOREIGN KEY `fk-property-tenant_id`(`tenant_id`) REFERENCES tenant(`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

CREATE INDEX `ix-property-tenant_id-id` on `property`(`tenant_id`,`id`);
CREATE INDEX `ix-tenant-server_name` on `tenant`(`server_name`);

ALTER TABLE `deal` ADD FOREIGN KEY `fk-deal-tenant_id`(`tenant_id`) REFERENCES tenant(`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
ALTER TABLE `deal` ADD FOREIGN KEY `fk-deal-buyer_id`(`buyer_id`) REFERENCES jhi_user(`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
ALTER TABLE `deal` ADD FOREIGN KEY `fk-deal-broker_id`(`broker_id`) REFERENCES jhi_user(`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
ALTER TABLE `deal` ADD FOREIGN KEY `fk-deal-property_id`(`property_id`) REFERENCES property(`id`) ON DELETE NO ACTION ON UPDATE CASCADE;

CREATE INDEX `ix-deal-tenant_id-id` on `deal`(`tenant_id`,`id`);
CREATE INDEX `ix-deal-tenant_id-buyer_id` on `deal`(`tenant_id`,`buyer_id`);
CREATE INDEX `ix-deal-tenant_id-broker_id` on `deal`(`tenant_id`,`broker_id`);


DROP PROCEDURE IF EXISTS proc_save_property;

DELIMITER $$

CREATE PROCEDURE proc_save_property (IN in_id char(36), IN in_tenantid char(36), IN in_metadata json, IN in_status varchar(50), OUT out_metadata json)
begin
		insert into `property` ( `id`,`tenant_id`, `metadata`, `status`,`created_by`, `created_date`, `last_modified_by`, `last_modified_date` )
		values ( in_id, in_tenantid, in_metadata, in_status,'Proc', NOW(), 'Proc', NOW());

		select metadata into out_metadata from `property` where id=in_id;

end $$

DELIMITER ;

DROP PROCEDURE IF EXISTS proc_update_property;

DELIMITER $$

CREATE PROCEDURE proc_update_property (IN in_id char(36), IN in_tenantid char(36), IN in_metadata json, OUT out_metadata json)
begin

		update `property` set metadata = in_metadata, last_modified_date = NOW()			
        where id = in_id and tenant_id = in_tenantid;
        
        if (ROW_COUNT = 0) THEN
			set out_metadata = null; 
        else			
			select metadata into out_metadata from `property` where id=in_id;
		end if;
end $$

DELIMITER ;

DROP PROCEDURE IF EXISTS proc_get_property;

DELIMITER $$

CREATE PROCEDURE proc_get_property (IN in_tenantid char(36))
begin
	Select metadata from property where tenant_id = in_tenantid;
end $$

DELIMITER ;

DROP PROCEDURE IF EXISTS proc_save_deal;

DELIMITER $$

CREATE PROCEDURE proc_save_deal (IN in_id char(36), IN in_tenantid char(36), IN in_propertyid char(36), IN in_buyerid bigint)
begin
		insert into `deal` ( `id`,`tenant_id`, `property_id`, `buyer_id`, `broker_id`, `status_id`,`status_name`,`created_by`, `created_date`, `last_modified_by`, `last_modified_date` )
		values ( in_id, in_tenantid, in_propertyid,in_buyerid, in_buyerid,10,'Created','Proc', NOW(), 'Proc', NOW());

		select id from `deal` where id=in_id;

end $$

DELIMITER ;

DROP PROCEDURE IF EXISTS proc_update_dealstatus;

DELIMITER $$

CREATE PROCEDURE proc_update_dealstatus (IN in_id char(36), IN in_statusid int, IN in_statusname varchar(50))
begin

		update `deal` set status_id = in_statusid, status_name = in_statusname, last_modified_date = NOW()			
        where id = in_id;
        
	
			select * from `deal` where id=in_id;
end $$

DELIMITER ;


DROP PROCEDURE IF EXISTS proc_get_deal;

DELIMITER $$

CREATE PROCEDURE proc_get_deal (IN in_dealid char(36), IN in_tenantid char(36), IN in_buyerid bigint,IN in_brokerid bigint)
begin
	Select * from deal where 
    (in_dealid IS NULL OR id = in_dealid)   and
     (in_tenantid IS NULL OR tenant_id = in_tenantid) and
      (in_buyerid = 0 OR buyer_id = in_buyerid) and
       (in_brokerid = 0 OR broker_id = in_brokerid);
end $$

DELIMITER ;

INSERT INTO tenant values ('65ad46c8-0c56-466b-b94d-1a5427edb1e8','localhost');
