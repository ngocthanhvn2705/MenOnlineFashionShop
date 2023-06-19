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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `EMPLOYEE_ID` varchar(5) NOT NULL,
  `EMPLOYEE_USERNAME` varchar(45) NOT NULL,
  `EMPLOYEE_NAME` varchar(45) NOT NULL,
  `EMPLOYEE_BIRTH` date DEFAULT NULL,
  `EMPLOYEE_GENDER` varchar(6) NOT NULL,
  `EMPLOYEE_ADDRESS` varchar(250) DEFAULT NULL,
  `EMPLOYEE_PHONE` varchar(11) NOT NULL,
  `EMPLOYEE_EMAIL` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`EMPLOYEE_ID`),
  KEY `FK_EMPLOYEE_USERNAME` (`EMPLOYEE_USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('E01','quyen1995','Bùi Long Quyên','1995-12-04','MALE','458 Binh Thanh, TPHCM','5647364756','quyen1995@gmail.com'),('E02','yen2000','Nguyễn Đình Yên','2000-01-31','MALE','64 Ap Bac, TP HCM','6756436455','yen2000@gmail.com'),('E03','hai1999','Huỳnh Sơn Hải','1999-01-10','MALE','53 Truong Chinh, TP HCM','8675644734','hai1999@gmail.com'),('E04','loan1999','Võ Kim Loan','1999-08-23','FEMALE','31 Ba Trieu, Ha Noi','2899500065','loan1999@gmail.com'),('E05','liem2000','Võ Huy Liêm','2000-11-18','FEMALE','32/4 Hai Ba Trung, Ha Noi','7564532261','liem2000@gmail.com'),('E06','employee','Nguyen Ngoc Thanh','2003-05-27','MALE','Nha Trang','09934324324','21522600@gm.uit.edu.vn');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-19 12:27:16
