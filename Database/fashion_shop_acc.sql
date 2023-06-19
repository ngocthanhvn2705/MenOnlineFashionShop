-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: fashion_shop
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `acc`
--

DROP TABLE IF EXISTS `acc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acc` (
  `ACC_USERNAME` varchar(250) NOT NULL,
  `ACC_PASSWORD` varchar(250) NOT NULL,
  `ACC_AUTHORITY` varchar(20) NOT NULL,
  PRIMARY KEY (`ACC_USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acc`
--

LOCK TABLES `acc` WRITE;
/*!40000 ALTER TABLE `acc` DISABLE KEYS */;
INSERT INTO `acc` VALUES ('admin','admin','Manager'),('binh1999','31121999B','Customer'),('customer','customer','Customer'),('dat2003','09092003D','Customer'),('diep2003','23041998D','Customer'),('dinh2003','12022003D','Customer'),('employee','employee','Employee'),('hai1999','hai1999','Employee'),('hong2002','26082002H','Customer'),('khang','khang','Customer'),('khang2003','17052003K','Customer'),('liem2000','1811liemm','Employee'),('loan1999','loanloan123','Employee'),('manh2003','30102003M','Customer'),('nam2000','30012000N','Customer'),('nam2000vn','15112000N','Customer'),('quyen1995','04121995Q','Employee'),('sana1996','29121996S','Manager'),('vinh2001','12032001V','Customer'),('yen2000','yen3101','Employee');
/*!40000 ALTER TABLE `acc` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 23:06:53
