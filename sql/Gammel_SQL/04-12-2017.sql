-- --------------------------------------------------------
-- Vært:                         binfo.cict7trvgh69.eu-central-1.rds.amazonaws.com
-- Server-version:               5.6.37-log - MySQL Community Server (GPL)
-- ServerOS:                     Linux
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for binfo
CREATE DATABASE IF NOT EXISTS `binfo` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `binfo`;

-- Dumping structure for tabel binfo.apartment
CREATE TABLE IF NOT EXISTS `apartment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `number` varchar(45) NOT NULL,
  `rooms` int(11) NOT NULL,
  `garden` bit(1) NOT NULL,
  `size` float NOT NULL,
  `floor` int(11) NOT NULL,
  `floors` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.apartment: ~13 rows (approximately)
DELETE FROM `apartment`;
/*!40000 ALTER TABLE `apartment` DISABLE KEYS */;
INSERT INTO `apartment` (`id`, `address`, `number`, `rooms`, `garden`, `size`, `floor`, `floors`) VALUES
	(27, 'asdasdsad', 'asdadasds123', 132, b'0', 32, 42, 213),
	(28, 'heyaa', '123', 5, b'1', 5, 5, 5),
	(29, 'morten', '999', 999, b'0', 99, 9, 9),
	(32, 'Dennisvej', '26', 6, b'0', 63, 3, 1),
	(33, 'Gobelagwp', '23', 6, b'0', 78, 2, 1),
	(34, 'leglihed', '69', 96, b'0', 69, 96, 69);
/*!40000 ALTER TABLE `apartment` ENABLE KEYS */;

-- Dumping structure for tabel binfo.role
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.role: ~2 rows (approximately)
DELETE FROM role;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO role (`role_id`, `role`) VALUES
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
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.user: ~8 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`email`, `active`, `name`, `last_name`, `password`, `phone_number`) VALUES
	('admin@admin.dk', 1, 'admin', 'test', '$2a$10$DTTgCUuee2pS5rDjlfVs5eSfxJbvLdvULJyP6kDoVuZ9Ad22LT5gC', '12345678'),
	('admin@admind.dk', 0, 'asdasd', 'asdad', '$2a$10$sjHXXmdAbz1fGj5Or0CRbeXYdfPPvlnRs8wsyQ.O/zKqagCAY.zGK', 'adssdas'),
	('admin@test.dk', 1, 'admin', 'test', '$2y$10$FXOxfMBubBaNScBvSb64DOgA4nW.T1UnK7clzyzhVRMxBEXEuY1/W', '6735344'),
	('amin@amin.dk', 1, 'amin', 'test', '$2a$10$eGuZZyoB.uMMCD1ywuBPt.JojTXWbROXua/BX7RMBw7nNcNn.c18e', '69696969'),
	('asdad@asdsdad.dk', 0, 'asdasd0asdo', 'qasdidasdl', '$2a$10$ECuuFO8RfYPvzuQ7PBmrFeNaQlz1iLRt6aXI60EnkzJxB89ea7./C', 'qdaofssf'),
	('asdasd@atp.dk', 0, 'asdsasd', 'asdasd', '$2a$10$dy5EwiPgwmT3KY9axTxZTuUR/8hOfSPBJz6uXPzJ8kne5vh3vDy8O', 'asdsasd'),
	('bigman@hotmale.dk', 0, 'big', 'man', '$2a$10$90D7KWVOE1SSSGF9ErZ5DOAQHAbGbaOZeIyFuc5wPKNlecRM1vatG', '69696969'),
	('dennis@atp.dk', 0, 'asdasd', 'asdas', '$2a$10$FAdqob1nQrlrdHABTNldAedeFZ/pFWXB60no7ymV0/l9bRHzfWnyG', 'asdasds'),
	('jens@test.dk', 0, 'jens', 'test', '$2a$10$6mtQfk.OIr5xkz/rWR865.SuCeOe/iVgJEPKKbxx4LQ/gRnjBDnS2', '123123213'),
	('morten@test.dk', 0, 'morten', 'test', '$2a$10$TdZIACdHlZHsxukQmyqR4uN6UWBbQ6xEW2oJfDBD8cgCJvc6aZV1q', '69696969'),
	('mortenercool@coolhotboy.dk', 1, 'Morten the', 'Mean machine', '$2a$10$zs33J09hWKMWgLEaIg0vuOu48GfvmqRbgDkhfvLJK00pXYa3MN/t6', '69696969'),
	('Storefyr@hotmail.dk', 0, 'pizza', 'elsker', '$2a$10$uSBftL09GtaHgMpdi.8wIOJDhbRHhNCyPJ.anKoOkTYKqIzQvvpwO', '696969696969'),
	('storefyr@hotmale.dk', 0, 'store', 'fyr', '$2a$10$8UX5P0JJOTNLszNlVrmMzepWvpHdVop2uPOU6PKrITmsRuWG9/0am', '69696969'),
	('superkaj@kajogandrea.dr', 1, 'Super', 'Kaj', '$2a$10$Wx5L/pKAsecXb2zBsrNK2.jnqaLh0VYfB2MLej0ORWxNZy80d7xge', '29375682'),
	('test1@test1.dk', 1, 'test', 'et', '$2a$10$apeP2EoDDnFe2o129RaWUOxvGf4wqQHlaCDxpGQqJI773TYhHFSwy', '2359237905'),
	('test2@test2.dk', 1, 'test', 'to', '$2a$10$x5Pb8yQYJjY8sIexNRd7PergyWwLCvzoIIIjqple936o.z15bMlt2', '12444545'),
	('test3@test3.dk', 1, 'test', 'tre', '$2a$10$hj65ox/1quIY7bUuQeNwjuERjzagqkrNOcfqZwqUaARxCwSo4ooBG', '69696969'),
	('test@test.dk', 1, 'test', 'test', '$2a$10$4OPEvHu1lqkLU1M6KcgFvuJQpGktZvW9vbu6bzYGWiZX6Dg6dq1zi', '12345678'),
	('testadmin@testadmin.dk', 0, 'test', 'admin', '$2a$10$rBfA5SsvZgP/o5ELMwjswuREwcvz/udylMG4btiXPMBr/fRx.zVM2', '69696969'),
	('Vagabonden@outlook.com', 1, 'Patrick', 'Klæbel', '$2a$10$Sl1JOpr1HYZXqek0HhHh0eL6p4eRWQomaHONvoOk0lUBzlt/YAj4y', '61717511');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for tabel binfo.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` varchar(50) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FK_user_role_role` (`role_id`),
  KEY `FK_user_role_user` (`user_id`),
  CONSTRAINT `FK_user_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `FK_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table binfo.user_role: ~10 rows (approximately)
DELETE FROM `user_role`;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
	('bigman@hotmale.dk', 1),
	('testadmin@testadmin.dk', 2),
	('morten@test.dk', 2),
	('jens@test.dk', 2),
	('amin@amin.dk', 1),
	('test1@test1.dk', 2),
	('test2@test2.dk', 2),
	('superkaj@kajogandrea.dr', 2),
	('test3@test3.dk', 2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
