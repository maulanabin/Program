CREATE TABLE `answers` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`created_at` DATETIME(6) NULL DEFAULT NULL,
	`updated_at` DATETIME(6) NULL DEFAULT NULL,
	`created_by` BIGINT(19) NULL DEFAULT NULL,
	`updated_by` BIGINT(19) NULL DEFAULT NULL,
	`description` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`explanation` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	`is_right` BIT(1) NULL DEFAULT NULL,
	`question_id` BIGINT(19) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK3erw1a3t0r78st8ty27x6v3g1` (`question_id`) USING BTREE,
	CONSTRAINT `FK3erw1a3t0r78st8ty27x6v3g1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON UPDATE NO ACTION ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=ndbcluster;


CREATE TABLE `appraisal_forms` (
	`id` BIGINT(19) NOT NULL AUTO_INCREMENT,
	`created_at` DATETIME(6) NULL DEFAULT NULL,
	`updated_at` DATETIME(6) NULL DEFAULT NULL,
	`created_by` BIGINT(19) NULL DEFAULT NULL,
	`updated_by` BIGINT(19) NULL DEFAULT NULL,
	`name` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=ndbcluster;