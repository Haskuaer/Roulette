-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: roulette
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `game_sessions`
--

DROP TABLE IF EXISTS `game_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_sessions` (
  `id` binary(16) NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `max_players` int DEFAULT NULL,
  `players_count` int DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_sessions`
--

LOCK TABLES `game_sessions` WRITE;
/*!40000 ALTER TABLE `game_sessions` DISABLE KEYS */;
INSERT INTO `game_sessions` VALUES (_binary '\\¶Q™øàCYó\ÌT=‘∏n','2025-02-09 19:07:27.290533',3,3,'active'),(_binary 'v®*_æ8FÖ¥jkZBb\Ë','2025-02-09 19:05:39.090172',3,3,'active'),(_binary 'ìYEÚ\ÿ\‹LXæ_œ©X9#K','2025-02-11 09:54:34.957024',3,3,'active'),(_binary '˘á\ÎôI˛é´}ü3(á','2025-02-11 11:00:21.578999',3,1,'active'),(_binary 'ˇ\'Û\Ì˘,B\Óâ9ë∂;\Àˆ','2025-02-11 09:20:27.623353',3,3,'active');
/*!40000 ALTER TABLE `game_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `round_bets`
--

DROP TABLE IF EXISTS `round_bets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `round_bets` (
  `id` binary(16) NOT NULL,
  `bet_amount` double NOT NULL,
  `bet_type` varchar(255) NOT NULL,
  `round_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKd8s1hqqcchqhxmr18cc5v598s` (`round_id`),
  KEY `FK7j1jrbucpb6f6v1vsh5ho9xvx` (`user_id`),
  CONSTRAINT `FK7j1jrbucpb6f6v1vsh5ho9xvx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKd8s1hqqcchqhxmr18cc5v598s` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `round_bets`
--

LOCK TABLES `round_bets` WRITE;
/*!40000 ALTER TABLE `round_bets` DISABLE KEYS */;
/*!40000 ALTER TABLE `round_bets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rounds`
--

DROP TABLE IF EXISTS `rounds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rounds` (
  `id` binary(16) NOT NULL,
  `createAt` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `session_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpw963y60xxpgcwesr92j24ro` (`session_id`),
  CONSTRAINT `FKpw963y60xxpgcwesr92j24ro` FOREIGN KEY (`session_id`) REFERENCES `game_sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rounds`
--

LOCK TABLES `rounds` WRITE;
/*!40000 ALTER TABLE `rounds` DISABLE KEYS */;
/*!40000 ALTER TABLE `rounds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `balance` double DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `round_id` binary(16) DEFAULT NULL,
  `session_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi2sx1v570j6ctu093bk7c3f30` (`round_id`),
  KEY `FKoswt86ksrkafoo7qglcc1p475` (`session_id`),
  CONSTRAINT `FKi2sx1v570j6ctu093bk7c3f30` FOREIGN KEY (`round_id`) REFERENCES `rounds` (`id`),
  CONSTRAINT `FKoswt86ksrkafoo7qglcc1p475` FOREIGN KEY (`session_id`) REFERENCES `game_sessions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary 'aâj5@Ü†\‰.Úx\nï',0,'test','testA',NULL,NULL),(_binary 'gF7hb¥Noâ-`z\≈\"\≈\…',0,'test','testB',NULL,NULL),(_binary 'h•Öjd5FÂüª\Ë—¶y…Ü',0,'test','testC',NULL,NULL),(_binary '\’˚˚ú\ŒLëîA\\ç\ÁÇ',10,'test','test',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-13 16:56:47
