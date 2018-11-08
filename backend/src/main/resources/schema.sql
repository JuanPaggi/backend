USE desa_byo;
-- ---
-- Globals
-- ---

-- SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
-- SET FOREIGN_KEY_CHECKS=0;

-- ---
-- Table 'users'
-- 
-- ---

DROP TABLE IF EXISTS `users`;
		
CREATE TABLE `users` (
  `id_user` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(128) NOT NULL,
  `last_name` VARCHAR(128) NOT NULL,
  `email` VARCHAR(125) NOT NULL,
  `password` VARCHAR(250) NULL DEFAULT NULL,
  `last_login` DATETIME NOT NULL,
  `signup_date` DATETIME NOT NULL,
  `linkedin_id` VARCHAR(250) NULL DEFAULT NULL,
  `busco` VARCHAR(255) NOT NULL,
  `ofrezco` VARCHAR(255) NOT NULL,
  `picture_url` VARCHAR(250) NOT NULL,
  `is_premium` bit NOT NULL,
  `salt_jwt` VARCHAR(120) NOT NULL,
  `completoByO` bit NOT NULL,
  `locked` bit NOT NULL,
  `failed_login_attempts` INTEGER NOT NULL,
  `unlock_account_code` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`id_user`),
KEY (`linkedin_id`)
);

-- ---
-- Table 'profile'
-- 
-- ---

DROP TABLE IF EXISTS `profile`;
		
CREATE TABLE `profile` (
  `id_user` BIGINT NOT NULL,
  `headline` MEDIUMTEXT NULL DEFAULT NULL,
  `industry` VARCHAR(120) NULL DEFAULT NULL,
  `country` INTEGER NOT NULL,
  `location` VARCHAR(200) NOT NULL,
  `linkedin_url` VARCHAR(250) NULL DEFAULT NULL,
  `summary` VARCHAR(250) NULL DEFAULT NULL,
  PRIMARY KEY (`id_user`)
);

-- ---
-- Table 'gps_data'
-- 
-- ---

DROP TABLE IF EXISTS `gps_data`;
		
CREATE TABLE `gps_data` (
  `id_gps_record` BIGINT NOT NULL AUTO_INCREMENT,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  `date_recorded` DATE NOT NULL,
  PRIMARY KEY (`id_gps_record`),
KEY (`latitude`, `longitude`)
);

-- ---
-- Table 'gps_data_users'
-- 
-- ---

DROP TABLE IF EXISTS `gps_data_users`;
		
CREATE TABLE `gps_data_users` (
  `id_gps_record` INTEGER NOT NULL,
  `id_user` BIGINT NOT NULL,
  PRIMARY KEY (`id_gps_record`, `id_user`)
);

-- ---
-- Table 'cached_users_close'
-- 
-- ---

DROP TABLE IF EXISTS `cached_users_close`;
		
CREATE TABLE `cached_users_close` (
  `id_user_searcher` BIGINT NOT NULL,
  `id_user_finded` BIGINT NOT NULL,
  `cache_date` DATE NOT NULL,
  PRIMARY KEY (`id_user_searcher`, `id_user_finded`, `cache_date`)
);

-- ---
-- Table 'friendships'
-- 
-- ---

DROP TABLE IF EXISTS `friendships`;
		
CREATE TABLE `friendships` (
  `id_user_requester` BIGINT NOT NULL,
  `id_user_target` BIGINT NOT NULL,
  `is_accepted` bit NOT NULL,
  `date_emitted` DATE NOT NULL,
  `is_viewed` bit NOT NULL,
  PRIMARY KEY (`id_user_requester`, `id_user_target`)
);

-- ---
-- Table 'chats'
-- 
-- ---

DROP TABLE IF EXISTS `chats`;
		
CREATE TABLE `chats` (
  `id_user_requester` BIGINT NOT NULL,
  `id_user_sender` BIGINT NOT NULL,
  `last_message_id` INTEGER NOT NULL,
  PRIMARY KEY (`id_user_requester`, `id_user_sender`)
);

-- ---
-- Table 'messages'
-- 
-- ---

DROP TABLE IF EXISTS `messages`;
		
CREATE TABLE `messages` (
  `id_message` BIGINT NOT NULL AUTO_INCREMENT,
  `id_user_sender` BIGINT NOT NULL,
  `id_user_requester` BIGINT NOT NULL,
  `message` VARCHAR(250) NOT NULL,
  `date_sended` DATE NOT NULL,
  `is_viewed` bit NOT NULL,
  PRIMARY KEY (`id_message`)
);

-- ---
-- Table 'events'
-- 
-- ---

DROP TABLE IF EXISTS `events`;
		
CREATE TABLE `events` (
  `id_event` INTEGER NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `logo` VARCHAR(250) NOT NULL,
  `id_gps_record` INTEGER NOT NULL,
  `location_description` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id_event`)
);

-- ---
-- Table 'stands'
-- 
-- ---

DROP TABLE IF EXISTS `stands`;
		
CREATE TABLE `stands` (
  `id_stand` MEDIUMINT NOT NULL AUTO_INCREMENT,
  `id_event` INTEGER NOT NULL,
  `name` VARCHAR(64) NOT NULL,
  `logo` VARCHAR(250) NOT NULL,
  `id_user_organizer` BIGINT NOT NULL,
  PRIMARY KEY (`id_stand`)
);

-- ---
-- Table 'external_ticket'
-- 
-- ---

DROP TABLE IF EXISTS `external_ticket`;
		
CREATE TABLE `external_ticket` (
  `id_user` BIGINT NOT NULL,
  `id_event` INTEGER NOT NULL,
  PRIMARY KEY (`id_user`, `id_event`)
);

-- ---
-- Table 'stands_checkin'
-- 
-- ---

DROP TABLE IF EXISTS `stands_checkin`;
		
CREATE TABLE `stands_checkin` (
  `id_stand` INTEGER NOT NULL,
  `id_user` BIGINT NOT NULL,
  `date_checkin` DATE NOT NULL,
  PRIMARY KEY (`id_stand`, `id_user`)
);

-- ---
-- Table 'countries'
-- 
-- ---

DROP TABLE IF EXISTS `countries`;
		
CREATE TABLE `countries` (
  `id_country` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`id_country`)
);

-- ---
-- Table 'configuration'
-- 
-- ---

DROP TABLE IF EXISTS `configuration`;
		
CREATE TABLE `configuration` (
  `id_user` BIGINT NOT NULL,
  `kms_radio` INTEGER NULL DEFAULT NULL,
  `linkedin_autoupdate` bit NOT NULL,
  PRIMARY KEY (`id_user`)
);

-- ---
-- Table 'remember_tokens'
-- 
-- ---

DROP TABLE IF EXISTS `remember_tokens`;
		
CREATE TABLE `remember_tokens` (
  `id_user` BIGINT NOT NULL,
  `unlock_key` VARCHAR(6) NOT NULL,
  `request_date` DATE NOT NULL,
  `attempts` INTEGER NOT NULL,
  PRIMARY KEY (`id_user`)
);

-- ---
-- Foreign Keys 
-- ---

ALTER TABLE `profile` ADD FOREIGN KEY (id_user) REFERENCES `users` (`id_user`);
ALTER TABLE `profile` ADD FOREIGN KEY (country) REFERENCES `countries` (`id_country`);
ALTER TABLE `gps_data_users` ADD FOREIGN KEY (id_gps_record) REFERENCES `gps_data` (`id_gps_record`);
ALTER TABLE `gps_data_users` ADD FOREIGN KEY (id_user) REFERENCES `users` (`id_user`);
ALTER TABLE `cached_users_close` ADD FOREIGN KEY (id_user_searcher) REFERENCES `users` (`id_user`);
ALTER TABLE `cached_users_close` ADD FOREIGN KEY (id_user_finded) REFERENCES `users` (`id_user`);
ALTER TABLE `friendships` ADD FOREIGN KEY (id_user_requester) REFERENCES `users` (`id_user`);
ALTER TABLE `friendships` ADD FOREIGN KEY (id_user_target) REFERENCES `users` (`id_user`);
ALTER TABLE `chats` ADD FOREIGN KEY (id_user_requester) REFERENCES `users` (`id_user`);
ALTER TABLE `chats` ADD FOREIGN KEY (id_user_sender) REFERENCES `users` (`id_user`);
ALTER TABLE `chats` ADD FOREIGN KEY (last_message_id) REFERENCES `messages` (`id_message`);
ALTER TABLE `messages` ADD FOREIGN KEY (id_user_sender) REFERENCES `users` (`id_user`);
ALTER TABLE `messages` ADD FOREIGN KEY (id_user_requester) REFERENCES `users` (`id_user`);
ALTER TABLE `events` ADD FOREIGN KEY (id_event) REFERENCES `external_ticket` (`id_event`);
ALTER TABLE `events` ADD FOREIGN KEY (id_gps_record) REFERENCES `gps_data` (`id_gps_record`);
ALTER TABLE `stands` ADD FOREIGN KEY (id_event) REFERENCES `events` (`id_event`);
ALTER TABLE `stands` ADD FOREIGN KEY (id_user_organizer) REFERENCES `users` (`id_user`);
ALTER TABLE `external_ticket` ADD FOREIGN KEY (id_user) REFERENCES `users` (`id_user`);
ALTER TABLE `stands_checkin` ADD FOREIGN KEY (id_stand) REFERENCES `stands` (`id_stand`);
ALTER TABLE `stands_checkin` ADD FOREIGN KEY (id_user) REFERENCES `users` (`id_user`);
ALTER TABLE `configuration` ADD FOREIGN KEY (id_user) REFERENCES `users` (`id_user`);
ALTER TABLE `remember_tokens` ADD FOREIGN KEY (id_user) REFERENCES `users` (`id_user`);

-- ---
-- Table Properties
-- ---

-- ALTER TABLE `users` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `profile` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `gps_data` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `gps_data_users` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `cached_users_close` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `friendships` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `chats` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `messages` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `events` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `stands` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `external_ticket` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `stands_checkin` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `countries` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `configuration` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
-- ALTER TABLE `remember_tokens` ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;