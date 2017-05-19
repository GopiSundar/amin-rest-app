
# Change this name when running the script for a different tenant
use amin;


DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `id` char(36) CHARACTER SET ascii NOT NULL,
  `tenant_id` char(36) CHARACTER SET ascii NOT NULL,
  `metadata` json NOT NULL, 
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

CREATE INDEX `ix-property-id-tenant_id` on `property`(`tenant_id`,`id`);
CREATE INDEX `ix-tenant-server_name` on `tenant`(`server_name`);


DROP PROCEDURE IF EXISTS proc_save_property;

DELIMITER $$

CREATE PROCEDURE proc_save_property (IN in_id char(36), IN in_tenantid char(36), IN in_metadata json, OUT out_metadata json)
begin
		insert into `property` ( `id`,`tenant_id`, `metadata`, `created_by`, `created_date`, `last_modified_by`, `last_modified_date` )
		values ( in_id, in_tenantid, in_metadata, 'Proc', NOW(), 'Proc', NOW());

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


INSERT INTO tenant values ('65ad46c8-0c56-466b-b94d-1a5427edb1e8','localhost');
