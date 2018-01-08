/--2015-08-30--/
ALTER TABLE `fileinfo`
ADD COLUMN `isNeedCache`  tinyint(2) NULL DEFAULT 1 AFTER `driver`,
ADD COLUMN `isNeedIndex`  tinyint(2) NULL DEFAULT 1 AFTER `isNeedCache`;
/--2015-08-30--/


