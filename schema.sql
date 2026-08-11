-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: BookStore
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `admin_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `UK47bvqemyk6vlm0w7crc3opdd4` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'2026-08-04 17:20:49.677390','admin@stackcoders.com','$2a$10$IqviVp.mX88vLI9E0.wDA.4Qo3oolt/PRrwLTVi.0XiDaMk.M945q','ADMIN','2026-08-04 17:20:49.677390','Admin');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart_user` (`user_id`),
  KEY `fk_cart_product` (`product_id`),
  CONSTRAINT `fk_cart_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
INSERT INTO `cart_items` VALUES (22,8,27,1),(40,5,3,1);
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Fiction'),(3,'History'),(4,'Kids'),(2,'Technology');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jwt_tokens`
--

DROP TABLE IF EXISTS `jwt_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jwt_tokens` (
  `token_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `token` varchar(1000) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `expires_at` timestamp NOT NULL,
  `is_expired` tinyint(1) NOT NULL DEFAULT '0',
  `is_revoked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`token_id`),
  KEY `fk_jwt_user` (`user_id`),
  CONSTRAINT `fk_jwt_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jwt_tokens`
--

LOCK TABLES `jwt_tokens` WRITE;
/*!40000 ALTER TABLE `jwt_tokens` DISABLE KEYS */;
INSERT INTO `jwt_tokens` VALUES (1,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa2FuYWthbDY2QGdtYWlsLmNvbSIsImlhdCI6MTc4NTMxOTczMSwiZXhwIjoxNzg1MzIzMzMxfQ.EsLHAtoq4KeYKCq2hPbAzNnB9EqGbSFvU_uA2JnJwDv5sHWPJy0e9cQb5_DVI5Pj','2026-07-29 04:38:52','2026-07-29 05:38:52',0,0),(2,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa2FuYWthbDY2QGdtYWlsLmNvbSIsImlhdCI6MTc4NTMxOTc1MywiZXhwIjoxNzg1MzIzMzUzfQ.A9l01Bikf6i9sF3yLt4L_b6oxF03RwdDoqNj_g8UgJ7_NZpwQY6We8-v-sXryVIj','2026-07-29 04:39:14','2026-07-29 05:39:14',0,0),(3,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa2FuYWthbDY2QGdtYWlsLmNvbSIsImlhdCI6MTc4NTMyMjU3OCwiZXhwIjoxNzg1MzI2MTc4fQ.E6iZmoLyKLTcHg3-GbGyPd8Fke1Mab5vCweOEv1isyN_ikl0x6zy5vZgrzH6g_3O','2026-07-29 05:26:19','2026-07-29 06:26:19',0,1),(4,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa2FuYWthbDY2QGdtYWlsLmNvbSIsImlhdCI6MTc4NTMyMjc1MywiZXhwIjoxNzg1MzI2MzUzfQ.sFF6g98C3s8BZJgZMuF8Jeamy2VjH1Ln4Wmx8s-scvLNmNvKY44dTKdTTrDD7-XA','2026-07-29 05:29:13','2026-07-29 06:29:13',0,1),(5,2,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0MTc4NTQxMTAwODMxNkBleGFtcGxlLmNvbSIsImlhdCI6MTc4NTQxMTAxNSwiZXhwIjoxNzg1NDE0NjE1fQ.8Gtljl_wSTnZArjo-j07DPGXI84gs6tjEQeSWQ8HNTmq9439nqHr1mpb0Ar1VnaG','2026-07-30 06:00:16','2026-07-30 07:00:16',0,1),(6,2,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0MTc4NTQxMTAwODMxNkBleGFtcGxlLmNvbSIsImlhdCI6MTc4NTQxMTAxOCwiZXhwIjoxNzg1NDE0NjE4fQ.dQwHm_DwTdgtoRxm8BFfWXV1ybec7Xf4_ymPysGtkaHwmDc69d5d27D9Ph4Zlw84','2026-07-30 06:00:18','2026-07-30 07:00:18',0,1),(9,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa2FuYWthbDY2QGdtYWlsLmNvbSIsImlhdCI6MTc4NTQxNTMwMSwiZXhwIjoxNzg1NDE4OTAxfQ.FFFWOZrRm07H9BdZf_Lo1Bpi7iYSZDwJV3nvQ3EaNq6VXpdfccroqmIjUqfuUdTE','2026-07-30 07:11:41','2026-07-30 08:11:41',0,1),(10,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa2FuYWthbDY2QGdtYWlsLmNvbSIsImlhdCI6MTc4NTQyMTgwMywiZXhwIjoxNzg1NDI1NDAzfQ.hpHXIEfdVYnM1SzLdk7y0HkxUquR59M6xbiR_3UIEu98YBFm4EnewhYA7_NoLuVE','2026-07-30 09:00:04','2026-07-30 10:00:04',0,0),(11,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTQ2NzcyMCwiZXhwIjoxNzg1NDcxMzIwfQ.bn7qPoC6zd9vVUeBLFyeG9syHqOnimwuySoNfoVL7ZZOoHE7vxgZfC6JTZlAhgp2','2026-07-30 21:45:21','2026-07-30 22:45:21',0,0),(12,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTUwMDAxMCwiZXhwIjoxNzg1NTAzNjEwfQ.CHtGBrOzke7Vc4Ok1THdx0xoljfORDzuaX9jSU3Yu_qOV5a6Ij9UONplybpo58t8','2026-07-31 06:43:31','2026-07-31 07:43:31',0,1),(13,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTUwMTUwNywiZXhwIjoxNzg1NTA1MTA3fQ.RjvsFlcYJgH3qwAR1feBwNvNbkvTNT170yQm8YeADve9n0cosb5KAwLilaFiF8wQ','2026-07-31 07:08:27','2026-07-31 08:08:27',0,0),(14,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTUwMzI0MSwiZXhwIjoxNzg1NTA2ODQxfQ.kRbOxM2KiNWomj-EOBDJ3CB5Ni3wfp25W3Blq6ya_5aDIHFt_7zxxesTZxaslHG7','2026-07-31 07:37:22','2026-07-31 08:37:22',0,0),(15,6,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ1c2VyQGdtYWlsLmNvbSIsImlhdCI6MTc4NTUwNzcyMiwiZXhwIjoxNzg1NTExMzIyfQ.Hf7EHwdnjmILUJ5HP0Cbm_zt3lvmv_Z3xEODnd2P5AZNa3EBW3Mrq2Wdt8g85JOf','2026-07-31 08:52:02','2026-07-31 09:52:02',0,0),(16,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTY1MzAwNCwiZXhwIjoxNzg1NjU2NjA0fQ.lhqX1xSm-cwYoucGNQeu3vn8Wd3DAZxUEyzUlYeVSkJrKIBumdmeffrXw7g-u8_3','2026-08-02 01:13:24','2026-08-02 02:13:24',0,1),(17,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTY1NDk4NywiZXhwIjoxNzg1NjU4NTg3fQ.TGF2jP4iUSr-vQaj_OPoV8r1pVfkzCVR0VzDty1y5NSfUENC6AXlgq5HKAXEdqal','2026-08-02 01:46:28','2026-08-02 02:46:28',0,1),(18,7,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJna0BnbWFpbC5jb20iLCJpYXQiOjE3ODU2NzcwOTYsImV4cCI6MTc4NTY4MDY5Nn0._8TQw-GSorFZMP2mqrhT1y4s7maS4hZI0VGDkfZCnWFmoDwL7ilIWmnPEWbvUlv_','2026-08-02 07:54:57','2026-08-02 08:54:57',0,0),(19,8,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ2YW1zaWd1amphbGE4MEBnbWFpbC5jb20iLCJpYXQiOjE3ODU3MzAxMDAsImV4cCI6MTc4NTczMzcwMH0.5ll5k60FUOTJtEvzp0WNcMwngwKgrIyay4yDTMDn5BLKeF4Zl2_yB3SpSe3bw9Ga','2026-08-02 22:38:20','2026-08-02 23:38:20',0,0),(20,7,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJna0BnbWFpbC5jb20iLCJpYXQiOjE3ODU3NTEyNzUsImV4cCI6MTc4NTc1NDg3NX0.rYYnRBryHI4WZcNkQ1AKtIzabUUS4MSjtYnFoCToOJtI_ApI3ikqte41pDrmkJGC','2026-08-03 04:31:16','2026-08-03 05:31:16',0,1),(21,9,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnYXZpa0BnbWFpbC5jb20iLCJpYXQiOjE3ODU3NTYxMjMsImV4cCI6MTc4NTc1OTcyM30.7VrT3JZ6twiyLmLkfEFZ2VP9clY2HMedeIRYSmHpJZstucFQjdxtONMozSZhpHlY','2026-08-03 05:52:04','2026-08-03 06:52:04',0,0),(22,7,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJna0BnbWFpbC5jb20iLCJpYXQiOjE3ODU4Mzg2OTIsImV4cCI6MTc4NTg0MjI5Mn0.8odp-xEaDPrJYt_GvPHKDkpwUewAHFtbexOD0VcvHQ8gMWDOliHT3zn_8EKejA-w','2026-08-04 04:48:13','2026-08-04 05:48:13',0,1),(23,10,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnc2tAZ21haWwuY29tIiwiaWF0IjoxNzg1ODM5NTQzLCJleHAiOjE3ODU4NDMxNDN9.dP_L0GU-DEBWGEKvf9OZ7wTji3N5B-fCg2oFQR67nslu-GWecrBn_lDx1sPkRAD8','2026-08-04 05:02:23','2026-08-04 06:02:23',0,1),(24,10,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnc2tAZ21haWwuY29tIiwiaWF0IjoxNzg1ODM5NjAzLCJleHAiOjE3ODU4NDMyMDN9.z3lT6q-2uFr1JJp5cz5K20R34Ml3OcmzDdlZJJp-bS6ltMPSnMn5QEFTvP4o2gop','2026-08-04 05:03:24','2026-08-04 06:03:24',0,1),(25,10,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJnc2tAZ21haWwuY29tIiwiaWF0IjoxNzg1ODM5NjU5LCJleHAiOjE3ODU4NDMyNTl9.Mr_ksSDQU-ncbmhd2Bb1UfxgzOFcGwfRRXWhlfhwrzCDRiiaR4JadQV4F-jBWhtu','2026-08-04 05:04:20','2026-08-04 06:04:20',0,0),(26,11,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NjA4NjAsImV4cCI6MTc4NTg2NDQ2MH0.xtiCwdaDOXuLvocakc1Uuqvo3Lrn326EvhkOJVZQuR8NQ927U7gIXO_UQCA5qbcN','2026-08-04 10:57:40','2026-08-04 11:57:40',0,1),(27,11,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NjEwMTAsImV4cCI6MTc4NTg2NDYxMH0.1Ke_cG9qapektK1Kg3CWgSbvNOnYv_9fDf-0rL5tw0gnrOv9uEUMewTn_lGoh0BU','2026-08-04 11:00:10','2026-08-04 12:00:10',0,1),(28,11,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NjIyMzEsImV4cCI6MTc4NTg2NTgzMX0.q0xo9W6sKiWHvuFqpkmzbXvTHVlPrCIjvQjvLqsOnx2pXba2nGWa6dgEpdYfiIcI','2026-08-04 11:20:32','2026-08-04 12:20:32',0,1),(29,11,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NjI2NDEsImV4cCI6MTc4NTg2NjI0MX0.1K26lazqc1FOTA7VnEIfOv7OkQfy5iH-ohjcc1kzL9qNFWYw0VmxHir_vcqsyTAU','2026-08-04 11:27:21','2026-08-04 12:27:21',0,0),(30,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NjU3MDgsImV4cCI6MTc4NTg2OTMwOH0.z4z-bE9vKo4VkWuEBNDQTHQb1rNyKWkNF1Cgqbyjhu6O-P4yQ52hMFsnmFly1H2m','2026-08-04 12:18:28','2026-08-04 13:18:28',0,1),(31,12,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJrYW5ha2FsQGdtYWlsLmNvbSIsImlhdCI6MTc4NTg2NjE2NywiZXhwIjoxNzg1ODY5NzY3fQ.NyOvDcjswCZrsvYWKkuFOahODmRfDutegQX4scFjICxRU0GX47Z6PwMjVIW_kPpt','2026-08-04 12:26:08','2026-08-04 13:26:08',0,1),(32,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NjYyODQsImV4cCI6MTc4NTg2OTg4NH0.RL8fQP4-whWDAuaNgHrIkhN_CjGv5FUFO2PiWHKbLIE4g3AfVQsTco9RqcfR68kn','2026-08-04 12:28:04','2026-08-04 13:28:04',0,0),(33,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4Njc2NTksImV4cCI6MTc4NTg3MTI1OX0.YDrkF2Ra68V0rulWDFSns7W-MIGVeKw76MsfPZ_oQyTr441YR7E5K-tqU3UAWCxc','2026-08-04 12:51:00','2026-08-04 13:51:00',0,0),(34,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NzEwNjksImV4cCI6MTc4NTg3NDY2OX0.FbV67MDFByoIRo4B4hUYw2oI0ZPzUndJeBKKovCB4mnxIGNvnyOauLU8kdI3AjP-','2026-08-04 13:47:50','2026-08-04 14:47:50',0,0),(35,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU4NzE1NjMsImV4cCI6MTc4NTg3NTE2M30.xf8zsX9d0y6APST81Jhgt3pHq3slXxCcrM1wHfOm4zY4mVhqrCXxnKdB6wzPujWg','2026-08-04 13:56:03','2026-08-04 14:56:03',0,0),(36,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU5MDMzNzQsImV4cCI6MTc4NTkwNjk3NH0.M96r0YAKRopxE-WuTUBa4-KVsHVSY3cK7vZTCDPmGefp2IQFtYcjTKY36UT7TRZ5','2026-08-04 22:46:15','2026-08-04 23:46:15',0,1),(37,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU5MDk3NjMsImV4cCI6MTc4NTkxMzM2M30.7LTzs4oX5Y-dhaAZC0tKkPJZklwoP_bSiLbUdPFdure2vO7Qfpg-0ScA_Ow3VO2v','2026-08-05 00:32:44','2026-08-05 01:32:44',0,1),(38,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU5MTEwMDAsImV4cCI6MTc4NTkxNDYwMH0.IpcFI5V5yZnQunkkLcqZ0_XpG4mWsT-4h0n6Fmec_kTDKhF7J4M-2YOZ8JIBvIKK','2026-08-05 00:53:21','2026-08-05 01:53:21',0,0),(39,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU5MjE4NDIsImV4cCI6MTc4NTkyNTQ0Mn0.8m__KM_zpKY2uc6474U4JzfwCv4C_n3SJtgHQJyUYC2Aym9UD3G8oG4D7JrHOIPK','2026-08-05 03:54:02','2026-08-05 04:54:02',0,1),(40,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU5MjE5ODQsImV4cCI6MTc4NTkyNTU4NH0.0ayCtq3JxWykkRXQxhMK599nMLVckcEEWoSllWyL0y-O56xwyiPrEVGCwp7DdPr0','2026-08-05 03:56:25','2026-08-05 04:56:25',0,1),(41,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODU5MjIwNDIsImV4cCI6MTc4NTkyNTY0Mn0.mwlFFS7xEpsVD2e3rdp_tOTgGBFFi56eYyuG4tc6biLuCIYqtIRCVu25BhELfLlQ','2026-08-05 03:57:23','2026-08-05 04:57:23',0,1),(42,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NTkyMjA4MiwiZXhwIjoxNzg1OTI1NjgyfQ.bvIVAqxt7HSDxZu-Gu-dKTUT2RS4M5lVXuWav38Yn9JFvVOoflDHWssdU_Ukd4zd','2026-08-05 03:58:02','2026-08-05 04:58:02',0,0),(43,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NjE5MTI2NywiZXhwIjoxNzg2MTk0ODY3fQ.MFxCSoEBIuCh3tV74sSrCPTEny6CeVDvxmD6_5EQUE2x6jEFrQ9XWg0w9BBAkXzQ','2026-08-08 06:44:28','2026-08-08 07:44:28',0,0),(44,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODYxOTEzNTAsImV4cCI6MTc4NjE5NDk1MH0.9maC8Q9Hn45rznVrz7EsDIel3dqat7Oh4Aty-sbK7ko7h2erl2wNEwPg6Fvyo7uK','2026-08-08 06:45:50','2026-08-08 07:45:50',0,0),(45,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NjM0MDQ3MSwiZXhwIjoxNzg2MzQ0MDcxfQ.K9exNl7Y2TNE_SGvNXAyq7wCR6Rh-p_Msu5keJ0ZG0SFXD6iFw4xeDwBTPNupXkZ','2026-08-10 00:11:12','2026-08-10 01:11:12',0,0),(46,1,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBzdGFja2NvZGVycy5jb20iLCJpYXQiOjE3ODYzNDA1NjgsImV4cCI6MTc4NjM0NDE2OH0.Tm6GRxD8IIb8t8w93M2YEIeCCAS2n13C3gX70XIf6UrK37jW5Dn5IlTgWOOq_X_V','2026-08-10 00:12:48','2026-08-10 01:12:48',0,0),(47,5,'eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhYmhpQGdtYWlsLmNvbSIsImlhdCI6MTc4NjQzOTU2NSwiZXhwIjoxNzg2NDQzMTY1fQ.pJl6xxKgAF0vHY2Oh8r0ernXNLa9Rw_6ReKyj3Bu1RE7gyWroBccstzBrfnGqqSG','2026-08-11 03:42:45','2026-08-11 04:42:45',0,0);
/*!40000 ALTER TABLE `jwt_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `price_per_unit` decimal(38,2) NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_orderitem_order` (`order_id`),
  KEY `fk_orderitem_product` (`product_id`),
  CONSTRAINT `fk_orderitem_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_orderitem_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,'order_TK7XTKqIhdJyu7',7,1,599.00,599.00),(2,'order_TK8okWZh1yqECq',7,1,599.00,599.00),(3,'order_TK8okWZh1yqECq',19,2,450.00,900.00),(4,'order_TKo59bY1IIntka',2,1,2999.00,2999.00),(5,'order_TKodiSxYG7cga2',3,1,1599.00,1599.00),(6,'order_TKodiSxYG7cga2',2,1,2999.00,2999.00),(7,'order_TKodiSxYG7cga2',1,1,249.00,249.00),(8,'order_TKodiSxYG7cga2',4,1,1299.00,1299.00),(9,'order_TKodiSxYG7cga2',5,1,899.00,899.00),(10,'order_TKodiSxYG7cga2',6,1,459.00,459.00),(11,'order_TKodiSxYG7cga2',7,1,599.00,599.00),(12,'order_TKodiSxYG7cga2',8,1,349.00,349.00),(13,'order_TKodiSxYG7cga2',9,1,600.00,600.00),(14,'order_TKodiSxYG7cga2',10,1,459.00,459.00),(15,'order_TKodiSxYG7cga2',11,1,359.00,359.00),(16,'order_TKuv4yEmJeiiH7',10,1,459.00,459.00),(17,'order_TKuv4yEmJeiiH7',23,1,350.00,350.00),(18,'order_TKuv4yEmJeiiH7',19,1,450.00,450.00),(19,'order_TKuxLM4SUqcAyZ',18,1,549.00,549.00),(20,'order_TKuxLM4SUqcAyZ',7,1,599.00,599.00),(21,'order_TLGPBo8CzsunUV',7,1,599.00,599.00),(22,'order_TLGPBo8CzsunUV',3,1,1599.00,1599.00),(23,'order_TLHM8XJ5Kgz3W8',1,7,249.00,1743.00),(24,'order_TLHOXNiXUQaGuo',26,1,350.00,350.00),(25,'order_TLHOXNiXUQaGuo',27,1,459.00,459.00),(26,'order_TLmb4AmwHo6ygu',1,1,249.00,249.00),(27,'order_TLmb4AmwHo6ygu',2,1,2999.00,2999.00),(28,'order_TLmb4AmwHo6ygu',3,1,1599.00,1599.00),(29,'order_TLmb4AmwHo6ygu',4,1,1299.00,1299.00),(30,'order_TM2TIFtBZwfuNE',2,1,2999.00,2999.00),(31,'order_TM2TIFtBZwfuNE',6,1,459.00,459.00),(32,'order_TM2TIFtBZwfuNE',10,1,459.00,459.00),(33,'order_TM2TIFtBZwfuNE',25,1,459.00,459.00),(34,'order_TNGuUDzgstKx7U',1,4,249.00,996.00),(35,'order_TNxHJ2yAF4U9HO',1,1,249.00,249.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` varchar(255) NOT NULL,
  `user_id` bigint NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `status` enum('PENDING','SUCCESS','FAILED') DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `fk_order_user` (`user_id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('order_TK7XTKqIhdJyu7',5,599.00,'SUCCESS','2026-07-31 07:40:04','2026-07-31 07:40:04'),('order_TK8okWZh1yqECq',6,1499.00,'SUCCESS','2026-07-31 08:54:18','2026-07-31 08:54:18'),('order_TKo59bY1IIntka',5,2999.00,'SUCCESS','2026-08-02 01:15:14','2026-08-02 01:15:14'),('order_TKodiSxYG7cga2',5,9870.00,'SUCCESS','2026-08-02 01:47:58','2026-08-02 01:47:58'),('order_TKuv4yEmJeiiH7',7,1259.00,'SUCCESS','2026-08-02 07:56:34','2026-08-02 07:56:34'),('order_TKuxLM4SUqcAyZ',7,1148.00,'SUCCESS','2026-08-02 07:59:28','2026-08-02 07:59:28'),('order_TLGPBo8CzsunUV',7,2198.00,'SUCCESS','2026-08-03 04:57:45','2026-08-03 04:57:45'),('order_TLHM8XJ5Kgz3W8',9,1743.00,'SUCCESS','2026-08-03 05:53:31','2026-08-03 05:53:31'),('order_TLHOXNiXUQaGuo',9,809.00,'SUCCESS','2026-08-03 05:55:34','2026-08-03 05:55:34'),('order_TLmb4AmwHo6ygu',12,6146.00,'SUCCESS','2026-08-04 12:27:15','2026-08-04 12:27:15'),('order_TM2TIFtBZwfuNE',5,4376.00,'SUCCESS','2026-08-05 03:58:41','2026-08-05 03:58:41'),('order_TNGuUDzgstKx7U',5,996.00,'SUCCESS','2026-08-08 06:45:11','2026-08-08 06:45:11'),('order_TNxHJ2yAF4U9HO',5,249.00,'SUCCESS','2026-08-10 00:11:53','2026-08-10 00:11:53');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `image_id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL,
  `image_url` varchar(255) NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `fk_product_image` (`product_id`),
  CONSTRAINT `fk_product_image` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (1,1,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/the%20god%20of%20small%20things.jpg'),(2,2,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/Whistler.jpg'),(3,3,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/the%20bhagavad%20gita.jpg'),(4,4,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/the%20glass%20palace.jpg'),(5,5,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/malgudi%20days.jpg'),(6,6,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/the%20namesake.jpg'),(7,7,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/malgudi%20days%20kannada.jpg'),(8,8,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/Aduge-meneyallondu-Huli-front.webp'),(9,9,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/delayed%20monsoon.jpg'),(10,10,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/heli%20hogu%20karana.jpg'),(11,11,'https://ik.imagekit.io/StringStackGavi/fiction%20books/fiction/site.jpg'),(12,12,'https://ik.imagekit.io/StringStackGavi/technology/technology/foundation%20of%20education%20tech.jpg?updatedAt=1785177549064'),(13,13,'https://ik.imagekit.io/StringStackGavi/technology/technology/tech%20dot%20com.jpg?updatedAt=1785177547783'),(14,14,'https://ik.imagekit.io/StringStackGavi/technology/technology/tech%20dot%20com.jpg?updatedAt=1785177547783'),(15,15,'https://ik.imagekit.io/StringStackGavi/technology/technology/science%20tech.jpg?updatedAt=1785177547662'),(16,16,'https://ik.imagekit.io/StringStackGavi/technology/technology/science%20and%20tech.jpg?updatedAt=1785177547655'),(17,17,'https://ik.imagekit.io/StringStackGavi/technology/technology/robotics.jpg?updatedAt=1785177547491'),(18,18,'https://ik.imagekit.io/StringStackGavi/technology/technology/future%20tech.jpg?updatedAt=1785177547146'),(19,19,'https://ik.imagekit.io/StringStackGavi/technology/technology/web%20tech.jpg?updatedAt=1785177547048'),(20,20,'https://ik.imagekit.io/StringStackGavi/technology/technology/science%20and%20technolory.jpg?updatedAt=1785177547089'),(21,21,'https://ik.imagekit.io/StringStackGavi/technology/technology/technology.jpg?updatedAt=1785177547024'),(22,22,'https://ik.imagekit.io/StringStackGavi/technology/technology/information%20science.jpg?updatedAt=1785177546797'),(23,23,'https://ik.imagekit.io/StringStackGavi/technology/technology/every%20day%20tech.jpg?updatedAt=1785177545579'),(24,24,'https://ik.imagekit.io/StringStackGavi/technology/technology/algorithms%20to%20live%20by.jpg?updatedAt=1785177545581'),(25,25,'https://ik.imagekit.io/StringStackGavi/history/History/174501.jpg'),(26,26,'https://ik.imagekit.io/StringStackGavi/history/History/india%20that%20us%20Bharat.jpg'),(27,27,'https://ik.imagekit.io/StringStackGavi/history/History/Lords%20of%20deccan.jpg'),(29,28,'https://ik.imagekit.io/StringStackGavi/history/History/Raj%20The%20Making%20and%20Unmaking%20of%20British%20India.jpg');
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  `category_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `author` varchar(65) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `fk_product_category` (`category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'The god of small things','The God of Small Things is a 1997 debut novel by Indian author Arundhati Roy that won the Booker Prize. Set in Kerala, India, it tells the tragic story of fraternal twins Estha and Rahel, whose lives are fractured by family dysfunction, forbidden love, and strict social rules.',249.00,50,1,'2026-07-27 17:55:25','2026-07-27 17:55:25','Arundhati Roy'),(2,'Whistler: A Novel','Whistler is a story about two adults looking back over the choices they made, and the choices that were made for them. It’s a story about bravery, memory, the often small yet consequential moments that define our lives, and the endless stream of loss that in time comes for us all.',2999.00,25,1,'2026-07-27 17:58:23','2026-07-27 17:58:23','Ann Patchett '),(3,'the bhagavad gita','In the Bhagavad Gita, Lord Krishna defines yoga not as physical postures, but as spiritual union, emotional equanimity (\"Samatvam yoga uchyate\" - balance in success and failure), and skill in action (\"Yogah karmasu kaushalam\" - performing one\'s duties without attachment to results)',1599.00,48,1,'2026-07-27 18:00:02','2026-07-27 18:00:02',NULL),(4,'the glass palace','[The Glass Palace] is an epic historical novel by Indian writer Amitav Ghosh published in 2000. It spans a century, starting with the British invasion of Burma in 1885 and the overthrow of King Thibaw, tracking how colonialism, war, and migration reshaped Burma, India, and Malaya through the lives of an interconnected family.',1299.00,15,1,'2026-07-27 18:01:55','2026-07-27 18:01:55','Amitav Ghosh '),(5,'Malgudi days','Malgudi Days is a famous collection of 32 short stories by R. K. Narayan, first published in 1943. The book is set in Malgudi, a fictional town in South India, and shows the simple, funny, and real lives of ordinary people',899.00,65,1,'2026-07-27 18:05:46','2026-07-27 18:05:46','R. K. Narayan,'),(6,'The Namesake','The Namesake is a 2003 novel by Jhumpa Lahiri. It follows the Ganguli family—Ashoke and Ashima—who move from Calcutta, India, to Massachusetts, USA. Their son, Gogol, struggles with his unusual pet name, his cultural identity, and the gap between his parents\' traditional Bengali life and modern American society',459.00,84,1,'2026-07-27 18:05:46','2026-07-27 18:05:46','Jhumpa Lahiri'),(7,'Malgudi dinagalu','Malgudi Dinagalu is the Kannada translation of Malgudi Days, a famous collection of 32 short stories written by the renowned Indian author R. K. Narayan. The stories are set in a fictional, quintessential South Indian small town named Malgudi, capturing the simple, ironic, and poignant moments of everyday life.',599.00,100,1,'2026-07-27 18:10:01','2026-07-27 18:10:01',' R K Narayan'),(8,'Aduge manel ondu huli','Aduge Maneyallondu Huli (A Tiger in the Kitchen) is a Kannada play written by the well-known filmmaker and playwright B. Suresha. It is structured as an engaging theatrical game or story that uses falsehoods and metaphors to hunt for deeper truths.',349.00,150,1,'2026-07-27 18:11:46','2026-07-27 18:11:46','B Suresha'),(9,'Delayed Monsoon','Delayed Monsoon is a 2011 fiction novel by Indian author Chitralekha Paul. It tells the story of Abhilasha, a lonely housewife and mother who finds unexpected romance and emotional rejuvenation online later in life, much like parched earth coming alive when the long-awaited monsoon rains finally arrive.',600.00,200,1,'2026-07-27 18:14:04','2026-07-27 18:14:04','Chitralekha Paul'),(10,'heli hogu karana','heli hogu karana  (ಹೇಳಿ ಹೋಗು ಕಾರಣ) is a popular Kannada emotional and romantic novel written by the renowned author Ravi Belagere. Published by Bhavana Prakashana, the book explores deep human emotions, love, heartbreak, and complex relationships.',459.00,350,1,'2026-07-27 18:15:43','2026-07-27 18:15:43','Ravi Belageri'),(11,'Site','best seller',359.00,600,1,'2026-07-27 18:20:35','2026-07-27 18:20:35','ameesha'),(12,'Foundation of education technology','best book to start the your tech journey.',650.00,80,2,'2026-07-28 04:00:07','2026-07-28 04:00:07','shareef M'),(13,'Tech dom','best seller',750.00,120,2,'2026-07-28 04:01:19','2026-07-28 04:01:19','-----'),(14,'The dessign of enevery day things','Indian best selller.',300.00,35,2,'2026-07-28 04:03:56','2026-07-28 04:03:56','Don Norman'),(15,'Science and technology.','Indian Best seller',349.00,60,2,'2026-07-28 04:05:26','2026-07-28 04:05:26','Ravi P Agrahari'),(16,'Science and Technology','Indian best seller',450.00,48,2,'2026-07-28 04:06:33','2026-07-28 04:06:33','Ravi P Agrahari'),(17,'Robotics','Best book to start learning the robotics ',859.00,70,2,'2026-07-28 04:08:59','2026-07-28 04:08:59','Kathy Ceceri'),(18,'Future Tech','Best book for the AI learner\'s',549.00,70,2,'2026-07-28 04:10:28','2026-07-28 04:10:28',NULL),(19,'Introduction to Web technology','Best book to start learning web texhnology.',450.00,80,2,'2026-07-28 04:12:11','2026-07-28 04:12:11','Tanveer Alam'),(20,'Science and technology','Best book for UPSC preparation.',599.00,240,2,'2026-07-28 04:15:12','2026-07-28 04:15:12',NULL),(21,'Technology','Best US seller',549.00,136,2,'2026-07-28 04:17:08','2026-07-28 04:17:08',NULL),(22,'introduction to information science ','Indian Best seller',349.00,49,2,'2026-07-28 05:47:06','2026-07-28 05:47:06','Sanjay'),(23,'Every day technology','Best story book teach you about the technology',350.00,25,2,'2026-07-28 05:52:29','2026-07-28 05:52:29','Devid Anold'),(24,'Algorithm to live By','Best seller ',450.00,51,2,'2026-07-28 05:54:53','2026-07-28 05:54:53','Thomas L. Griffiths, and Tom Griffiths'),(25,'India A History','John Keay\'s India: A History is a probing and provocative chronicle of five thousand years of South Asian history, from the first Harrapan settlements on the banks of the Indus River to the recent nuclear-arms race. In a tour de force of narrative history, Keay blends together insights from a variety of scholarly fields and weaves them together to chart the evolution of the rich tapestry of cultures, religions, and peoples that makes up the modern nations of Pakistan, India, and Bangladesh. Authoritative and eminently readable, India: A History is a compelling epic portrait of one of the world\'s oldest and most richly diverse civilizations.',459.00,45,3,'2026-07-28 09:02:06','2026-07-28 09:02:06','John Keay'),(26,'India that is Bharat','India, That Is Bharat, the first book of a comprehensive trilogy, explores the influence of European \'colonial consciousness\' (or \'coloniality\'), in particular its religious and racial roots, on Bharat as the successor state to the Indic civilisation and the origins of the Indian Constitution. It lays the foundation for its sequels by covering the period between the Age of Discovery, marked by Christopher Columbus\' expedition in 1492, and the reshaping of Bharat through a British-made constitution-the Government of India Act of 1919. This includes international developments leading to the founding of the League of Nations by Western powers that tangibly impacted this journey.',350.00,48,3,'2026-07-28 09:07:52','2026-07-28 09:07:52','J. Sai Deepak'),(27,'Lords of the Deccan','The history of the vast Indian subcontinent is usually told as a series of ephemeral moments when a large part of modern-day India was ruled by a single sovereign. There is an obsession with foreign invasions and the polities of the Gangetic plains, while the histories of the rest of the subcontinent have been reduced to little more than dry footnotes. Now, in this brilliant and critically acclaimed debut book, Anirudh Kanisetti shines a light into the darkness, bringing alive for the lay-reader the early medieval Deccan, from the sixth century CE to the twelfth century CE, in all its splendour and riotous glory.',459.00,70,3,'2026-07-28 09:07:52','2026-07-28 09:07:52','Anirudh Kanisetti'),(28,'Raj  The Making and Unmaking of British India','In less that one hundred years, the British made themselves the masters of India. They ruled for another hundred, leaving behind the independent nations of India and Pakistan when they finally withdrew in 1947. Both nations would owe much to the British Raj: under its rule, Indians learned to see themselves as Indians; its benefits included railways, roads, canals, schools, universities, hospitals, universal language and common law.',899.00,64,3,'2026-07-28 09:09:49','2026-08-05 00:53:38','Lawrence James');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(50) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'gavikanakal66@gmail.com','gavikanakal66@gmail.com','$2a$10$gpttUNnVQbdjJWC9KCHJJe8JzC9ZtsjAsLq6wseblPPOqSeK69Q36','USER','2026-07-29 04:38:36','2026-08-04 14:22:30'),(2,'testuser','test1785411008316@example.com','$2a$10$M.yjGxYI.qRyRFEYJAEK7.CIDZbQrbbphFSX1pEhG0ovUgIHwYNZq','USER','2026-07-30 06:00:14','2026-07-30 06:00:14'),(4,'Gavi','gavi@gmail.com','$2a$10$CEJW13mFV.wmPG65pEIMsu1ACKKU917JURyVB2ZuL5QLFbMG05vH6','USER','2026-07-30 07:11:11','2026-07-30 07:11:11'),(5,'Abhi','abhi@gmail.com','$2a$10$SU3/m/k03TXODWOUnZz8huOqs3q1uM9Skjq5Ou8Nz75y9hioqYXEC','USER','2026-07-30 21:44:47','2026-07-30 21:44:47'),(6,'User','user@gmail.com','$2a$10$P61d6tPbqkutgTT5ImC.3uFRtIW.7c3BvZafyyJJkiLUmrhZLLiDS','USER','2026-07-31 08:51:40','2026-07-31 08:51:40'),(7,'Gavi','gk@gmail.com','$2a$10$yL2Tmjdh.Ii1CTS6v/j75.oewWt76LSQJm5hNxLfki0A3NjqYKI/e','USER','2026-08-02 07:54:41','2026-08-02 07:54:41'),(8,'Vamsi','vamsigujjala80@gmail.com','$2a$10$6XEzYdbHhZ0ujJ9qj9wNJO65vs8/fji2zww.1ceHcLBn0f8iMrK9e','USER','2026-08-02 22:37:45','2026-08-02 22:37:45'),(9,'Gavi','gavik@gmail.com','$2a$10$.uULL1lCCPbIb/AA2i9FnuPROhERPv5MOxqlVmptz0l/rF4/vuHEa','USER','2026-08-03 05:51:43','2026-08-03 05:51:43'),(10,'Gavi','gsk@gmail.com','$2a$10$GsIzV10kGnlB6gVLFXArZOpMANC.uzUPCHfUNZ4IM7TxvrX.5K4cG','USER','2026-08-04 05:01:53','2026-08-04 05:01:53'),(11,'Admin','admin@stackcoders.com','$2a$10$DkmfsH2WhTWpT54DlQ.UeOAUimLRnGHAEeUXUEm5BSLIW7xzcEUDu','ADMIN','2026-08-04 10:56:58','2026-08-04 10:56:58'),(12,'Gavi','kanakal@gmail.com','$2a$10$/esXlcdK5H/Z5bo76BM0geqlu9i1yExQyf.OEQQGNIV26NQZVd/Iq','USER','2026-08-04 12:25:50','2026-08-04 12:25:50');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlist_items`
--

DROP TABLE IF EXISTS `wishlist_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist_items` (
  `wishlist_id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`wishlist_id`),
  UNIQUE KEY `UKtp53unkks741xiqi6m620i7mx` (`user_id`,`product_id`),
  KEY `FKqxj7lncd242b59fb78rqegyxj` (`product_id`),
  CONSTRAINT `FKmmj2k1i459yu449k3h1vx5abp` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKqxj7lncd242b59fb78rqegyxj` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlist_items`
--

LOCK TABLES `wishlist_items` WRITE;
/*!40000 ALTER TABLE `wishlist_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `wishlist_items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-11 22:10:58
