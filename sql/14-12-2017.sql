-- --------------------------------------------------------
-- Vært:                         binfo.cict7trvgh69.eu-central-1.rds.amazonaws.com
-- Server-version:               5.6.37-log - MySQL Community Server (GPL)
-- ServerOS:                     Linux
-- HeidiSQL Version:             9.4.0.5125
-- User:						 Vagabonden
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for binfo
CREATE DATABASE IF NOT EXISTS `binfo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `binfo`;

-- Dumping structure for tabel binfo.apartment
CREATE TABLE IF NOT EXISTS `apartment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `number` varchar(45) NOT NULL,
  `rooms` int(11) NOT NULL,
  `garden` bit(1) NOT NULL,
  `size` float NOT NULL,
  `floor` int(11) NOT NULL,
  `floors` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.apartment: ~4 rows (approximately)
DELETE FROM `apartment`;
/*!40000 ALTER TABLE `apartment` DISABLE KEYS */;
INSERT INTO `apartment` (`id`, `address`, `number`, `rooms`, `garden`, `size`, `floor`, `floors`) VALUES
	(36, 'Genforeningspladsen', '1 st. tv.', 3, b'0', 91, 0, 1),
	(37, 'Genforeningspladsen', '1', 4, b'0', 91, 0, 1),
	(38, 'Mågevej', '13', 6, b'0', 127, 0, 1),
	(39, 'Mågevej', '12', 3, b'0', 98, 0, 0);
/*!40000 ALTER TABLE `apartment` ENABLE KEYS */;

-- Dumping structure for tabel binfo.apartment_neighbours
CREATE TABLE IF NOT EXISTS `apartment_neighbours` (
  `id_apartment` int(11) NOT NULL,
  `neighbour` int(11) NOT NULL,
  PRIMARY KEY (`id_apartment`,`neighbour`),
  KEY `apartment_neighbours_apartment_neighbour_id_fk` (`neighbour`),
  CONSTRAINT `apartment_neighbours_apartment_id_fk` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `apartment_neighbours_apartment_neighbour_id_fk` FOREIGN KEY (`neighbour`) REFERENCES `apartment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.apartment_neighbours: ~2 rows (approximately)
DELETE FROM `apartment_neighbours`;
/*!40000 ALTER TABLE `apartment_neighbours` DISABLE KEYS */;
INSERT INTO `apartment_neighbours` (`id_apartment`, `neighbour`) VALUES
	(36, 37),
	(38, 39);
/*!40000 ALTER TABLE `apartment_neighbours` ENABLE KEYS */;

-- Dumping structure for tabel binfo.list_and_seniority
CREATE TABLE IF NOT EXISTS `list_and_seniority` (
  `email` varchar(255) NOT NULL,
  `list_priority` int(11) NOT NULL,
  `seniority` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`seniority`,`email`),
  KEY `seniority` (`seniority`),
  KEY `email` (`email`),
  CONSTRAINT `ezpz` FOREIGN KEY (`email`) REFERENCES `user` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.list_and_seniority: ~5 rows (approximately)
DELETE FROM `list_and_seniority`;
/*!40000 ALTER TABLE `list_and_seniority` DISABLE KEYS */;
INSERT INTO `list_and_seniority` (`email`, `list_priority`, `seniority`) VALUES
	('admin@test.dk', 1, 82),
	('user1@test.dk', 2, 83),
	('user2@test.dk', 1, 84),
	('user2@test.dk', 2, 85),
	('user3@test.dk', 2, 86);
/*!40000 ALTER TABLE `list_and_seniority` ENABLE KEYS */;

-- Dumping structure for tabel binfo.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`,`role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.role: ~2 rows (approximately)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role`) VALUES
	(1, 'ADMIN'),
	(2, 'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Dumping structure for tabel binfo.user
CREATE TABLE IF NOT EXISTS `user` (
  `email` varchar(255) NOT NULL,
  `active` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `my_apartment` int(11) DEFAULT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `user_email_uindex` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.user: ~7 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`email`, `active`, `name`, `last_name`, `password`, `phone_number`, `my_apartment`) VALUES
	('admin@test.dk', 1, 'Admin', 'Test', '$2a$10$pkoE6NruW63pRWs6He3g4OllPMXlubNPoJMgEt7pbFh59nukB8gkS', '12312312', 36),
	('user1@test.dk', 1, 'User1', 'Test', '$2a$10$fd.uDCUKMy.30Vr.UK2VGuIx95CZjfJeYzK.GDQGmb8EjyEo/m/NC', '23423423', 0),
	('user2@test.dk', 1, 'User2', 'Test', '$2a$10$Wh71B8Jkd8SpUZFPBxSMluhQ9wdRRcnbwVz46b9a/CwH33QPit5FS', '34534534', 37),
	('user3@test.dk', 1, 'User3', 'Test', '$2a$10$Pu8gf/JHo7t5mLQ1A6/dBOUXF9Z8p.Mrc9UINMEi4uqvXARakb1bC', '45645645', 38);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for tabel binfo.user_preferences
CREATE TABLE IF NOT EXISTS `user_preferences` (
  `email` varchar(255) CHARACTER SET latin1 NOT NULL,
  `id_apartment` int(11) NOT NULL,
  PRIMARY KEY (`email`,`id_apartment`),
  KEY `user_preferences_apartment_id_fk` (`id_apartment`),
  CONSTRAINT `user_preferences_apartment_id_fk` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.user_preferences: ~9 rows (approximately)
DELETE FROM `user_preferences`;
/*!40000 ALTER TABLE `user_preferences` DISABLE KEYS */;
INSERT INTO `user_preferences` (`email`, `id_apartment`) VALUES
	('admin@test.dk', 36),
	('user1@test.dk', 36),
	('user2@test.dk', 36),
	('user3@test.dk', 36),
	('admin@test.dk', 37),
	('user1@test.dk', 38),
	('user2@test.dk', 38),
	('user1@test.dk', 39),
	('user3@test.dk', 39);
/*!40000 ALTER TABLE `user_preferences` ENABLE KEYS */;

-- Dumping structure for tabel binfo.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` varchar(50) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FK_user_role_role` (`role_id`),
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`email`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.user_role: ~7 rows (approximately)
DELETE FROM `user_role`;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	('admin@test.dk', 1),
	('user1@test.dk', 2),
	('user2@test.dk', 2),
	('user3@test.dk', 2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
